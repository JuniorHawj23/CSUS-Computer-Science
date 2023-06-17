# Brian Hert lab 7
// Date: 4-7-2023

lab7: lab7.o get_range_count.o
	gcc lab7.o get_range_count.o -o lab7 -lm

lab7.o: lab7.c lab7.h
	gcc -c lab7.c

get_range_count.o: get_range_count.c lab7.h
	gcc -c get_range_count.c 
