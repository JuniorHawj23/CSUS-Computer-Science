/**
 * CPE/CSC 159 - Operating System Pragmatics
 * California State University, Sacramento
 *
 * Keyboard Functions
 */

#include "io.h"
#include "kernel.h"
#include "keyboard.h"

// Modifier state tracking
static int shift_pressed = 0;
static int ctrl_pressed = 0;
static int alt_pressed = 0;
static int caps_lock_enabled = 0;
static int extended_key = 0;

void keyboard_init(void) {
    kernel_log_info("Initializing keyboard driver");

    // Enable the keyboard
    outportb(0x64, 0xAE);

    // Clear keyboard buffer
    while (inportb(0x64) & 0x1) {
        inportb(0x60);
    }
}

unsigned int keyboard_scan(void) {
    if (inportb(0x64) & 0x1) {
        return inportb(0x60);
    }
    return KEY_NULL;
}

unsigned int keyboard_poll(void) {
    unsigned int scan_code = keyboard_scan();
    if (scan_code != KEY_NULL) {
        return keyboard_decode(scan_code);
    }
    return KEY_NULL;
}

unsigned int keyboard_getc(void) {
    unsigned int c = KEY_NULL;
    while ((c = keyboard_poll()) == KEY_NULL);
    return c;
}

/**
 * keyboard_decode:
 *  - Tracks SHIFT, CTRL, ALT, CAPS states
 *  - Converts scancode -> ASCII
 *  - If CTRL is pressed, letters become control codes
 */
unsigned int keyboard_decode(unsigned int scan_code) {
    // Handle extended key prefix
    if (scan_code == 0xE0) {
        extended_key = 1;
        return KEY_NULL;
    }

    // If extended key release, reset flag and ignore
    if (extended_key && (scan_code & 0x80)) {
        extended_key = 0;
        return KEY_NULL;
    }

    // Handle key release events (bit 7 set)
    if (scan_code & 0x80) {
        unsigned int released = scan_code & 0x7F;
        if (released == 0x2A || released == 0x36) {
            shift_pressed = 0;
        } else if (released == 0x1D) {
            ctrl_pressed = 0;
        } else if (released == 0x38) {
            alt_pressed = 0;
        }
        return KEY_NULL;
    }

    // For key press events, update modifiers
    switch (scan_code) {
        case 0x2A: case 0x36: // Shift
            shift_pressed = 1;
            return KEY_NULL;
        case 0x1D: // Ctrl
            ctrl_pressed = 1;
            return KEY_NULL;
        case 0x38: // Alt
            alt_pressed = 1;
            return KEY_NULL;
        case 0x3A: // Caps lock
            caps_lock_enabled = !caps_lock_enabled;
            return KEY_NULL;
        default:
            // Not a direct modifier, so we'll decode
            break;
    }

    // Basic scan code -> ASCII
    static char keymap[128] = {
        0,  27, '1', '2', '3', '4', '5', '6',
        '7', '8', '9', '0', '-', '=', '\b',
        '\t', 'q', 'w', 'e', 'r', 't', 'y', 'u',
        'i', 'o', 'p', '[', ']', '\n', 0,
        'a', 's', 'd', 'f', 'g', 'h', 'j', 'k',
        'l', ';', '\'', '`', 0, '\\', 'z', 'x',
        'c', 'v', 'b', 'n', 'm', ',', '.', '/',
        0, '*', 0, ' ', 0
        // Fill up to 128 if needed
    };

    static char keymap_shift[128] = {
        0,  27, '!', '@', '#', '$', '%', '^',
        '&', '*', '(', ')', '_', '+', '\b',
        '\t', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U',
        'I', 'O', 'P', '{', '}', '\n', 0,
        'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K',
        'L', ':', '"', '~', 0, '|', 'Z', 'X',
        'C', 'V', 'B', 'N', 'M', '<', '>', '?',
        0, '*', 0, ' ', 0
        // Fill up to 128 if needed
    };

    unsigned int c = KEY_NULL;
    if (scan_code < 128) {
        // SHIFT + CAPS logic
        if (caps_lock_enabled) {
            // SHIFT + CAPS => letters become lowercase, symbols SHIFT
            if (shift_pressed) {
                c = keymap[scan_code];
            } else {
                c = keymap_shift[scan_code];
            }
        } else {
            if (shift_pressed) {
                c = keymap_shift[scan_code];
            } else {
                c = keymap[scan_code];
            }
        }
    }

    // If CTRL is pressed, convert letters to control codes
    //  'A'..'Z' => c - 'A' + 1
    //  'a'..'z' => c - 'a' + 1
    if (ctrl_pressed) {
        if (c >= 'A' && c <= 'Z') {
            c = c - 'A' + 1;  // e.g. 'A'->1, 'B'->2, 'K'->11, 'P'->16
        } else if (c >= 'a' && c <= 'z') {
            c = c - 'a' + 1;  // e.g. 'a'->1, 'b'->2, 'p'->16
        }
        // Non-letter keys won't be changed
    }

    return c;
}

// Expose this so kernel can check if CTRL is held (for CTRL-ESC logic)
int keyboard_is_ctrl_pressed(void) {
    return ctrl_pressed;
}
