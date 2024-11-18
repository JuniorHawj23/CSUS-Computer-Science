// umem.c

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/mman.h>
#include "umem.h"
#include <unistd.h>
#include <fcntl.h>

/*
 * Custom Memory Allocator Implementation
 *
 * This file implements a custom memory allocator with functions such as
 * umalloc, ufree, urealloc, umemstats, and umeminit. The allocator
 * supports various memory allocation strategies, including BEST_FIT, 
 * WORST_FIT, FIRST_FIT, and NEXT_FIT. It utilizes mmap to request 
 * a block of memory from the operating system and manages the allocated
 * memory internally.
 */


// Function prototypes for internal use
void *best_fit(size_t size);                // Finds the best-fitting block for allocation
void *worst_fit(size_t size);               // Finds the worst-fitting block for allocation
void *first_fit(size_t size);               // Finds the first fitting block for allocation
void *next_fit(size_t size);                // Finds a fitting block using the NEXT_FIT strategy
void *locate_fit(size_t size);              // Finds a suitable block of memory based on the selected algorithm
void coalesce_available_blocks();                // Combines adjacent free blocks to reduce fragmentation
void add_to_free_list(node_t *node);        // Adds a block to the free list
void delete_from_free_list(node_t *node);   // Removes a block from the free list
int locate_in_free_list(void *ptr);         // Checks if a given pointer is in the free list

// Global variables
int entire_allocations = 0;                  // Total number of allocations made
int entire_deallocations = 0;                // Total number of deallocations made
int allocation_algorithm = 0;               // Selected allocation algorithm (BEST_FIT, WORST_FIT, FIRST_FIT, NEXT_FIT)
void *umem_begin = NULL;                    // Start of the memory region allocated by mmap
void *umem_end = NULL;                      // End of the memory region
void *next_fit_pointer = NULL;              // Pointer used for NEXT_FIT allocation strategy
node_t *free_list = NULL;                   // Head of the free list (linked list of free memory blocks)
size_t total_memory = 0;                    // Total size of the memory region allocated by mmap
size_t allocated_memory = 0;                // Currently allocated memory (in use)
size_t free_memory = 0;                     // Currently free memory available for allocation


/*
 * umeminit: Initializes the memory allocator by allocating a memory region using mmap.
 *          It sets the allocation algorithm and initializes the free list with one large block.
 */
int umeminit(size_t sizeOfRegion, int allocationAlgo) {
    static int initialized = 0;  // Flag to ensure initialization happens only once

    // Check for invalid initialization
    if (initialized || sizeOfRegion <= 0 ||
        (allocationAlgo != BEST_FIT && allocationAlgo != WORST_FIT &&
         allocationAlgo != FIRST_FIT && allocationAlgo != NEXT_FIT)) {
        return -1;
    }

    // Get the system's page size
    size_t pagesize = getpagesize();

    // Round up region size to the nearest multiple of the page size
    size_t region_size = ((sizeOfRegion + pagesize - 1) / pagesize) * pagesize;

    // Open "/dev/zero" to allocate memory from the OS (a special file that provides zeroed-out pages)
    int fd = open("/dev/zero", O_RDWR);
    if (fd == -1) {
        perror("open");
        return -1;
    }

    // Allocate the memory region with mmap
    void *ptr = mmap(NULL, region_size, PROT_READ | PROT_WRITE, MAP_PRIVATE, fd, 0);
    if (ptr == MAP_FAILED) {
        perror("mmap");
        close(fd);
        return -1;
    }

    // Close the file descriptor as it's no longer needed
    close(fd);

    // Initialize global variables
    umem_begin = ptr;
    umem_end = ptr + region_size;
    total_memory = region_size;
    free_memory = region_size;
    allocation_algorithm = allocationAlgo;
    next_fit_pointer = free_list;

    // Initialize the free list with one large block covering the entire region
    free_list = (node_t *)ptr;
    free_list->size = region_size;
    free_list->next = NULL;

    initialized = 1;  // Mark the allocator as initialized

    return 0;
}

/*
 * umalloc: Allocates memory of the given size from the memory pool.
 *          Uses the selected allocation algorithm to find a suitable block.
 */
void *umalloc(size_t size) {
    // Check if the memory allocator is initialized
    if (umem_begin == NULL) {
        return NULL;
    }

    // If size is zero, return NULL (no memory allocated)
    if (size == 0) {
        return NULL;
    }

    // Adjust size to account for header and alignment
    size_t total_size = size + sizeof(header_t);
    if (total_size % 8 != 0) {
        total_size += (8 - (total_size % 8));  // Align to 8-byte boundary
    }

    // Find a suitable free block
    node_t *node = (node_t *)locate_fit(total_size);
    if (node == NULL) {
        return NULL;  // No suitable block found
    }

    // Split the block if it's large enough to accommodate both the allocation and a free block
    size_t remaining_size = node->size - total_size;
    if (remaining_size >= sizeof(node_t) + 8) {
        node_t *new_free_node = (node_t *)((char *)node + total_size);
        new_free_node->size = remaining_size;
        add_to_free_list(new_free_node);  // Add the remaining block to the free list
    } else {
        // Can't split, allocate the entire block
        total_size = node->size;
    }

    // Remove the node from the free list
    delete_from_free_list(node);

    // Set up the header for the allocated block
    header_t *header = (header_t *)node;
    header->size = total_size;
    header->magic = MAGIC;  // Mark the block as valid with a magic number

    // Update memory statistics
    allocated_memory += total_size;
    free_memory -= total_size;
    entire_allocations++;

    // Return a pointer to the user data (skipping over the header)
    return (void *)((char *)header + sizeof(header_t));
}

/*
 * urealloc: Reallocates the given memory block to a new size.
 *          It either expands the block in place (if possible) or allocates a new block
 *          and copies the data from the old block to the new one.
 */
void *urealloc(void *ptr, size_t size) {
    // If ptr is NULL, it behaves like umalloc
    if (ptr == NULL) {
        return umalloc(size);
    }

    // If size is zero, free the block and return NULL
    if (size == 0) {
        ufree(ptr);
        return NULL;
    }

    // Get the header for the existing block
    header_t *header = (header_t *)((char *)ptr - sizeof(header_t));

    // Check for memory corruption
    if (header->magic != MAGIC) {
        fprintf(stderr, "Error: Memory corruption detected at block %p\n", ptr);
        exit(1);
    }

    // Get the old size of the block
    size_t old_size = header->size - sizeof(header_t);
    if (size <= old_size) {
        // If the new size is smaller, we can shrink the block in place
        return ptr;
    } else {
        // Try to expand in place if possible
        void *next_block = (char *)header + header->size;
        if (next_block < umem_end) {
            node_t *next_node = (node_t *)next_block;
            if (locate_in_free_list(next_node) && next_node->size >= (size - old_size)) {
                // Delete the next node from the free list and expand the block
                delete_from_free_list(next_node);
                size_t total_new_size = header->size + next_node->size;

                size_t total_required_size = size + sizeof(header_t);
                if (total_required_size % 8 != 0) {
                    total_required_size += (8 - (total_required_size % 8));
                }

                size_t remaining_size = total_new_size - total_required_size;

                if (remaining_size >= sizeof(node_t) + 8) {
                    // Split and create a new free node
                    node_t *new_free_node = (node_t *)((char *)header + total_required_size);
                    new_free_node->size = remaining_size;
                    add_to_free_list(new_free_node);
                } else {
                    // Can't split, use the entire block
                    total_required_size = total_new_size;
                }

                // Update memory statistics
                allocated_memory += (total_required_size - header->size);
                free_memory -= (total_required_size - header->size);
                header->size = total_required_size;

                return ptr;
            }
        }

        // If we can't expand in place, allocate a new block and copy the data
        void *new_ptr = umalloc(size);
        if (new_ptr == NULL) {
            return NULL;  // Allocation failed
        }

        // Copy the data from the old block to the new one
        memcpy(new_ptr, ptr, old_size);

        // Free the old block
        ufree(ptr);

        return new_ptr;
    }
}

/*
 * ufree: Frees the allocated memory block and adds it back to the free list.
 *        It also coalesces adjacent free blocks to reduce fragmentation.
 */
int ufree(void *ptr) {
    // If ptr is NULL, do nothing
    if (ptr == NULL) {
        return 0;
    }

    // Get the header for the block to be freed
    header_t *header = (header_t *)((char *)ptr - sizeof(header_t));

    // Check for memory corruption
    if (header->magic != MAGIC) {
        fprintf(stderr, "Error: Memory corruption detected at block %p\n", ptr);
        exit(1);
    }

    // Check for double free
    if (locate_in_free_list((node_t *)header)) {
        fprintf(stderr, "Error: Double free detected at block %p\n", ptr);
        exit(1);
    }

    // Mark the block as free by clearing the magic number
    header->magic = 0;

    // Convert the header to a free node and add it to the free list
    node_t *node = (node_t *)header;
    node->size = header->size;

    add_to_free_list(node);

    // Update memory statistics
    allocated_memory -= node->size;
    free_memory += node->size;
    entire_deallocations++;

    // Coalesce adjacent free blocks to reduce fragmentation
    coalesce_available_blocks();

    return 0;
}

/*
 * umemstats: Displays memory allocator statistics including the number of allocations, deallocations,
 *            allocated memory, free memory, and fragmentation percentage.
 */
void umemstats(void) {
    double fragmentation = 0.0;

    // Calculate fragmentation by checking the free list
    size_t biggest_free_block = 0;
    size_t total_free = 0;
    node_t *current = free_list;
    while (current != NULL) {
        if (current->size > biggest_free_block) {
            biggest_free_block = current->size;
        }
        total_free += current->size;
        current = current->next;
    }

    size_t fragmented_memory = total_free - biggest_free_block;
    if (total_free > 0) {
        fragmentation = ((double)fragmented_memory / (double)total_free) * 100.0;
    }

    // Print memory statistics
    printumemstats(entire_allocations, entire_deallocations, allocated_memory, free_memory, fragmentation);
}

/*
 * best_fit: Finds the smallest free block that is large enough to satisfy the allocation request.
 */
void *best_fit(size_t size) {
    node_t *best_node = NULL;
    node_t *current = free_list;
    while (current != NULL) {
        if (current->size >= size) {
            if (best_node == NULL || current->size < best_node->size) {
                best_node = current;
                if (current->size == size) {
                    break; // Perfect fit
                }
            }
        }
        current = current->next;
    }
    return best_node;
}

/*
 * worst_fit: Finds the largest free block that is large enough to satisfy the allocation request.
 */
void *worst_fit(size_t size) {
    node_t *worst_node = NULL;
    node_t *current = free_list;
    while (current != NULL) {
        if (current->size >= size) {
            if (worst_node == NULL || current->size > worst_node->size) {
                worst_node = current;
            }
        }
        current = current->next;
    }
    return worst_node;
}

/*
 * first_fit: Finds the first free block that is large enough to satisfy the allocation request.
 */
void *first_fit(size_t size) {
    node_t *current = free_list;
    while (current != NULL) {
        if (current->size >= size) {
            return current;
        }
        current = current->next;
    }
    return NULL;
}

/*
 * next_fit: Finds a suitable free block using the NEXT_FIT strategy, which starts from the last allocation.
 */
void *next_fit(size_t size) {
    if (next_fit_pointer == NULL) {
        next_fit_pointer = free_list;
    }

    node_t *start = next_fit_pointer;
    node_t *current = next_fit_pointer;
    do {
        if (current->size >= size) {
            next_fit_pointer = current->next;
            return current;
        }
        current = current->next ? current->next : free_list;
    } while (current != start);

    return NULL;
}

// Helper functions

/*
 * find_fit: Based on the selected allocation algorithm, finds a suitable free block.
 */
void *locate_fit(size_t size) {
    switch (allocation_algorithm) {
        case BEST_FIT:
            return best_fit(size);
        case WORST_FIT:
            return worst_fit(size);
        case FIRST_FIT:
            return first_fit(size);
        case NEXT_FIT:
            return next_fit(size);
        default:
            return NULL;
    }
}

/*
 * coalesce_free_blocks: Combines adjacent free blocks in the free list to reduce fragmentation.
 */
void coalesce_available_blocks() {
    node_t *current = free_list;
    node_t *prev = NULL;

    // Sort the free list by address using simple insertion sort (for small lists)
    node_t *sorted = NULL;
    while (current != NULL) {
        node_t *next = current->next;
        if (sorted == NULL || current < sorted) {
            current->next = sorted;
            sorted = current;
        } else {
            node_t *s = sorted;
            while (s->next != NULL && s->next < current) {
                s = s->next;
            }
            current->next = s->next;
            s->next = current;
        }
        current = next;
    }

    free_list = sorted;

    // Traverse the free list and coalesce adjacent blocks
    current = free_list;
    while (current != NULL && current->next != NULL) {
        if ((char *)current + current->size == (char *)current->next) {
            // Adjacent blocks, coalesce them
            current->size += current->next->size;
            current->next = current->next->next;
        } else {
            current = current->next;
        }
    }
}

/*
 * add_to_free_list: Adds a block to the free list.
 */
void add_to_free_list(node_t *node) {
    node->next = free_list;
    free_list = node;
}


/*
 * is_in_free_list: Checks if a given pointer is in the free list.
 */
int locate_in_free_list(void *ptr) {
    node_t *current = free_list;
    while (current != NULL) {
        if ((void *)current == ptr) {
            return 1;
        }
        current = current->next;
    }
    return 0;
}

/*
 * delete_from_free_list: Deletes a block from the free list.
 */
void delete_from_free_list(node_t *node) {
    node_t *current = free_list;
    node_t *prev = NULL;

    while (current != NULL) {
        if (current == node) {
            if (prev == NULL) {
                free_list = current->next;
            } else {
                prev->next = current->next;
            }
            current->next = NULL;
            return;
        }
        prev = current;
        current = current->next;
    }
}