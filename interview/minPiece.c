#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

int min_pieces(int* original, int* desired, int length)
{
    int stripe = 1;

    int j=0;
    for(int i=0; i<length; i++){
        if(j<length && original[j] == desired[i]){
            j++;
            continue;
        }else{
            stripe++;
            for(j=i;j<length;j++){
                if(original[j] == desired[i]){
                    break;
                }
            }
        }
    }
    return stripe;
}

#ifndef RunTests
int main()
{
    int original[] = {1, 4, 3, 2};
    int desired[] = {1, 2, 4, 3};
    printf("%d\n", min_pieces(original, desired, 4)); //output 3: (1, (3,4), 2)

    return 0;
}
#endif