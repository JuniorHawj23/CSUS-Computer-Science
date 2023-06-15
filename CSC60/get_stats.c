/*-------------------------------------------------------------------*/
/* Brian Hert                                                        */ 
/* 4-23-2023                                                         */
/* Ruthann Biel                                                      */
/* get_stats_me is a function to do figure the best throw for each   */
/* thrower, compute the all-over average of the best throws, and     */
/* find the longest throw  on the track and each thrower's deviation */
/* from the winning throw                                            */

#include "lab8.h"

void get_stats(thrower_t throw_list[NCOMPETITORS], /* in & out */
        stats_t *throw_stats)           	       /* output   */
{ 

    int r,c;

    throw_stats->average_of_best_throws = 0;
    throw_stats->winning_throw = 0;

    for(r=0; r<NCOMPETITORS; r++) {

        throw_list[r].best_throw = throw_list[r].tries[0];
        for(c=1; c<N_TRIES; c++) {

            if(throw_list[r].tries[c] > throw_list[r].best_throw)  
                throw_list[r].best_throw = throw_list[r].tries[c];
        }

        throw_stats -> average_of_best_throws += throw_list[r].best_throw;

            if(throw_stats -> winning_throw < throw_list[r].best_throw) 
                throw_stats -> winning_throw = throw_list[r].best_throw;
        
         }
        
        for(r=0; r<NCOMPETITORS; r++) {

            throw_list[r].deviation = throw_stats->winning_throw - throw_list[r].best_throw;
        }

        throw_stats -> average_of_best_throws /= NCOMPETITORS;

        return; 
} 
/*--------------------------------------------------------*/
