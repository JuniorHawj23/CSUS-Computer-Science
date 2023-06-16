# Brian Hert
# CSC 35
# Lab 4

.intel_syntax noprefix
.data

Pet:
	.ascii "Did you get an animal pet? (1=yes 2=no)?\n\0"
House:
	.ascii "\nAre you afraid of what house you will be sorted into? (1=yes 2=no)?\n\0"
Born:
	.ascii "\nAre you a muggle-born? (1=yes 2=no)?\n\0"
Wand:
	.ascii "\nOh, did you get a wand yet? (1=yes 2=no)?\n\0"
Stress:
	.ascii "\nYour total stress level is at \0"
Stress1:
	.ascii "\n\nOh dear! You are so stressed! Here! Have some magical calming hot cocoa!\n\0"
Stress2:
	.ascii "\n\nYou are going do fine student! Keep your chin up!\n\0"

Pet0:
	.quad 15
House0:
	.quad 30
Born0:
	.quad 30
Wand0:
	.quad 25

.text
.global _start
_start:

	# Ask the user the Pet Question
	lea rdx, Pet
	call PrintZString
	# Scan user input
	call ScanInt
	cmp rdx, 2
	je Pet2
	cmp rdx, 1
	je Pet1

# If the user input 2 then 15 will be add to the total stress
Pet2:
	mov rax, Pet0
	jmp Pet1

# Print the House question while scanning the input
Pet1:
	lea rdx, House
	call PrintZString
	call ScanInt
	cmp rdx, 1
	je House1
	cmp rdx, 2
	je House2

# If the user input 1 then 30 will be add to the total stress
House1:
	add rax, House0
	jmp House2

# Print the Born question while scanning the input 
House2:
	lea rdx, Born
	call PrintZString
	call ScanInt
	cmp rdx, 1
	je Born1
	cmp rdx, 2
	je Born2

# If the user input 1 then 30 will be add to the total stress
Born1:
	add rax, Born0
	jmp Born2

# Print the Wand question while scanning the input 
Born2:
	lea rdx, Wand
	call PrintZString
	call ScanInt
	cmp rdx, 2
	je Wand2
	cmp rdx, 1
	je Complete1

# If the user input 2 then 25 will be add to the total stress
Wand2:
	add rax, Wand0
	jmp Complete1


# Print the user stress number
Complete1:
	lea rdx, Stress
	call PrintZString
	mov rdx, rax
	call PrintInt
	jmp Complete2

# Determine if the user is stress or not
Complete2:
	cmp rdx, 50
	jg Option1
	jl Option2

# Tells that the user is stress
Option1:
	lea rdx, Stress1
	call PrintZString
	jmp Done

# Tells that the user is doing fine
Option2:
	lea rdx, Stress2
	call PrintZString
	jmp Done
	
Done:
	call Exit
