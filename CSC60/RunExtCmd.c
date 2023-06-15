/* ----------------------------------------------------------------- */
/*                 RunExtCmd                            CLASS        */
/*------------------------------------------------------------------ */
/* Brian Hert      */
/* Date: 5-19-2023 */

#include "lab9_10.h"

void RunExtCmd(int argc, char **argv) 
{                             
    // int ret;
    //Add the call to the function Redirect

    Redirect(argc, argv);

    // Add the call to execvp

    int ret = execvp(argv[0], argv);
    
    if (ret == -1)    // error check for the exec call
    {                                       
        fprintf(stderr, "Error on the exec call\n");             
        _exit(EXIT_FAILURE);                                   
    }                                                         
                                                          
    return;
}
/* ----------------------------------------------------------------- */
