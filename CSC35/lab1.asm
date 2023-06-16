# Brian Hert
# CSC 35
# lab1.asm

# 1. Assemble : as -o lab1.o lab1.asm
# 2. Link     : ld -o a.out lab1.o csc35.o
# 3. Execute  : ./a.out

.intel_syntax noprefix			# Use Intel formatting
.data					# Start the data section

Hello: 					# Message is an address
	.ascii "\nHello, world\n\0" 	# Create a buffer of ASCII

Name:
	.ascii "My name is Brian Hert\n\0"

Inspirational:
	.ascii "Great things come from hard work and perseverance.\n\0"

Year:
	.ascii "In the year \0"
Computer:
	.ascii " the first computer was invented by Charles Babbage.\n\n\0"

.text					# Start the text section
.global _start				# Make the _start label public

_start:					# UNIX starts here
	lea rdx, Hello			# Put address into rdx
	call PrintZString

	lea rdx, Name
	call PrintZString

	lea rdx, Inspirational
	call PrintZString

	lea rdx, Year
	call PrintZString
	    mov rdx, 1822
	    call PrintInt
	   
	 lea rdx, Computer
	 call PrintZString

call Exit				# Exit out the program
