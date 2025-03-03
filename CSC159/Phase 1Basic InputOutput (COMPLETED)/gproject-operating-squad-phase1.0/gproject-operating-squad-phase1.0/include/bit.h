/**
 * CPE/CSC 159 Operating System Pragmatics
 * California State University, Sacramento
 *
 * Bit Utilities
 */
#ifndef BIT_H
#define BIT_H

unsigned int bit_count(unsigned int value);
unsigned int bit_test(unsigned int value, int bit);
unsigned int bit_set(unsigned int value, int bit);
unsigned int bit_clear(unsigned int value, int bit);
unsigned int bit_toggle(unsigned int value, int bit);

#endif
