package lab1;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class StringStack {
    public static Stack<Character> stack = new Stack<Character>();
    public static void main(String[] args) {
        System.out.println("Type something (iterative reverse):");
        iterativeStack();
        System.out.println("Type something (recursive reverse): ");
        recursiveStack();
        System.out.println();
        System.out.println("Done!");
    }
    
    public static void iterativeStack() {
        Stack<Character> localStack = new Stack<Character>();
        char ch;
        
        while((ch = (char) StdIn.readChar()) != '\n')
            if(ch != '\n' && ch != '\r')
                localStack.push(ch);
        
        while(!localStack.isEmpty())
            StdOut.print(localStack.pop());
        
        StdOut.print('\n');
    }
    
    public static void recursiveStack() {
        char ch = StdIn.readChar();
        if(ch != '\n' && ch != '\r') {
            stack.push(ch);
            recursiveStack();
        } else
            return;
        StdOut.print(stack.pop());
    }
}
