/**
 * CPE/CSC 159 - Operating System Pragmatics
 * California State University, Sacramento
 *
 * Kernel functions
 */
#include <spede/flames.h>
#include <spede/stdarg.h>
#include <spede/stdio.h>
#include <spede/string.h>

#include "kernel.h"
#include "vga.h"
#include "keyboard.h"

#ifndef KERNEL_LOG_LEVEL_DEFAULT
#define KERNEL_LOG_LEVEL_DEFAULT KERNEL_LOG_LEVEL_TRACE
#endif

int kernel_log_level = KERNEL_LOG_LEVEL_DEFAULT;

void kernel_init(void) {
    kernel_log_info("Welcome to %s!", OS_NAME);
    kernel_log_info("Initializing kernel...");
}

void kernel_log_error(char *msg, ...) {
    if (kernel_log_level < KERNEL_LOG_LEVEL_ERROR) {
        return;
    }
    va_list args;
    printf("error: ");
    va_start(args, msg);
    vprintf(msg, args);
    va_end(args);
    printf("\n");
}

void kernel_log_warn(char *msg, ...) {
    if (kernel_log_level < KERNEL_LOG_LEVEL_WARN) {
        return;
    }
    va_list args;
    printf("warn: ");
    va_start(args, msg);
    vprintf(msg, args);
    va_end(args);
    printf("\n");
}

void kernel_log_info(char *msg, ...) {
    if (kernel_log_level < KERNEL_LOG_LEVEL_INFO) {
        return;
    }
    va_list args;
    printf("info: ");
    va_start(args, msg);
    vprintf(msg, args);
    va_end(args);
    printf("\n");
}

void kernel_log_debug(char *msg, ...) {
    if (kernel_log_level < KERNEL_LOG_LEVEL_DEBUG) {
        return;
    }
    va_list args;
    printf("debug: ");
    va_start(args, msg);
    vprintf(msg, args);
    va_end(args);
    printf("\n");
}

void kernel_log_trace(char *msg, ...) {
    if (kernel_log_level < KERNEL_LOG_LEVEL_TRACE) {
        return;
    }
    va_list args;
    printf("trace: ");
    va_start(args, msg);
    vprintf(msg, args);
    va_end(args);
    printf("\n");
}

void kernel_panic(char *msg, ...) {
    va_list args;
    printf("panic: ");
    vga_printf("panic: ");
    va_start(args, msg);
    vprintf(msg, args);
    va_end(args);
    printf("\n");
    kernel_break();
    exit(1);
}

int kernel_get_log_level(void) {
    return kernel_log_level;
}

int kernel_set_log_level(log_level_t level) {
    int prev_log_level = kernel_log_level;
    if (level < KERNEL_LOG_LEVEL_NONE) {
        kernel_log_level = KERNEL_LOG_LEVEL_NONE;
    } else if (level > KERNEL_LOG_LEVEL_ALL) {
        kernel_log_level = KERNEL_LOG_LEVEL_ALL;
    } else {
        kernel_log_level = level;
    }
    if (prev_log_level != kernel_log_level) {
        printf("<<kernel log level set to %d>>", kernel_log_level);
    }
    return kernel_log_level;
}

void kernel_break(void) {
    breakpoint();
}

/**
 * kernel_command:
 *   Interprets certain control characters (or ASCII codes) as special commands:
 *
 *   - 0x10 = CTRL-P => kernel_panic()
 *   - 0x02 = CTRL-B => kernel_break()
 *   - 0x0B = CTRL-K => clear screen
 *   - CTRL-ESC x 3 => exit
 *   - '+' => increase log level
 *   - '-' => decrease log level
 *
 */
void kernel_command(char c) {
    static int ctrl_esc_count = 0;

    switch (c) {
        case 0x10: // CTRL-P => panic
            kernel_panic("test panic");
            break;

        case 0x02: // CTRL-B => breakpoint
            kernel_break();
            break;

        case 0x0B: // CTRL-K => clear screen
            vga_clear();
            break;

        case '+': // Increase log level
            kernel_set_log_level(kernel_get_log_level() + 1);
            break;

        case '-': // Decrease log level
            kernel_set_log_level(kernel_get_log_level() - 1);
            break;

        case KEY_ESCAPE: // 0x1B => ESC
            // If CTRL is pressed, increment ctrl_esc_count
            if (keyboard_is_ctrl_pressed()) {
                ctrl_esc_count++;
                if (ctrl_esc_count >= 3) {
                    kernel_exit();
                }
            } else {
                // If ESC alone, reset the count
                ctrl_esc_count = 0;
            }
            break;

        default:
            // For anything else, reset the ESC count
            ctrl_esc_count = 0;
            break;
    }
}

void kernel_exit(void) {
    printf("Exiting %s...\n", OS_NAME);
    exit(0);
}
