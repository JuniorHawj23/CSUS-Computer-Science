# Brian Hert
# CSC 35
# lab6.asm

.intel_syntax noprefix
.data

Title:
	.ascii "Greetings Ravenclaw student!\nBefore you may proceed enter a number from 1 to 100\n\0"
Guess:
	.ascii "\nGuess: \0"
Big:
	.ascii "Atlas, that is too big.\n\0"
Small:
	.ascii "Regrettably, that is too small.\n\0"
Win:
	.ascii "You're a intelligent person. You win!\n\0"

.text
.global _start
_start:

	lea rdx, Title			# Print title
	call PrintZString

	mov rdx, 100			# Move 100 into rdx
	call Random			# Get random number
	add rdx, 1			# Add 1 into rdx in order to get 100 instead of only 99
	mov rbx, rdx			# Move the random number from rdx into rbx

While:					# Create a while loop 
	lea rdx, Guess			# Tells the user to take a guess
	call PrintZString		
	call ScanInt			# Scan Integer from the user 

	cmp rdx, rbx			# Compare the user guess with the random number
	jg High
	jl Low
	je Done

High:
	lea rdx, Big			# Tells the user input is too large
	call PrintZString
	jmp While			# Jump into while so the user can guess again

Low:
	lea rdx, Small			# Tells the user input is too small
	call PrintZString
	jmp While			# Jump into while so the user can guess again
Done:
	lea rdx, Win			# Tells the user he/she is correct
	call PrintZString
	call Exit			# End the program
