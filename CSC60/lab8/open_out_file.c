/*----------------------------------------------------------*/
/* Brian Hert                                               */
/* Date: 4-23-2023 & Lab 8                                  */
/* This function will open the output file and return the   */
/* file pointer name to the main program.                   */

#include "lab8.h"

FILE * open_out_file (void)
{
    FILE * outfile;

    outfile = fopen(OUT_FILENAME, "w");
    if (outfile == NULL)
    {
        printf("Error on fopen of %s \n", OUT_FILENAME);
        exit(EXIT_FAILURE);
    }
    fprintf(outfile, "\nBrian Hert, Lab 8 output. \n");

    return outfile;
}

/*-----------------------------------------------------------*/
