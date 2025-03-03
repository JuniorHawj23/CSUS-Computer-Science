#include <spede/stdio.h>

// **CHANGE:** Define VIDEO_MEMORY and WHITE_ON_BLACK constants
#define VIDEO_MEMORY 0xB8000            // Memory address for video output
#define WHITE_ON_BLACK 0x0F             // Color attribute for white text on black background

/**
 * Returns the length of a NULL terminated string
 * @param str pointer to the string
 * @return value indicating the length of the string
 */
int strlen(char *str) {
    int len = 0;
    // Traverse the string until the NULL character is encountered
    while (str[len] != '\0') {
        len++;
    }
    return len;
}

/**
 * Prints a string to the screen starting at 0, 0
 * @param str pointer to the string to print
 */
void puts(char *str) {
    if (!str) { // **CHANGE:** Corrected Null Check
        printf("NULL pointer!\n");
        return;
    }

    volatile char *video = (volatile char *) VIDEO_MEMORY;
    size_t len = strlen(str); 

    for (size_t i = 0; i < len; i++) {
        video[i * 2] = str[i];             // Store Character
        video[i * 2 + 1] = WHITE_ON_BLACK; // Set text color
    }
}

/**
 * Main function
 * **CHANGE:** Fixed return type of main function
 */
int main(void) { // **CHANGE:** Corrected 'int void main(void)' to 'int main(void)'
    char buf[128] = {0};
    int year = 2025;

    *((char *) 0xB8000) = 'A'; // Test output to video memory

    printf("Hello, world!\n");
    printf("Welcome to %s!\n", OS_NAME);

    puts("Hello, World!\n");

    snprintf(buf, sizeof(buf), "Welcome to %s!\n", OS_NAME);
    printf("The buffer size is %d bytes\n", strlen(buf));
    puts(buf);

    printf("CPE/CSC 159 for Spring/Fall %d will be fun!\n", year);
    return 0; // **CHANGE:** Added return 0 to main function
}
