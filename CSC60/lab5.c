/*--------------------------------------------*/
/* Brian Hert                                 */
/* Lab 5                                      */
/* Figure the area of a parabola using files  */

#include <stdio.h>
#include <stdlib.h>

#define IN_FILE_NAME "lab5.dat"
#define OUT_FILE_NAME "lab5.txt"

int main(void) {
    double length, depth, area;
    FILE *infile = fopen(IN_FILE_NAME, "r");
    FILE *outfile = fopen(OUT_FILE_NAME, "w+");

    fprintf(outfile, "\nBrian Hert. Lab 5. \n\n");
    fprintf(outfile, "Data on Parabolas \n\n");
    fprintf(outfile, "  Length      Depth     Area\n");
    fprintf(outfile, " --------   --------   ---------\n");
                               
    while((fscanf(infile, "%lf%lf" ,&length,&depth)) == 2) {
      area = 2*length*depth/3;
      fprintf(outfile,"%7.2lf     %7.2lf  %10.31f\n",length,depth,area);
      
    }

    fprintf(outfile, "\n\n");
    
    fclose(infile);
    fclose(outfile);
    return EXIT_SUCCESS;
}
/*-------------------------------------------------*/