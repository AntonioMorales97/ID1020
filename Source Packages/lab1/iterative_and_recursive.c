/*
*   "README" - comment.
*   The functions "iterative_reverse" and "recursive_reverse" both accomplish
*   the same task; to read the standard input stream until newline and put it
*   in the standard output stream reversed. The difference is the implementation
*   of the functions.
*
*   The "iterative_reverse" function iterates through the input stream until newline
*   saving each char in an array and if necessary reallocates more memory, then it
*   outputs each char of the array starting from the last char to the output stream.
*
*   The "recursive_reverse" function reads the first char of the input stream and
*   if it is not equal to '\n' it will recursivly call itself until the char is equal
*   to newline '\n'. In that way everyting will be saved in the stack and thereby put
*   to the output stream in a reverse order.
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define INIT_ARRAY_SIZE 100

/*
* Allocates initial memory and reads each char from stdin until newline is read.
* Then prints it out in the reversed order by iterating from the last char to the first.
*/
void iterative_reverse(void)
{
    int i = 0;
    char c;
    char *str = malloc(INIT_ARRAY_SIZE*sizeof(char));

    while((c = getchar()) != '\n')
    {
        str[i++] = c;
        if(i >= strlen(str))
            str = (char *) realloc(str, strlen(str)*2);
    }

    for (i = i - 1; i >= 0; i--)
        putchar(str[i]);

    free (str);
}

/*
* Reads each char and if it is not a newline it calls itself recursivly. This will
* save each char and the caller in a stack and when newline is reached it will return
* to the caller function and print out the char that was read. In this way the chars
* will be printed out in a reversed order.
*/
void recursive_reverse(void)
{
    char c;
    if((c = getchar()) != '\n')
        recursive_reverse();
    putchar(c);
}

int main(int argc, const char* argv[])
{
    printf("Enter something to stdin (recursive reversed): \n");
    recursive_reverse();
    putchar('\n');
    printf("Enter something to stdin (iterative reversed): \n");
    iterative_reverse();
    putchar('\n');
    return 0;
}
