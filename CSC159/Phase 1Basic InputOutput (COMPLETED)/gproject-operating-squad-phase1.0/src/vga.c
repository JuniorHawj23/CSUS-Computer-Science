#include <spede/stdarg.h>
#include <spede/stdio.h>

#include "bit.h"
#include "io.h"
#include "kernel.h"
#include "vga.h"

/**
 * Forward Declaration
 */
void vga_cursor_update(void);

#define TAB_STOP 8

int currentBackground;
int currentForeground;
int currentRow;
int currentCol;
bool cursorEnabled;
unsigned short *vga_base = (unsigned short*)VGA_BASE;

/**
 * Initializes the VGA driver and clears the screen.
 */
void vga_init(void) {
    kernel_log_info("Initializing VGA driver");

    currentBackground = VGA_COLOR_BLACK;
    currentForeground = VGA_COLOR_LIGHT_GREY;
    currentRow = 0;
    currentCol = 0;
    cursorEnabled = false;
    
    vga_clear();
}

/**
 * Clears the VGA screen.
 */
void vga_clear(void) {
    for (int i = 0; i < VGA_WIDTH * VGA_HEIGHT; ++i) {
        vga_base[i] = VGA_CHAR(VGA_COLOR_BLACK, VGA_COLOR_LIGHT_GREY, ' ');
    }
    currentRow = 0;
    currentCol = 0;
    vga_set_rowcol(0, 0);
}

/**
 * Clears only the background for all characters.
 */
void vga_clear_bg(int bg) {
    for (int i = 0; i < VGA_WIDTH * VGA_HEIGHT; ++i) {
        unsigned char fg  = vga_base[i] & 0x0F;
        vga_base[i] = VGA_CHAR(bg, fg, ' ');
    }
}

/**
 * Clears only the foreground for all characters.
 */
void vga_clear_fg(int fg) {
    for (int i = 0; i < VGA_WIDTH * VGA_HEIGHT; ++i) {
        unsigned char bg = (vga_base[i] >> 4) & 0x0F;
        vga_base[i] = VGA_CHAR(bg, fg, ' ');
    }
}

/**
 * Enables the VGA text mode cursor.
 */
void vga_cursor_enable(void) {
    unsigned char cursorStartRegister = 0x0A;
    unsigned char cursorEndRegister = 0x0F;
    unsigned char cursorStartScanline = 0x0A;
    unsigned char cursorEndScanline = 0x0F;
    unsigned char cursor_start = inportb(VGA_PORT_ADDR);
    
    cursor_start &= 0xDF;
    outportb(VGA_PORT_ADDR, cursorStartRegister);
    outportb(VGA_PORT_DATA, cursorStartScanline);
    outportb(VGA_PORT_ADDR, cursorEndRegister);
    outportb(VGA_PORT_DATA, cursorEndScanline);
    cursorEnabled = true;
}

/**
 * Disables the VGA text mode cursor.
 */
void vga_cursor_disable(void) {
    unsigned char cursorStartRegister = 0x0A;
    outportb(VGA_PORT_ADDR, cursorStartRegister);
    unsigned char cursorStart = inportb(VGA_PORT_DATA);
    cursorStart |= 0x20;
    outportb(VGA_PORT_DATA, cursorStart);
    cursorEnabled = false;
}

/**
 * Returns whether the cursor is enabled.
 */
bool vga_cursor_enabled(void) {
    return cursorEnabled;
}

/**
 * Updates the VGA cursor position to the current row and column.
 */
void vga_cursor_update(void) {
    if (!cursorEnabled) {
        return;  
    }
    unsigned short cursorPosition = (vga_get_row() * VGA_WIDTH) + vga_get_col();
    unsigned char cursorLow = (unsigned char)(cursorPosition & 0xFF);
    unsigned char cursorHigh = (unsigned char)((cursorPosition >> 8) & 0xFF);
    outportb(VGA_PORT_ADDR, 0x0F);
    outportb(VGA_PORT_DATA, cursorLow);
    outportb(VGA_PORT_ADDR, 0x0E);
    outportb(VGA_PORT_DATA, cursorHigh);
}

/**
 * Sets the current row and column, updating the cursor if enabled.
 */
void vga_set_rowcol(int row, int col) {
    if (row < 0) row = 0;
    if (row >= VGA_HEIGHT) row = VGA_HEIGHT - 1;
    if (col < 0) col = 0;
    if (col >= VGA_WIDTH) col = VGA_WIDTH - 1;
    
    currentRow = row;
    currentCol = col;
    
    if (cursorEnabled) {
        vga_cursor_update();
    }
}

/**
 * Returns the current row.
 */
int vga_get_row(void) {
    return currentRow;
}

/**
 * Returns the current column.
 */
int vga_get_col(void) {
    return currentCol;
}

/**
 * Sets the background color for future operations.
 */
void vga_set_bg(int bg) {
    currentBackground = bg;
}

/**
 * Returns the current background color.
 */
int vga_get_bg(void) {
    return currentBackground;
}

/**
 * Sets the foreground color for future operations.
 */
void vga_set_fg(int fg) {
    currentForeground = fg;
}

/**
 * Returns the current foreground color.
 */
int vga_get_fg(void) {
    return currentForeground;
}

/**
 * Displays a character on the screen without moving the cursor.
 */
void vga_setc(unsigned char c) {
    vga_base[0] = VGA_CHAR(VGA_COLOR_BLACK, VGA_COLOR_LIGHT_GREY, c);
}

/**
 * Prints a character at the current cursor position,
 * handling special characters and wrapping.
 */
void vga_putc(unsigned char c) {
    if (c == '\t') {
        currentCol = (currentCol / TAB_STOP + 1) * TAB_STOP;
        if (currentCol >= VGA_WIDTH) {
            currentCol = 0;
            currentRow++;
        }
    } else if (c == '\b') {
        if (currentCol > 0) {
            currentCol--;
            vga_base[currentRow * VGA_WIDTH + currentCol] = VGA_CHAR(currentBackground, currentForeground, ' ');
        }
    } else if (c == '\n') {
        currentRow++;
        currentCol = 0;
    } else if (c == '\r') {
        currentCol = 0;
    } else {
        vga_base[currentRow * VGA_WIDTH + currentCol] = VGA_CHAR(currentBackground, currentForeground, c);
        currentCol++;
    }
    
    if (currentCol >= VGA_WIDTH) {
        currentCol = 0;
        currentRow++;
    }
    
    if (currentRow >= VGA_HEIGHT) {
        // For Phase 1, simply wrap around (scrolling not required)
        currentRow = 0;
        currentCol = 0;
    }

    if (cursorEnabled) {
        vga_cursor_update();
    }
}

/**
 * Prints a string starting at the current cursor position.
 */
void vga_puts(char *s) {
    if (s == NULL) {
        return;
    }
    while (*s != '\0') {
        vga_putc(*s);
        s++;
    }
}

/**
 * Prints a character at the specified row, column, background, and foreground.
 */
void vga_putc_at(int row, int col, int bg, int fg, unsigned char c) {
    unsigned short *printLocation = vga_base + (row * VGA_WIDTH + col);
    *printLocation = VGA_CHAR(bg, fg, c);
}

/**
 * Prints a string at the specified row, column, background, and foreground.
 */
void vga_puts_at(int row, int col, int bg, int fg, char *s) {
    while (*s != '\0') {
        vga_putc_at(row, col, bg, fg, *s);
        col++;
        if (col >= VGA_WIDTH) {
            col = 0;
            row++;
            if (row >= VGA_HEIGHT) {
                row = 0;
            }
        }
        s++;
    }
}
