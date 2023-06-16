# Brian Hert
# lab5.asm
# CSC 35

.intel_syntax noprefix
.data

Title:
	.ascii "Welcome to Hogwarts\n\nThe Sorting Hat is being placed upon your head\n\0"

Question1:
	.ascii "\nDo you wish to command others (y/n)\n\0"
Question2:
	.ascii "\nDo you find contentment in reading (y/n)?\n\0"

Ravenclaw:
	.ascii "Ravenclaw!\n\0"
HufflePuff:
	.ascii "HufflePuff\n\0"
Gryffindor:
	.ascii "Gryffindor\n\0"
Slytherin:
	.ascii "Slytherin\n\0"

.text
.global _start
_start:

	lea rdx, Title
	call PrintZString
	lea rdx, Question1	# Print question one
	call PrintZString
	call ScanChar		# Scan Character
	mov cl, 121		# y decimal is equivalent to 121
	cmp dl, cl		
	je optionY		# If user enter y then optionY will proceed
	jne optionN		# If user enter something else then optionN will proceed

optionY:
	lea rdx, Question2	# Print question two
	call PrintZString	
	call ScanChar		# Scan Character to determine which location the user will go
	mov cl, 121		# Mov 121 into cl because y decimal is 121
	cmp dl, cl
	je Slytherin1		
	jne Gryffindor1

optionN:
	lea rdx, Question2	# Print question two
	call PrintZString
	call ScanChar		# Scan Character to determine which location the user will go
	mov cl, 121		# Mov 121 into cl 
	cmp dl, cl
	je Raven1
	jne HufflePuff1

Raven1:			 
	lea rdx, Ravenclaw
	call PrintZString
	jmp Done

Gryffindor1:
	lea rdx, Gryffindor
	call PrintZString
	jmp Done

HufflePuff1:
	lea rdx, HufflePuff
	call PrintZString
	jmp Done

Slytherin1:
	lea rdx, Slytherin
	call PrintZString
	jmp Done
Done:
	call Exit
