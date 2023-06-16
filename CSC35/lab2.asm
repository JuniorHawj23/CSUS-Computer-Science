# Brian Hert
# Lab2.asm
# CSC35

.intel_syntax noprefix
.data

Title:
	.ascii "Joe Gunchy\n\n\0"

Earn:
	.ascii "How much do you earn each month?\n\0"
Spend:
	.ascii "How much do you spend on food and gas per month?\n\0"

CashFlow:
	.ascii "Joe Gunchy, your cash flow is \$\0"

Cash:
	.quad 0

Space:
        .ascii "\n\0"

.text
.global _start
_start:

	lea rdx, Title
	call PrintZString 

	lea rdx, Earn
	call PrintZString
	    call ScanInt
	    mov Cash, rdx
	
	lea rdx, Space
	call PrintZString

	lea rdx, Spend
	call PrintZString
	    call ScanInt
	    mov rbx, rdx	     	
            sub Cash, rbx

	lea rdx, Space
	call PrintZString	

	lea rdx, CashFlow
	call PrintZString
	    mov rdx, Cash
	    call PrintInt

	lea rdx, Space
	call PrintZString
	   
 call Exit			

