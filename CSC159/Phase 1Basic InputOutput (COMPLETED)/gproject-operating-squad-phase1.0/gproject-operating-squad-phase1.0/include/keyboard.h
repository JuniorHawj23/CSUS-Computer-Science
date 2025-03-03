/**
 * CPE/CSC 159 - Operating System Pragmatics
 * California State University, Sacramento
 *
 * Keyboard Functions
 */
#ifndef KEYBOARD_H
#define KEYBOARD_H

#define KBD_PORT_DATA           0x60
#define KBD_PORT_STAT           0x64

#define KEY_NULL                0x00
#define KEY_ESCAPE              0x1B

// Directional Keys
#define KEY_HOME                0xE0
#define KEY_END                 0xE1
#define KEY_UP                  0xE2
#define KEY_DOWN                0xE3
#define KEY_LEFT                0xE4
#define KEY_RIGHT               0xE5
#define KEY_PAGE_UP             0xE6
#define KEY_PAGE_DOWN           0xE7
#define KEY_INSERT              0xE8
#define KEY_DELETE              0xE9

// Function Keys
#define KEY_F1                  0xF1
#define KEY_F2                  0xF2
#define KEY_F3                  0xF3
#define KEY_F4                  0xF4
#define KEY_F5                  0xF5
#define KEY_F6                  0xF6
#define KEY_F7                  0xF7
#define KEY_F8                  0xF8
#define KEY_F9                  0xF9
#define KEY_F10                 0xFA
#define KEY_F11                 0xFB
#define KEY_F12                 0xFC

#ifndef ASSEMBLER
void keyboard_init(void);
unsigned int keyboard_scan(void);
unsigned int keyboard_poll(void);
unsigned int keyboard_getc(void);
unsigned int keyboard_decode(unsigned int c);

// We add this so kernel can see if CTRL is pressed
int keyboard_is_ctrl_pressed(void);

#endif

#endif
