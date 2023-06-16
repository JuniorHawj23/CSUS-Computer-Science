# Brian Hert
# CSC 35
# Lab 7

.intel_syntax noprefix
.data

Bill:
        .ascii "\nHow much, in knuts, was the bill?\n\0"
Splitting:
        .ascii "\nHow many people are splitting the bill?\n\0"
Witches:
        .ascii "\nOkay, witches and wizards, give \0"
Wizards:
	.ascii " knuts each.\n\n\0"

.text
.global _start
_start:

	# Print the amount of the bill      
        lea rdx, Bill
        call PrintZString
	
	# Scan user input and store it from rdx into rax
        call ScanInt
        mov rax, rdx

	# A continue loop for the user if he/she input a valid input
Continue:
        lea rdx, Splitting
        call PrintZString
        call ScanInt		
	mov rbx, rdx
	cmp rbx, 0
	jle Continue

	# rax is the bill and rbx is how many people splitting
        lea rdx, Witches
	call PrintZString

	# Divide bill with the amount of people
        cqo
        idiv rbx
        mov rdx, rax
        call PrintInt
	lea rdx, Wizards
	call PrintZString

call Exit
