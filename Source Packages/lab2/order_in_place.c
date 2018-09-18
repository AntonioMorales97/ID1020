#include<stdio.h>
#include<stdlib.h>
#include <ctype.h>
/*
* READ_ME comment:
* This program will take a first input from the user which will be the N size of
* an array and then the N numbers. After receiving the numbers it will put them in
* an array and then sort the negative integers to the left side of the array and the
* positive integers to the right.
*/

/*
* This function will sort the given array so the negative integers will be
* placed on the "left side" of the array and the positive integers will be placed
* on the "right side" of the array. It works by having an extra variable j which
* will hold the position of the "left limit" or in other words; where to put the
* next negative integer that is found. Time complexity: O(n), since it iterates
* through the array only once.
*
* Loop invariant: j <= i. This will always be true since j will be the index where
* to put the next negative integer, starting at 0 and increasing everytime a new negative
* integer is found, which will never be greater than i.
*
*/
static void sort_positive_negative(int* array, int size)
{
    if(size == 1)
        return;
    int i;
    int j = 0;
    int tempInt;
    for (i = 0; i < size; i++)
    {
        if(array[i] < 0)
        {
            tempInt = array[i];
            array[i] = array[j];
            array[j++] = tempInt;
        }
    }
}

/*
* Takes input from the user and sorts the negative integers from the positive integers in
* the array using the function above. Finally the array will be printed out.
*/
int main (int argc, char* argv[])
{
    int i;
    int size;
    int number;

    printf("Enter size of array:\n");
    scanf("%d", &size);
    if(size <= 0)
    {
        printf("We are done here...\n");
        return 1;
    }

    int *array = malloc(sizeof(int)*size);
    printf("Enter the integers:\n");
    for(i = 0; i < size; i++)
    {
        scanf("%d", &number);
        array[i] = number;
    }

    sort_positive_negative(array, size);
    for(i = 0; i < size-1; i++)
        printf("[%d], ", array[i]);
    printf("[%d]\n", array[i]);
    free(array);
    return 0;
}
