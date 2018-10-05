#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

/*
* Just a program that reads from stdin and sends to stdout every character that is
* alphabetical or a newline. Can be used to filter a textfile to only have alphabetical
* chars by typing the following in the command line:
* alphabetic_filter < thetext.txt > thetextalpha.txt
*/

/*
* A function that reads from stdin, preferably from a file, until EOF and sends
* the alphabetical chars to stdout.
*/
void read_stdin(void)
{
    int c;
    while((c = fgetc(stdin))!=EOF)
    {
        if(isalpha(c) || c == '\n')
            printf("%c", c);
        else
            printf("%c", ' ');
    }
}

/*
* Simple text client that only calls the read_stdin function.
*/
int main (int argc, char* argv[])
{
    read_stdin();
}
