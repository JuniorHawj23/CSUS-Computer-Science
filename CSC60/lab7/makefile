# Brian Hert lab 7

CC = gcc
range: lab7.o get_data.o print_all.o get_range_count.o lab7.h
	gcc lab7.o get_data.o print_all.o get_range_count.o -o range -lm
	
lab7.o: lab7.c lab7.h
	gcc -c lab7.c

get_data.o: get_data.c lab7.h
	gcc -c get_data.c
	
print_all.o: print_all.c lab7.h
	gcc -c print_all.c

get_range_count.o: get_range_count.c lab7.h
	gcc -c get_range_count.c        
