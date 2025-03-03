/* Brian Hert
   2/3/2025 
 * handlers.c -- Timer Event
 */
#include <spede/flames.h>
#include <spede/machine/asmacros.h>
#include <spede/machine/io.h>
#include <spede/machine/pic.h>
#include <spede/machine/proc_reg.h>
#include <spede/machine/seg.h>
#include <spede/stdio.h>
#include <spede/string.h>

#define PIC_CONTROL     0x20    // Programmable Interrupt Controller (PIC).
#define TIMER_DONE      0x60    // Set to PIC control when timer interrupt service done.

char my_name[] = "Brian Hert";  // Name to display
size_t i = 0;  			// Index for the current character in my_name
int tick_count = 0;  		// Count of timer events

// VGA position to show the first character of name
unsigned short *char_p = (unsigned short *)0xB8000 + 12 * 80 + 34; // Center Position

// Timer handler function, invoked from TimerEvent
void TimerHandler() {
    size_t name_length = strlen(my_name); // Store the length of the name in a size_t variable

    // If tick_count is 0, display the i-th character from the name
    if (tick_count == 0) {
        *(char_p) = my_name[i] | 0xf00;  // Display character with color (light gray)
    }

    // Increment tick_count
    tick_count++;

    // If tick_count reaches 75 (0.75 second), reset and move to the next character
    if (tick_count == 75) {
        tick_count = 0; // Reset tick count
        i++;  		// Move to the next character
        char_p++;  	// Move the cursor to the next VGA position

        // If we reached the end of the name, reset i and char_p
        if (i == name_length) {
            i = 0;
            char_p = (unsigned short *)0xB8000 + 12 * 80 + 34;  // Go back to start position

            // Erase all displayed characters
            for (size_t j = 0; j < name_length; j++) {
                *(char_p + j) = ' ' | 0x0;  // Clear the character without color
            }
        }
    }

    // Dismiss the timer interrupt (IRQ 0) to allow for the next interrupt
    outportb(PIC_CONTROL, TIMER_DONE);
}
