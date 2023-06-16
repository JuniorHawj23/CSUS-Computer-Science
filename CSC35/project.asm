# Brian Hert
# CSC 35
# Project

.intel_syntax noprefix
.data

Title:
        .ascii "\nWelcome to Pokemon Showdown!\nWritten by Brian Hert\n\n\0"
Rule1:
        .ascii "\nPokemon Showdown Rules\nDo not target a Pokemon with a health of zero or less.\n\0"
Rule2:
        .ascii "When a Pokemon is knockout of the game. That Pokemon will deal the same amount of damage.\n\0"  
Rule3:
	.ascii "It took to the Pokemon with the lowest number next to it name. That is still a valid target.\n\0"
Rule4:
        .ascii "Only target within the amount of Pokemon you would like to see battle.\n\0"
Rule5:
	.ascii "Each attack will contain a different amount of damage it can possibly do.\n\0"
Rule6:
        .ascii "The last pokemon standing is the winner.\nLet the battle begin!\n\0"
Question:
        .ascii "\nHow many Pokemon would you like to see battle? \0"

Target:
        .ascii "\nYour target: \0"
Selected:
        .ascii "Select a attack move: \0"
Attack:
        .ascii "Your attack did a total of \0"
Choice:
	.ascii "Your choice: \0"

HP:
        .ascii "\nHealth: \0"
Winner:
        .ascii "\nThe Pokemon Showdown winner for today battle is \0"
Articuno:
        .ascii "Pokemon 1: Articuno, the Freezing Legendary Bird Pokemon!\n\0"
Zapdos:
        .ascii "Pokemon 2: Zapdos, the Lightning Legendary Bird Pokemon!\n\0"
Moltres:
        .ascii "Pokemon 3: Moltres, the Blazing Legendary Bird Pokemon!\n\0"
Lugia:
        .ascii "Pokemon 4: Lugia, the Beast of the Sea Pokemon!\n\0"
Meowth:
	.ascii "Pokemon 5: Meowth, the talking cat Pokemon!\n\0"
Snorlax:
	.ascii "Pokemon 6: Snorlax, the eating and sleeping Pokemon!\n\0"
Charizard:
	.ascii "Pokemon 7: Charizard, the fierce fire Pokemon!\n\0"
Gengar:
	.ascii "Pokemon 8: Gengar, the ghost and poison Pokemon!\n\0"
Alakazam:
	.ascii "Pokemon 9: Alakazam, the psychic spoon Pokemon!\n\0"
Magikarp:
	.ascii "Pokemon 10: Magikarp, the almighty powerful Pokemon!\n\0"

Option1:
        .ascii "\n\nPokemon 1: Articuno\0"
Option2:
        .ascii "\n\nPokemon 2: Zapdos\0"
Option3:
        .ascii "\n\nPokemon 3: Moltres\0"
Option4:
        .ascii "\n\nPokemon 4: Lugia\0"
Option5:
        .ascii "\n\nPokemon 5: Meowth\0"
Option6:
	.ascii "\n\nPokemon 6: Snorlax\0"
Option7:
	.ascii "\n\nPokemon 7: Charizard\0"
Option8:
	.ascii "\n\nPokemon 8: Gengar\0"
Option9:
	.ascii "\n\nPokemon 9: Alakazam\0"
Option10:
	.ascii "\n\nPokemon 10: Magikarp\0"

Space:
	.ascii "\n\0"
Pokemons:
        .quad 0
Attack1:
	.ascii "\nAttack Moves\n1. Super Punch!\n2. Hyper Beam!\n3. Ancient Power!\n\n\0"

Health1:
        .quad 100
Health2:
        .quad 100
Health3:
        .quad 100
Health4:
        .quad 100
Health5:
        .quad 100
Health6:
	.quad 100
Health7:
	.quad 100
Health8:
	.quad 100
Health9:
	.quad 100
Health10:
	.quad 100
Art1:	    
        .ascii "         @@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                \n"				
        .ascii "             @@@@@@@@@@@@@@@@@@@@@@@				      \n"
        .ascii "                @@@@@@@@@@@@@@@@@				      \n"
        .ascii "                   @@@@@@@@@@@@					      \n"
	.ascii "                      @@@@@@					      \n"
	.ascii "                        @@@      ___.				      \n"
	.ascii "                         . .    |._, \\				      \n"
	.ascii "                          . .   <  <  \\             _ _              \n"
	.ascii "           __ __           . .  '.   | \\           :    .            \n"
	.ascii "          /      .          . . |    .\\'.          :     .           \n"
	.ascii "         /   __   .          . .,'     \\ \\         :      .         \n"
	.ascii "        /   /   .  .          ,'.-.'-._,' |        :       .          \n"
	.ascii "       /   .     .   .         |    '--/  | \\      :        .        \n"
	.ascii "      /    .      .   .        | \__ - _ ,'' '     :   . .    .       \n"
	.ascii "     /     .        .   .      '-...-\" |  '-'    :   :   .    .      \n"
	.ascii "    /      .          .   .       |    |        :   :     .    .      \n"
	.ascii "   /       .           .    .     |    |      :   :        .    .     \n"
	.ascii "  /        .            '-    .  _|    |_ ,-':   :         .     .    \n"
	.ascii " /         .              \\_,._-/        '-'' _,:          .      .  \n\n\0"			

Art2:
	.ascii "	    _____ ___      ___	        \n"		
	.ascii "	   /     /   \\___/   \         \n"
	.ascii "	  /      (     . .    ) 	\n"
	.ascii " 	 /       \\__	   __/	    	\n"
	.ascii " 	/|    	     (\\   |(		\n" 
	.ascii "       > \\   /____\\    /\\ |          \n"
        .ascii "  	 |___|     |___|_..  	 	\n\0"


Art3:
	.ascii "	  _       _			\n"
	.ascii "	  \\'\\   /'/			\n"
	.ascii "	   \\ \\_/ /			\n"
       	.ascii "	   /.\\ /.\\			\n"
	.ascii "	   \\  T  /			\n"
	.ascii "  	   /  ^  \\			\n"
	.ascii "          / \\\\ // \\		  	\n"
	.ascii "	__\\ _____ /__		       	\n"
	.ascii "       (___/     \\___)               	\n\0"
.text
.global _start
_start:

        lea rdx, Title                  # Print the title of the game 
        call PrintZString
	lea rdx, Art2
	call PrintZString
	
	lea rdx, Rule1			# Rules for understanding how the game work
        call PrintZString
        lea rdx, Rule2
        call PrintZString
        lea rdx, Rule3
        call PrintZString
        lea rdx, Rule4
        call PrintZString
	lea rdx, Rule5
	call PrintZString
	lea rdx, Rule6
	call PrintZString
	lea rdx, Art3
	call PrintZString

QUESTION:
        lea rdx, Question               # Ask the user how many pokemon
        call PrintZString
        call ScanInt			# Scan the user input
        mov Pokemons, rdx               # Move the user input into quad Pokemons
        movq rcx, Pokemons		# rcx is the amount of pokemon the user has input 

        cmp rcx, 2			# Compare the user input with rcx 
        je First1
        cmp rcx, 3			# I wanted to start with Pokemon1 
        je First1
        cmp rcx, 4			# Regardless of how many Pokemon the user would like to have
        je First1
	cmp rcx, 5
	je First1

	cmp rcx, 6
	je First1
	cmp rcx, 7
	je First1
        cmp rcx, 8
	je First1
	cmp rcx, 9
	je First1
	cmp rcx, 10

	je First1
	cmp rcx, 1
        jle QUESTION
        cmp rcx, 10
        jg QUESTION

First1:
        lea rdx, Option1		# Print out Pokemon1
        call PrintZString
        lea rdx, HP			# Print Pokemon1 Health
        call PrintZString
        sub Health1, rbx		# Subtract the attack from Pokemon1 Health
        movq rdx, Health1		# Move quad Health1 into rdx
        call PrintInt			# Print Pokemon1 remaining Health
        jmp TARGET			# Jump into target 

TARGET:
        lea rdx, Target			# Ask the user which target
        call PrintZString

        call ScanInt			# The user input will determine which Pokemon to jump into
        cmp rdx, 1
        je Pokemon1			# In order to calculate the damage
        cmp rdx, 2
        je Pokemon2
        cmp rdx, 3
        je Pokemon3
        cmp rdx, 4
        je Pokemon4

	cmp rdx, 5
	je Pokemon5
	cmp rdx, 6
	je Pokemon6
	cmp rdx, 7
	je Pokemon7
	cmp rdx, 8
	je Pokemon8
	cmp rdx, 9
	je Pokemon9
	cmp rdx, 10
	je Pokemon10

Continue:				# A continue label for determining the Pokemon overall health 
        movq rdx, Health1		# Move quad Health into rdx 
        cmp rdx, 0			# Compare 0 with the Pokemon Health
        jg Poke1			# If the Pokemon Health is greater than 0 that Pokemon can still win the game. 
        movq rdx, Health2		# Pokemon with a Health of 0 or less will no longer be able to win the game. 
        cmp rdx, 0
        jg Poke2			
        movq rdx, Health3
        cmp rdx, 0
        jg Poke3
        movq rdx, Health4
        cmp rdx, 0
        jg Poke4
	movq rdx, Health5
	cmp rdx, 0
	jg Poke5

	movq rdx, Health6
	cmp rdx, 0
	jg Poke6
	movq rdx, Health7
	cmp rdx, 0
	jg Poke7
	movq rdx, Health8
	cmp rdx, 0
	jg Poke8
	movq rdx, Health9
	cmp rdx, 0
	jg Poke9
	movq rdx, Health10
	cmp rdx, 0
	jg Poke10	

Pokemon1:
	lea rdx, Attack1		# Display the attack options
	call PrintZString
	lea rdx, Selected		# Tell the user which attack he selected
	call PrintZString

	call ScanInt			# Scan the user Integer to determine which attack he selected
	cmp rdx, 1
	je Punch1			# Jump if equal into the following attack option
	cmp rdx, 2
	je Beam1
	cmp rdx, 3
	je Power1
	cmp rdx, 0
	jle Pokemon1
	cmp rdx, 4
	jge Pokemon1

Punch1:
	lea rdx, Attack			# Print the attack 
	call PrintZString
        mov rdx, 45			# Move 45 into rdx
        call Random			# Get a random number
        add rdx, 20			# Add 20 into rdx 
        mov rbx, rdx           		# rbx is the value of the attack after moving rdx into rbx
        call PrintInt
        jmp Poke1

Beam1:
	lea rdx, Attack			# Print the attack
	call PrintZString
	mov rdx, 65			# Move 65 into rdx
	call Random			# Get a random number
	add rdx, 1			# Add one into rdx in order to get a high or low number at random
	mov rbx, rdx
	call PrintInt
        jmp Poke1

Power1:
        lea rdx, Attack			# Print the attack
        call PrintZString
        mov rdx, 55			# Move 55 into rdx
        call Random			# Get a random number
        add rdx, 10			# Add ten into rdx 
        mov rbx, rdx			# Move rdx into rbx
        call PrintInt			# Print the integer value for the damage 
        jmp Poke1

Poke1:					# Create a Poke1 label to display the Pokemon
        lea rdx, Option1
        call PrintZString
        lea rdx, HP			# Print the Health 
        call PrintZString

        sub Health1, rbx		# Subtract Health from the attack damage
        movq rdx, Health1
        call PrintInt
        mov rax, rdx
        cmp rax, 0			# If Pokemon Health is lower than zero than jump into Subtract
        jle Subtract			# Jump into TARGET if the Pokemon Health is still greater than zero
        jmp TARGET

Pokemon2:
        lea rdx, Attack1
        call PrintZString
        lea rdx, Selected
        call PrintZString

        call ScanInt
        cmp rdx, 1
        je Punch2
        cmp rdx, 2
        je Beam2
        cmp rdx, 3
        je Power2
        cmp rdx, 0
        jle Pokemon1
        cmp rdx, 4
        jge Pokemon1

Punch2:
        lea rdx, Attack                
        call PrintZString
        mov rdx, 45                    
        call Random                     
        add rdx, 20                     
        mov rbx, rdx                    
        call PrintInt
        jmp Poke2

Beam2:
        lea rdx, Attack
        call PrintZString
        mov rdx, 65
        call Random
        add rdx, 1
        mov rbx, rdx
        call PrintInt
        jmp Poke2

Power2:
	lea rdx, Attack
        call PrintZString
        mov rdx, 55
        call Random
        add rdx, 10
        mov rbx, rdx
        call PrintInt
	jmp Poke2

Poke2:
        lea rdx, Option2
        call PrintZString
        lea rdx, HP
        call PrintZString

        sub Health2, rbx
        movq rdx, Health2
        call PrintInt
        mov rax, rdx
        cmp rax, 0
        jle Subtract
        jmp TARGET

Pokemon3:
        lea rdx, Attack1
        call PrintZString
        lea rdx, Selected
        call PrintZString

        call ScanInt
        cmp rdx, 1
        je Punch3
        cmp rdx, 2
        je Beam3
        cmp rdx, 3
        je Power3
        cmp rdx, 0
        jle Pokemon3
        cmp rdx, 4
        jge Pokemon3

Punch3:
        lea rdx, Attack                
        call PrintZString
        mov rdx, 45                    
        call Random                   
        add rdx, 20                     
        mov rbx, rdx                    
        call PrintInt
        jmp Poke3

Beam3:
        lea rdx, Attack
        call PrintZString
        mov rdx, 65
        call Random
        add rdx, 1
        mov rbx, rdx
        call PrintInt
        jmp Poke3

Power3:
        lea rdx, Attack
        call PrintZString
        mov rdx, 55
        call Random
        add rdx, 10
        mov rbx, rdx
        call PrintInt
        jmp Poke3

Poke3:
        lea rdx, Option3
        call PrintZString
        lea rdx, HP
        call PrintZString

        sub Health3, rbx
        movq rdx, Health3
        call PrintInt
        mov rax, rdx
        cmp rax, 0
        jle Subtract
        jmp TARGET

Pokemon4:
        lea rdx, Attack1
        call PrintZString
        lea rdx, Selected
        call PrintZString

        call ScanInt
        cmp rdx, 1
        je Punch4
        cmp rdx, 2
        je Beam4
        cmp rdx, 3
        je Power4
        cmp rdx, 0
        jle Pokemon4
        cmp rdx, 4
        jge Pokemon4


Punch4:
        lea rdx, Attack                 
        call PrintZString
        mov rdx, 45                     
        call Random                    
        add rdx, 20                    
        mov rbx, rdx                    
        call PrintInt
        jmp Poke4

Beam4:
        lea rdx, Attack
        call PrintZString
        mov rdx, 65
        call Random
        add rdx, 1
        mov rbx, rdx
        call PrintInt
        jmp Poke4

Power4:
        lea rdx, Attack
        call PrintZString
        mov rdx, 55
        call Random
        add rdx, 10
        mov rbx, rdx
        call PrintInt
        jmp Poke4

Poke4:
        lea rdx, Option4
        call PrintZString
        lea rdx, HP
        call PrintZString

        sub Health4, rbx
        movq rdx, Health4
        call PrintInt
        mov rax, rdx
        cmp rax, 0
        jle Subtract
        jmp TARGET

Pokemon5:
        lea rdx, Attack1
        call PrintZString
        lea rdx, Selected
        call PrintZString

        call ScanInt
        cmp rdx, 1
        je Punch5
        cmp rdx, 2
        je Beam5
        cmp rdx, 3
        je Power5
        cmp rdx, 0
        jle Pokemon5
        cmp rdx, 4
        jge Pokemon5

Punch5:
        lea rdx, Attack                
        call PrintZString
        mov rdx, 45                  
        call Random                     
        add rdx, 20                   
        mov rbx, rdx                    
        call PrintInt
        jmp Poke5

Beam5:
        lea rdx, Attack
        call PrintZString
        mov rdx, 65
        call Random
        add rdx, 1
        mov rbx, rdx
        call PrintInt
        jmp Poke5

Power5:
        lea rdx, Attack
        call PrintZString
        mov rdx, 55
        call Random
        add rdx, 10
        mov rbx, rdx
        call PrintInt
        jmp Poke5

Poke5:
        lea rdx, Option5
        call PrintZString
        lea rdx, HP
        call PrintZString

        sub Health5, rbx
        movq rdx, Health5
        call PrintInt
        mov rax, rdx
        cmp rax, 0
        jle Subtract
        jmp TARGET

Pokemon6:
        lea rdx, Attack1
        call PrintZString
        lea rdx, Selected
        call PrintZString

        call ScanInt
        cmp rdx, 1
        je Punch6
        cmp rdx, 2
        je Beam6
        cmp rdx, 3
        je Power6
        cmp rdx, 0
        jle Pokemon6
        cmp rdx, 4
        jge Pokemon6

Punch6:
        lea rdx, Attack                
        call PrintZString
        mov rdx, 45                     
        call Random                     
        add rdx, 20                    
        mov rbx, rdx                    
        call PrintInt
        jmp Poke6

Beam6:
        lea rdx, Attack
        call PrintZString
        mov rdx, 65
        call Random
        add rdx, 1
        mov rbx, rdx
        call PrintInt
        jmp Poke6

Power6:
        lea rdx, Attack
        call PrintZString
        mov rdx, 55
        call Random
        add rdx, 10
        mov rbx, rdx
        call PrintInt
        jmp Poke6

Poke6:
        lea rdx, Option6
        call PrintZString
        lea rdx, HP
        call PrintZString

        sub Health6, rbx
        movq rdx, Health6
        call PrintInt
        mov rax, rdx
        cmp rax, 0
        jle Subtract
        jmp TARGET

Pokemon7:
        lea rdx, Attack1
        call PrintZString
        lea rdx, Selected
        call PrintZString

        call ScanInt
        cmp rdx, 1
        je Punch7
        cmp rdx, 2
        je Beam7
        cmp rdx, 3
        je Power7
        cmp rdx, 0
        jle Pokemon7
        cmp rdx, 4
        jge Pokemon7

Punch7:
        lea rdx, Attack                
        call PrintZString
        mov rdx, 45                     
        call Random                   
        add rdx, 20                     
        mov rbx, rdx                   
        call PrintInt
        jmp Poke7

Beam7:
        lea rdx, Attack
        call PrintZString
        mov rdx, 65
        call Random
        add rdx, 1
        mov rbx, rdx
        call PrintInt
        jmp Poke7

Power7:
        lea rdx, Attack
        call PrintZString
        mov rdx, 55
        call Random
        add rdx, 10
        mov rbx, rdx
        call PrintInt
        jmp Poke7

Poke7:
        lea rdx, Option7
        call PrintZString
        lea rdx, HP
        call PrintZString

        sub Health7, rbx
        movq rdx, Health7
        call PrintInt
        mov rax, rdx
        cmp rax, 0
        jle Subtract
        jmp TARGET

Pokemon8:
        lea rdx, Attack1
        call PrintZString
        lea rdx, Selected
        call PrintZString

        call ScanInt
        cmp rdx, 1
        je Punch8
        cmp rdx, 2
        je Beam8
        cmp rdx, 3
        je Power8
        cmp rdx, 0
        jle Pokemon8
        cmp rdx, 4
        jge Pokemon8

Punch8:
        lea rdx, Attack                
        call PrintZString
        mov rdx, 45                     
        call Random                    
        add rdx, 20                    
        mov rbx, rdx                    
        call PrintInt
        jmp Poke8

Beam8:
        lea rdx, Attack
        call PrintZString
        mov rdx, 65
        call Random
        add rdx, 1
        mov rbx, rdx
        call PrintInt
        jmp Poke8

Power8:
        lea rdx, Attack
        call PrintZString
        mov rdx, 55
        call Random
        add rdx, 10
        mov rbx, rdx
        call PrintInt
        jmp Poke8

Poke8:
        lea rdx, Option8
        call PrintZString
        lea rdx, HP
        call PrintZString

        sub Health8, rbx
        movq rdx, Health8
        call PrintInt
        mov rax, rdx
        cmp rax, 0
        jle Subtract
        jmp TARGET

Pokemon9:
        lea rdx, Attack1
        call PrintZString
        lea rdx, Selected
        call PrintZString

        call ScanInt
        cmp rdx, 1
        je Punch9
        cmp rdx, 2
        je Beam9
        cmp rdx, 3
        je Power9
        cmp rdx, 0
        jle Pokemon9
        cmp rdx, 4
        jge Pokemon9

Punch9:
        lea rdx, Attack                
        call PrintZString
        mov rdx, 45                     
        call Random                     
        add rdx, 20                    
        mov rbx, rdx                   
        call PrintInt
        jmp Poke9

Beam9:
        lea rdx, Attack
        call PrintZString
        mov rdx, 65
        call Random
        add rdx, 1
        mov rbx, rdx
        call PrintInt
        jmp Poke9

Power9:
        lea rdx, Attack
        call PrintZString
        mov rdx, 55
        call Random
        add rdx, 10
        mov rbx, rdx
        call PrintInt
        jmp Poke9

Poke9:
        lea rdx, Option9
        call PrintZString
        lea rdx, HP
        call PrintZString

        sub Health9, rbx
        movq rdx, Health9
        call PrintInt
        mov rax, rdx
        cmp rax, 0
        jle Subtract
        jmp TARGET

Pokemon10:
        lea rdx, Attack1
        call PrintZString
        lea rdx, Selected
        call PrintZString

        call ScanInt
        cmp rdx, 1
        je Punch10
        cmp rdx, 2
        je Beam10
        cmp rdx, 3
        je Power10
        cmp rdx, 0
        jle Pokemon10
        cmp rdx, 4
        jge Pokemon10

Punch10:
        lea rdx, Attack               
        call PrintZString
        mov rdx, 45                     
        call Random                    
        add rdx, 20                     
        mov rbx, rdx                    
        call PrintInt
        jmp Poke10

Beam10:
        lea rdx, Attack
        call PrintZString
        mov rdx, 65
        call Random
        add rdx, 1
        mov rbx, rdx
        call PrintInt
        jmp Poke10

Power10:
        lea rdx, Attack
        call PrintZString
        mov rdx, 55
        call Random
        add rdx, 10
        mov rbx, rdx
        call PrintInt
        jmp Poke10

Poke10:
        lea rdx, Option10
        call PrintZString
        lea rdx, HP
        call PrintZString

        sub Health10, rbx
        movq rdx, Health10
        call PrintInt
        mov rax, rdx
        cmp rax, 0
        jle Subtract
        jmp TARGET


Subtract:		
	lea rdx, Space
	call PrintZString
        sub rcx, 1		# Subtract one to rcx if a pokemon is unable to continue battle
        mov rdx, rcx
        cmp rdx, 1		# Compare rdx to one to determine if the game should be continue
        je Next		
        jne Continue		# If rdx is one the winner will be annouce

Next:				# Next label is for determining if we have a winner 
        lea rdx, Winner
        call PrintZString
        movq rdx, Health1	# Each Health is being compare to 0 to determine which one is greater 
        cmp rdx, 0
        jg Winner1
        movq rdx, Health2	# The Pokemon with a Health of greater than 0 will be announce
        cmp rdx, 0
        jg Winner2
        movq rdx, Health3
        cmp rdx, 0
        jg Winner3
        movq rdx, Health4
        cmp rdx, 0
        jg Winner4

	movq rdx, Health5
	cmp rdx, 0
	jg Winner5
	movq rdx, Health6
	cmp rdx, 0
	jg Winner6
	movq rdx, Health7
	cmp rdx, 0
	jg Winner7
	movq rdx, Health8
	cmp rdx, 0
	jg Winner8
	movq rdx, Health9
	cmp rdx, 0
	jg Winner9
	movq rdx, Health10
	cmp rdx, 0
	jg Winner10

Winner1:				# Print the name of the Pokemon
        lea rdx, Articuno
        call PrintZString
        jmp Done			# Jump into done after in order to announce the winner and end the game
Winner2:
        lea rdx, Zapdos
        call PrintZString
        jmp Done
Winner3:
        lea rdx, Moltres
        call PrintZString
        jmp Done
Winner4:
        lea rdx, Lugia
        call PrintZString
        jmp Done
Winner5:
	lea rdx, Meowth
	call PrintZString
	jmp Done
Winner6:
	lea rdx, Snorlax
	call PrintZString
	jmp Done
Winner7:
	lea rdx, Charizard
	call PrintZString
	jmp Done
Winner8:
	lea rdx, Gengar
	call PrintZString
	jmp Done
Winner9:
	lea rdx, Alakazam
	call PrintZString
	jmp Done
Winner10:
	lea rdx, Magikarp
	call PrintZString
	jmp Done
Done:
	lea rdx, Art1			# Print the cool looking Charizard 
	call PrintZString
        call Exit
