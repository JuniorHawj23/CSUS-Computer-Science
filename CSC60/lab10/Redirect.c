/* Author(s): Brian Hert    */
/* Date: 5-19-2023 & Lab 10 */
/* ----------------------------------------------------------------- */
/*                  Redirect, part of a child process                */
/* ----------------------------------------------------------------- */

#include "lab9_10.h"
#include<stdlib.h>
#include<unistd.h>
#include<stdio.h>
#include<fcntl.h>

void Redirect(int argc, char *argv[])
{
    int i;        // loop counter
    int out = 0;  // track position of location Out redirection, >
    int in = 0;   // track position of location In redirection, <
    int fd;

    for(i=0; i<argc; i++) 
    {
        if(strcmp(argv[i], ">")==0) 
        {
            if(out != 0) 
            {
                fprintf(stderr, "Cannot output to more than one file.\n");
                _exit(EXIT_FAILURE);
            }
            else if(i==0) 
            {
                fprintf(stderr, "No command entered.\n");
                _exit(EXIT_FAILURE);
            } 
            out = i;
        }
        else if(strcmp(argv[i],"<")==0) 
        {
            if(in != 0) 
            {
                fprintf(stderr, "Cannot input to more than one file.\n");
                _exit(EXIT_FAILURE);
            }
            else if(i==0) {
                fprintf(stderr, "No command entered.\n");
                _exit(EXIT_FAILURE);
            }
            in = i;
        }
    }

    if(out !=0) {
        if(argv[out+1] == '\0') {
            fprintf(stderr, "There is no file.\n");
            _exit(EXIT_FAILURE);
        }
        fd = open(argv[out+1], O_RDWR | O_CREAT | O_TRUNC, S_IRUSR | S_IWUSR);
        if(fd==-1){
            fprintf(stderr, "Cannot open/create file./n");
            _exit(EXIT_FAILURE);
        }
        dup2(fd, 1);
        if(close(fd) == -1) { 
            perror("Error closing file\n");
            _exit(EXIT_FAILURE);
        }      
        argv[out] = NULL;
    } 
    if(in != 0)
    {
        if(argv[in + 1] == NULL)
        {
            fprintf(stderr, "No file specified.\n");
            _exit(EXIT_FAILURE);
        }
        fd = open(argv[in+1], O_RDONLY);
        if(fd==-1)
        {
            fprintf(stderr, "Cannot open/create file.\n");
            _exit(EXIT_FAILURE);
        }
        dup2(fd, 0);
        if( close(fd) == -1)
        {
            perror("Error closing file\n");
            exit(EXIT_FAILURE);
        }
        argv[in] = NULL;
    }
}

/*end of function*/
