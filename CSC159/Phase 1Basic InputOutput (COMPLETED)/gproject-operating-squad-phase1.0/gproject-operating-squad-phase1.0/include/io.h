#ifndef KERNEL_IO_H
#define KERNEL_IO_H

static inline void outportb(unsigned short port, unsigned char data) {
    asm volatile ("outb %1, %0" : : "dN" (port), "a" (data));
}

static inline unsigned char inportb(unsigned short port) {
    unsigned char val;
    asm volatile("inb %1, %0" : "=a"(val) : "dN"(port));
    return val;
}

static inline void outportw(unsigned short port, unsigned short val) {
    asm volatile("outw %1, %0" : : "a"(val), "Nd"(port));
}

static inline unsigned short inportw(unsigned short port) {
    unsigned short val;
    asm volatile("inw %0, %1" : "=a"(val) : "Nd"(port));
    return val;
}

static inline void outportl(unsigned short port, unsigned int val) {
    asm volatile("outl %1, %0" : : "a"(val), "Nd"(port));
}

static inline unsigned int inportl(unsigned short port) {
    unsigned int val;
    asm volatile("inl %0, %1" : "=a"(val) : "Nd"(port));
    return val;
}

#endif
