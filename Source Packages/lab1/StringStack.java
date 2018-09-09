package lab1;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * This program reads the user input from stdin and prints them out to stdout in
 * reversed order in two ways. First by an iterative method using a Stack where
 * is pushes every character until newline ('\n' or '\r') and then pops
 * everything until stack is empty. The second way is by a recursive method
 * where it pushes everything to a static stack and calls itself until newline
 * is read and then pops. In this program, for simplicity, the Stack object for
 * the recursive method is kept static.
 * 
 * @author Antonio
 *
 */
public class StringStack {
    public static Stack<Character> stack = new Stack<Character>();

    /**
     * Just a test client that takes some input from user and prints out the result.
     * 
     * @param args
     *            Not used here.
     */
    public static void main(String[] args) {
        System.out.println("Type something (iterative reverse):");
        iterativeStack();
        System.out.println("Type something (recursive reverse): ");
        recursiveStack();
        System.out.println();
        System.out.println("Done!");
    }

    /**
     * Iterative method.
     */
    public static void iterativeStack() {
        Stack<Character> localStack = new Stack<Character>();
        char ch;

        while ((ch = (char) StdIn.readChar()) != '\n')
            if (ch != '\r') // Otherwise an empty line will also be pushed...
                localStack.push(ch);

        while (!localStack.isEmpty())
            StdOut.print(localStack.pop());

        StdOut.println();
    }

    /**
     * Recursive method.
     */
    public static void recursiveStack() {
        char ch = StdIn.readChar();
        if (ch != '\n' && ch != '\r') {
            stack.push(ch);
            recursiveStack();
        } else
            return;
        StdOut.print(stack.pop());
    }
}
