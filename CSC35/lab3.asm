# Brian Hert
# lab3.asm
# CSC 35

.intel_syntax noprefix
.data

Title:
	.ascii "Welcome to my restaurant\n\0"

Order:
	.ascii "What is your order?\n\0"

Selected:
	.ascii "You selected:\n\0"

Bill:
	.ascii "How many knuts are you feeding it?\n\0" 

Change:
	.ascii " knuts is your change.\n\0"

Space:
	.ascii "\n\0"

# Menu with the prices

Option1:
	.ascii "1. Bertie Bott's Every Flavor Sliders (23 knuts)\n\0"      

Option2:
	.ascii "2. Cauldron Cakes (37 knuts)\n\0"

Option3:
	.ascii "3. Pumpkin Pasties (74 knuts)\n\0"

Option4:
	.ascii "4. Cancel the order (0 knuts)\n\0"
Option5:
	.ascii "5. Apple (30 knuts)\n\0"

# Table of Items
Items:
	.quad Option1
	.quad Option2
	.quad Option3
	.quad Option4
	.quad Option5

# Each quad represent the price of each option 
Costs:
	.quad 23
	.quad 37
	.quad 74
	.quad 0
	.quad 30
.text
.global _start
_start:

	# print title
	lea rdx, Title
	call PrintZString
	lea rdx, Space
	call PrintZString

	# create a loop label for the items
	mov rsi, 0
ItemsLoop:
	cmp rsi, 5
	mov rdx, [Items + rsi * 8]
	call PrintZString
	add rsi, 1


Continue:
	# Print the menu for the user to select
	lea rdx, Option2
	call PrintZString
	lea rdx, Option3
	call PrintZString
	lea rdx, Option4
	call PrintZString
	lea rdx, Option5
	call PrintZString
	lea rdx, Space
	call PrintZString

	# Ask the user what order they want
	lea rdx, Order	
	call PrintZString

	# Scan the user integer to determine which order they want
	call ScanInt
	mov rsi, rdx
	# If the user enter zero then the order will cancel
	cmp rdx, 0
        je End

	lea rdx, Space
	call PrintZString
	
	# Print the order the user would like
	lea rdx, Selected	
	call PrintZString

	# subtract one because of zero indexing
	sub rsi, 1
	mov rdx, [Items + rsi * 8]
	call PrintZString

	lea rdx, Space
	call PrintZString

	# print how many knuts the user have
	lea rdx, Bill
	call PrintZString

	# Scan the user knuts amount
	call ScanInt
	mov rbx, rdx

	lea rdx, Space
	call PrintZString
	mov rax, [Costs + rsi * 8]

   	# subtract the user knuts from the menu price
	sub rax, rbx
	mov rdx, rax
	call PrintInt

	# Print the user remaining knuts after the order
	lea rdx, Change
	call PrintZString

	lea rdx, Space
	call PrintZString

End:
	call Exit
