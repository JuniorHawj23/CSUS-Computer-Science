# Brian Hert Lab 10 part 2
/* Date: 5-19-2023      */

CC = gcc
lab10: lab10.o ParseCmd.o ProcessExtCmd.o Redirect.o RunExtCmd.o
	gcc lab10.o ParseCmd.o ProcessExtCmd.o Redirect.o RunExtCmd.o -o lab10
	
lab10.o: lab10.c lab9_10.h
	gcc -c lab10.c -Wall
	
ParseCmd.o: ParseCmd.c lab9_10.h
	gcc -c ParseCmd.c -Wall
	
ProcessExtCmd.o: ProcessExtCmd.c lab9_10.h
	gcc -c ProcessExtCmd.c -Wall
	
Redirct.o: Redirect.c lab9_10.h
	gcc -c Redirect.c -Wall
	
RunExtCmd.o: RunExtCmd.c lab9_10.h
	gcc -c RunExtCmd.c -Wall
