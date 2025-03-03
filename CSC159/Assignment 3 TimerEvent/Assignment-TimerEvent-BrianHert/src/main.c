/* Brian Hert
   2/3/2025 
 * main.c -- Timer Event
 */
#include <spede/flames.h>
#include <spede/machine/asmacros.h>
#include <spede/machine/io.h>
#include <spede/machine/pic.h>
#include <spede/machine/proc_reg.h>
#include <spede/machine/seg.h>
#include <spede/stdio.h>
#include <spede/string.h>

#include "events.h" // Address of TimerEvent

#define LOOP 16660000 // Handy loop to time .6 microseconds

typedef void (* func_ptr_t)();
struct i386_gate *IDT_p;

void Process(void) {
    int i;

    // Infinite loop, terminate when a key is pressed
    while (1) {
        if (cons_kbhit()) {
            cons_printf("Process terminated by key press.\n");
            break;  // Exit the loop when a key is pressed
        }

        // Simulate a 1-second delay by looping
        for (i = 0; i < LOOP; i++) {
            asm("inb $0x80");  // Delay by reading from I/O port 0x80
        }

        // Display 'z' at the bottom of the screen
        cons_putchar('z');
    }
}

int main() {
    IDT_p = get_idt_base();  // Get IDT base address

    cons_printf("IDT located @ DRAM addr %x (%d).\n", IDT_p, IDT_p);

    // Set up the timer interrupt in the IDT (Interrupt Descriptor Table)
    fill_gate(&IDT_p[TIMER_EVENT], (int)TimerEntry, get_cs(), ACC_INTR_GATE, 0);
    outportb(0x21, ~0x01);  // Unmask IRQ0 (timer interrupt)

    // Enable interrupts in the CPU (EFLAGS)
    asm("sti");

    // Call the Process function to run the program
    Process();

    return 0;
}
