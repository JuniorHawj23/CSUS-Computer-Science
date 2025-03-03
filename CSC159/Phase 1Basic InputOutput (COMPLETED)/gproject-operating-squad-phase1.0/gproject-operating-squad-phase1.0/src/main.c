/**
 * CPE/CSC 159 Operating System Pragmatics
 * California State University, Sacramento
 *
 * Operating system entry point
 */

#include "vga.h"
#include "keyboard.h"
#include "kernel.h"
#include "bit.h"

void main(void) {
    // Initialize VGA and keyboard drivers
    vga_init();
    keyboard_init();

    // Set text color to red for initial output
    vga_set_fg(VGA_COLOR_RED);

    // Print welcome message
    vga_printf("Welcome to OperatingSquad!\n");

    // Demonstrate bit operation utilities
    unsigned int value = 0xdecafbad;
    int count = bit_count(value);
    int test = bit_test(value, count);
    unsigned int toggled1 = bit_toggle(value, 31);
    unsigned int toggled2 = bit_toggle(value, 0);

    vga_printf("0x%08x has %d bits set\n", value, count);
    vga_printf("bit %d of 0x%08x is %d\n", count, value, test);
    vga_printf("0x%08x -> toggling bit 31 = 0x%08x\n", value, toggled1);
    vga_printf("0x%08x -> toggling bit 0  = 0x%08x\n", value, toggled2);

    // Wait for key press to test VGA output
    vga_printf("Press any key to test VGA output\n");
    keyboard_getc();

    // Print ASCII table (all characters in red)
    for (int c = 0; c < 255; c++) {
        vga_putc((unsigned char)c);
    }

    // Wait for key press to test VGA colors
    vga_printf("\nPress any key to test VGA colors\n");
    keyboard_getc();

    // Draw a grid of color squares at the bottom-right of the screen
    for (int bg = 0; bg <= 0xf; bg++) {
        for (int fg = 0; fg <= 0xf; fg++) {
            vga_putc_at(VGA_HEIGHT - bg - 2, VGA_WIDTH - fg - 2, bg, fg, '*');
        }
    }

    // Print control instructions at the bottom in contrasting colors
    vga_puts_at(VGA_HEIGHT - 2, 0, VGA_COLOR_CYAN, VGA_COLOR_WHITE,
                "CTRL-P to test panic, CTRL-B for breakpoint");
    vga_puts_at(VGA_HEIGHT - 1, 0, VGA_COLOR_LIGHT_BLUE, VGA_COLOR_WHITE,
                "CTRL-K to clear screen, CTRL-ESC x 3 to exit");

    // Wait for key press to test keyboard input
    vga_printf("\nPress any key to test keyboard input\n");
    keyboard_getc();

    // Change text color to white for user-typed keys
    vga_set_fg(VGA_COLOR_WHITE);

    // Loop forever, echoing any typed keys (in white)
    while (1) {
        char c = keyboard_poll();
        if (c) {
            // Pass the character to kernel_command() for special CTRL commands
            kernel_command(c);
            // Echo the key on screen
            vga_putc(c);
        }
    }
}
