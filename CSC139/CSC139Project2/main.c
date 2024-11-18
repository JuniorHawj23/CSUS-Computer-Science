// main.c

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/wait.h>
#include "umem.h"
#include <unistd.h>

// Main Testing Code for Custom Memory Allocator with Forking
// This file contains several test cases to validate the functionality
// of the allocator. Each test case is run in a separate process using fork(),
// allowing umeminit to be called without violating the single-initialization rule.

// Function prototypes for test cases
void test_best_fit_allocation();                // Test BEST_FIT allocation algorithm
void test_worst_fit_allocation();               // Test WORST_FIT allocation algorithm
void test_first_fit_allocation();               // Test FIRST_FIT allocation algorithm
void test_next_fit_allocation();                // Test NEXT_FIT allocation algorithm
void test_urealloc_functionality();             // Test urealloc (realloc) functionality
void test_double_free_error_handling();         // Test double-free error handling
void test_memory_corruption_error_handling();   // Test memory corruption error handling

int main() {
    pid_t process;                      // To hold the PID of the forked child process
    int status;                         // To store the exit status of child processes

    // Test Case 1: Testing BEST_FIT allocation algorithm
    process = fork();                   // Create a child process
    if (process == 0) {
        test_best_fit_allocation();     // Child process runs BEST_FIT test
        exit(0);                        // Exit child process after test completes
    } else {
        waitpid(process, &status, 0);   // Parent waits for child to finish
    }

     // Test Case 2: Testing WORST_FIT allocation algorithm
    process = fork();                   // Fork another child process
    if (process == 0) {
        test_worst_fit_allocation();    // Child process runs WORST_FIT test
        exit(0);                        // Exit after test
    } else {
        waitpid(process, &status, 0);   // Parent waits
    }

     // Test Case 3: Testing FIRST_FIT allocation algorithm
    process = fork();                   // Fork another child process
    if (process == 0) {
        test_first_fit_allocation();    // Child process runs FIRST_FIT test
        exit(0);                        // Exit after test
    } else {
        waitpid(process, &status, 0);   // Parent waits
    }

     // Test Case 4: Testing NEXT_FIT allocation algorithm
    process = fork();                   // Fork another child process
    if (process == 0) {
        test_next_fit_allocation();     // Child process runs NEXT_FIT test
        exit(0);                        // Exit after test
    } else {
        waitpid(process, &status, 0); // Parent waits
    }

     // Test Case 5: Testing urealloc (realloc) functionality
    process = fork();                   // Fork another child process
    if (process == 0) {
        test_urealloc_functionality();  // Child process runs urealloc test
        exit(0);                        // Exit after test
    } else {
        waitpid(process, &status, 0);   // Parent waits
    }

    // Test Case 6: Testing double-free error handling
    process = fork();                       // Fork another child process
    if (process == 0) {
        test_double_free_error_handling();  // Child runs double-free error test
        exit(0);                            // Exit after test
    } else {
        waitpid(process, &status, 0); // Parent waits
    }

    // Test Case 7: Testing memory corruption error handling
    process = fork();                            // Fork another child process
    if (process == 0) {
        test_memory_corruption_error_handling(); // Child runs memory corruption error test
        exit(0);                                 // Exit after test
    } else {
        waitpid(process, &status, 0); // Parent waits
    }

    return 0;  // Main function returns, indicating end of program
}

// Test Case 1: Testing BEST_FIT allocation algorithm
void test_best_fit_allocation() {
    printf("Running Test Case 1: BEST_FIT Allocation Algorithm Initialization\n");
    if (umeminit(7500, BEST_FIT) != 0) {
        fprintf(stderr, "Error initializing memory allocator with BEST_FIT\n");
        exit(1);
    }

    // Allocate several blocks of varying sizes
    printf("Allocating memory blocks of different sizes...\n");
    void *ptr1 = umalloc(32);
    void *ptr2 = umalloc(512);
    void *ptr3 = umalloc(128);
    void *ptr4 = umalloc(64);

    printf("Allocated memory blocks at addresses: %p, %p, %p, %p\n", ptr1, ptr2, ptr3, ptr4);

    // Free some blocks to create fragmentation
    printf("Freeing some blocks to simulate fragmentation...\n");
    ufree(ptr2);
    ufree(ptr4);

    // Allocate a block to test BEST_FIT
    printf("Attempting to allocate a 100-byte block (should fit into the 128-byte free block)...\n");
    void *ptr5 = umalloc(100);
    printf("Allocated at address: %p\n", ptr5);

    // Verify that ptr5 is allocated in the best-fit block
    if (ptr5 == ptr2) {
        printf("BEST_FIT allocation successful: selected the best-fit block.\n");
    } else {
        printf("BEST_FIT allocation failed to select the best-fit block.\n");
    }

    // Clean up
    ufree(ptr1);
    ufree(ptr3);
    ufree(ptr5);

    // Display memory statistics
    printf("Displaying memory statistics after BEST_FIT test:\n");
    umemstats();
}

// Test Case 2: Testing WORST_FIT allocation algorithm
void test_worst_fit_allocation() {
    printf("\nRunning Test Case 2: WORST_FIT Allocation Algorithm Initialization\n");
    if (umeminit(7500, WORST_FIT) != 0) {
        fprintf(stderr, "Error initializing memory allocator with WORST_FIT\n");
        exit(1);
    }

    // Allocate and free blocks to create fragmentation
    printf("Allocating and freeing blocks to simulate fragmentation...\n");
    void *blocks[5];
    blocks[0] = umalloc(200);  // 200 bytes
    blocks[1] = umalloc(300);  // 300 bytes
    blocks[2] = umalloc(1000); // 1000 bytes
    blocks[3] = umalloc(500);  // 500 bytes
    blocks[4] = umalloc(400);  // 400 bytes

    ufree(blocks[1]); // Free the largest block
    ufree(blocks[3]); // Free another block

    // Allocate a block to test WORST_FIT
    printf("Allocating a 400-byte block (should fit into the 1000-byte free block)...\n");
    void *ptr6 = umalloc(400);
    printf("Allocated at address: %p\n", ptr6);

    // Verify that ptr6 is allocated in the largest free block
    if (ptr6 == blocks[1]) {
        printf("WORST_FIT allocation successful: selected the largest free block.\n");
    } else {
        printf("WORST_FIT allocation failed to select the largest free block.\n");
    }

    // Clean up
    for (int i = 0; i < 5; i++) {
        if (blocks[i] != ptr6 && blocks[i] != NULL) {
            ufree(blocks[i]);
        }
    }
    ufree(ptr6);

    // Display memory statistics
    printf("Displaying memory statistics after WORST_FIT test:\n");
    umemstats();
}

// Test Case 3: Testing FIRST_FIT allocation algorithm
void test_first_fit_allocation() {
    printf("\nRunning Test Case 3: FIRST_FIT Allocation Algorithm Initialization\n");
    if (umeminit(7500, FIRST_FIT) != 0) {
        fprintf(stderr, "Error initializing memory allocator with FIRST_FIT\n");
        exit(1);
    }

    // Allocate and free blocks
    printf("Allocating memory blocks...\n");
    void *ptr7 = umalloc(200);
    void *ptr8 = umalloc(400);
    void *ptr9 = umalloc(800);

    printf("Freeing the first allocated block...\n");
    ufree(ptr7);

    // Allocate a block to test FIRST_FIT
    printf("Allocating a 100-byte block (should fit into the first available free block)...\n");
    void *ptr10 = umalloc(100);
    printf("Allocated at address: %p\n", ptr10);

    if (ptr10 == ptr7) {
        printf("FIRST_FIT allocation successful: selected the first available free block.\n");
    } else {
        printf("FIRST_FIT allocation failed to select the first available block.\n");
    }

    // Clean up
    ufree(ptr8);
    ufree(ptr9);
    ufree(ptr10);

    // Display memory statistics
    printf("Displaying memory statistics after FIRST_FIT test:\n");
    umemstats();
}

// Test Case 4: Testing NEXT_FIT allocation algorithm
void test_next_fit_allocation() {
    printf("\nRunning Test Case 4: NEXT_FIT Allocation Algorithm Initialization\n");
    if (umeminit(7500, NEXT_FIT) != 0) {
        fprintf(stderr, "Error initializing memory allocator with NEXT_FIT\n");
        exit(1);
    }

    // Allocate and free blocks
    printf("Allocating memory blocks...\n");
    void *ptr11 = umalloc(300);
    void *ptr12 = umalloc(500);
    void *ptr13 = umalloc(800);

    printf("Freeing the first and third allocated blocks...\n");
    ufree(ptr11);
    ufree(ptr13);

    // Allocate blocks to test NEXT_FIT
    printf("Allocating a 200-byte block...\n");
    void *ptr14 = umalloc(200);
    printf("Allocated at address: %p\n", ptr14);

    printf("Allocating 500-byte block...\n");
    void *ptr15 = umalloc(500);
    printf("Allocated at address: %p\n", ptr15);

    // Clean up
    ufree(ptr12);
    ufree(ptr14);
    ufree(ptr15);

    // Display memory statistics
    printf("Displaying memory statistics after NEXT_FIT test:\n");
    umemstats();
}

// Test Case 5: Testing urealloc functionality
void test_urealloc_functionality() {
    printf("\nRunning Test Case 5: Testing urealloc Functionality\n");
    if (umeminit(7500, FIRST_FIT) != 0) {
        fprintf(stderr, "Error initializing memory allocator for urealloc test\n");
        exit(1);
    }

    printf("Allocating a 256-byte block...\n");
    void *ptr16 = umalloc(256);
    strcpy(ptr16, "This is a test string for urealloc.");

    printf("Reallocating to a larger 512-byte block...\n");
    void *ptr17 = urealloc(ptr16, 512);
    if (ptr17 == NULL) {
        fprintf(stderr, "urealloc failed to allocate larger block\n");
        exit(1);
    }
    printf("Data after realloc: %s\n", (char *)ptr17);

    printf("Reallocating to a smaller 128-byte block...\n");
    void *ptr18 = urealloc(ptr17, 128);
    if (ptr18 == NULL) {
        fprintf(stderr, "urealloc failed to allocate smaller block\n");
        exit(1);
    }
    printf("Data after realloc: %s\n", (char *)ptr18);

    // Clean up
    ufree(ptr18);

    // Display memory statistics
    printf("Displaying memory statistics after urealloc test:\n");
    umemstats();
}

// Test Case 6: Error handling - double free
void test_double_free_error_handling() {
    printf("\nRunning Test Case 6: Error Handling - Double Free\n");
    if (umeminit(7500, FIRST_FIT) != 0) {
        fprintf(stderr, "Error initializing memory allocator for double free test\n");
        exit(1);
    }

    printf("Allocating a 128-byte block...\n");
    void *ptr19 = umalloc(128);
    printf("Freeing the block...\n");
    ufree(ptr19);
    printf("Attempting to free the block again (should trigger an error)...\n");
}

// Test Case 7: Error handling - memory corruption
void test_memory_corruption_error_handling() {
    printf("\nRunning Test Case 7: Error Handling - Memory Corruption\n");
    if (umeminit(7500, FIRST_FIT) != 0) {
        fprintf(stderr, "Error initializing memory allocator for memory corruption test\n");
        exit(1);
    }

    printf("Allocating a 256-byte block...\n");
    void *ptr20 = umalloc(256);
    printf("Corrupting the allocated memory block...\n");
    header_t *header = (header_t *)((char *)ptr20 - sizeof(header_t));
    header->magic = 0xBADBADBAD;

    printf("Attempting to free the corrupted block (should trigger an error)...\n");
}
