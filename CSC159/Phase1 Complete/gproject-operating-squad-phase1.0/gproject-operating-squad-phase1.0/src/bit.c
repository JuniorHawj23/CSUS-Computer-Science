/**
 * CPE/CSC 159 Operating System Pragmatics
 * California State University, Sacramento
 *
 * Bit Utilities
 */
#include "bit.h"

unsigned int bit_count(unsigned int value) {
    unsigned int count = 0;
    for (int i = 0; i < 32; i++) {
        if (((value >> i) & 1) == 1) {
            count++;
        }
    }
    return count;
}

unsigned int bit_test(unsigned int value, int bit) {
    return (((value >> bit) & 1) == 1) ? 1 : 0;
}

unsigned int bit_set(unsigned int value, int bit) {
    return value | (1 << bit);
}

unsigned int bit_clear(unsigned int value, int bit) {
    return value & ~(1 << bit);
}

unsigned int bit_toggle(unsigned int value, int bit) {
    return value ^ (1 << bit);
}
