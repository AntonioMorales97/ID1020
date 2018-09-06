/*
*   "README" - comment.
*   The functions "iterative_reverse" and "recursive_reverse" both accomplish
*   the same task, to read the standard input stream until newline and put it
*   in the standard output stream reversed. The difference is the implementation
*   of the functions.
*
*   The "iterative_reverse" function iterates through the input stream until newline
*   saving each char in an array (up to 100), then it outputs each char of the array
*   starting from the last char to the output stream.
*
*   The "recursive_reverse" function reads the first char of the input stream and
*   if it is not equal to '\n' it will recursivly call itself until the char is equal
*   to newline '\n'. In that way everyting will be saved in the stack and thereby put
*   to the output stream in a reverse order.
*/
#include <stdio.h>
#include <stdlib.h>

void iterative_reverse(void)
{
    int size = 10;
    int i = 0;
    int j;
    char c;
    char *str = malloc(10*sizeof(char));

    while((c = getchar()) != '\n')
    {
        str[i++] = c;
        if(i >= size)
        {
            str = (char *) realloc(str, size*2);
            size = (size*2);
        }
    }

    for (j = i - 1; j >= 0; j--)
        putchar(str[j]);

    free (str);
}

void recursive_reverse(void)
{
    char c;
    if((c = getchar()) != '\n')
        recursive_reverse();
    else
        return; //avoid printing out the '\n' in the end.
    putchar(c);
}

int main(void)
{
    printf("Enter something to stdin (recursive reversed): \n");
    recursive_reverse();
    putchar('\n');
    printf("Enter something to stdin (iterative reversed): \n");
    iterative_reverse();
    putchar('\n');
}
