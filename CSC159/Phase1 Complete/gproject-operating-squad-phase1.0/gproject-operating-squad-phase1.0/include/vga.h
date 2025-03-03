/**
 * CPE/CSC 159 - Operating System Pragmatics
 * California State University, Sacramento
 *
 * VGA Definitions
 */
#ifndef VGA_H
#define VGA_H

#include <spede/stdio.h>
#include <spede/stdbool.h>

#define VGA_BASE                ((unsigned short *)(0xB8000))
#define VGA_ATTR(bg, fg)        (((bg & 0xf) << 4) | (fg & 0xf))
#define VGA_CHAR(bg, fg, c)     (((VGA_ATTR((bg & 0xf), (fg & 0xf)) << 8)) | (c))

#define VGA_PORT_ADDR 0x3D4
#define VGA_PORT_DATA 0x3D5

#define VGA_WIDTH               80
#define VGA_HEIGHT              25

#define VGA_COLOR_BLACK         0x0
#define VGA_COLOR_BLUE          0x1
#define VGA_COLOR_GREEN         0x2
#define VGA_COLOR_CYAN          0x3
#define VGA_COLOR_RED           0x4
#define VGA_COLOR_MAGENTA       0x5
#define VGA_COLOR_BROWN         0x6
#define VGA_COLOR_LIGHT_GREY    0x7

#define VGA_COLOR_DARK_GREY     0x8
#define VGA_COLOR_LIGHT_BLUE    0x9
#define VGA_COLOR_LIGHT_GREEN   0xA
#define VGA_COLOR_LIGHT_CYAN    0xB
#define VGA_COLOR_LIGHT_RED     0xC
#define VGA_COLOR_LIGHT_MAGENTA 0xD
#define VGA_COLOR_YELLOW        0xE
#define VGA_COLOR_WHITE         0xF

void vga_init(void);
void vga_clear(void);
void vga_clear_bg(int bg);
void vga_clear_fg(int fg);
void vga_cursor_enable(void);
void vga_cursor_disable(void);
bool vga_cursor_enabled(void);
void vga_set_rowcol(int row, int col);
int vga_get_row(void);
int vga_get_col(void);
void vga_set_bg(int bg);
int vga_get_bg(void);
void vga_set_fg(int fg);
int vga_get_fg(void);
void vga_setc(unsigned char c);
void vga_putc(unsigned char c);
void vga_puts(char *s);
void vga_putc_at(int x, int y, int bg, int fg, unsigned char c);
void vga_puts_at(int x, int y, int bg, int fg, char *s);

#define vga_printf(fmt, ...) { \
    char _vga_printf_buf[VGA_WIDTH * VGA_HEIGHT] = {0}; \
    snprintf(_vga_printf_buf, sizeof(_vga_printf_buf), (fmt), ##__VA_ARGS__); \
    vga_puts(_vga_printf_buf); \
}

#endif
