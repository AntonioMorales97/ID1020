package lab1;

import java.util.Scanner;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * A program that checks if the brackets ("(){}[]") are balanced or not, using a
 * stack ADT that will only hold brackets.
 * 
 * @author Antonio
 *
 */
public class BracketCounter {
    private final char leftCurlyBrack = '{';
    private final char rightCurlyBrack = '}';
    private final char leftSquareBrack = '[';
    private final char rightSquareBrack = ']';
    private final char leftRoundBrack = '(';
    private final char rightRoundBrack = ')';

    /**
     * Creates a <code>Stack</code> ADT and iterates through every character in the
     * given string pushing only "left" brackets, meaning the brackets in the left
     * position such as "{[(". If a "right" bracket is found, then the popped
     * character from the stack must be its "left" bracket. Otherwise it is not
     * balanced. Ex: '}' is found, and the the popped bracket must be '{'.
     * 
     * @param input
     *            the string that will be check for bracket balance.
     * @return true if it is balanced, false if not.
     */
    public boolean checkBalance(String input) {
        Stack<Character> stackOfChars = new Stack<Character>();
        char ch;
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);

            if (ch == leftSquareBrack || ch == leftRoundBrack || ch == leftCurlyBrack) {
                stackOfChars.push(ch);
            } else if (ch == rightSquareBrack) {
                if (stackOfChars.isEmpty() || stackOfChars.pop() != leftSquareBrack)
                    return false;
            } else if (ch == rightRoundBrack) {
                if (stackOfChars.isEmpty() || stackOfChars.pop() != leftRoundBrack)
                    return false;
            } else if (ch == rightCurlyBrack) {
                if (stackOfChars.isEmpty() || stackOfChars.pop() != leftCurlyBrack)
                    return false;
            }
        }
        return stackOfChars.isEmpty();
    }

    /**
     * A test client that reads the chars from the stdin until EOF (or if user
     * presses Ctrl + Z) and sends it as a string to check if the brackets are
     * balanced or not.
     * 
     * @param args
     *            Input from command line, not used here.
     */
    public static void main(String[] args) {
        BracketCounter counter = new BracketCounter();
        StringBuilder s = new StringBuilder();
        StdOut.println("Write something here and press Ctrl + Z to terminate the StdIn:");
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine())
            s.append(scan.nextLine());
        scan.close();
        StdOut.println("Entered: " + s.toString());
        boolean balanced = counter.checkBalance(s.toString());
        StdOut.println("Are brackets balanced?: " + balanced);
    }
}
