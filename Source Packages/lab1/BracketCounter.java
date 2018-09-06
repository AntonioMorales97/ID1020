package lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BracketCounter {
    private final char leftCurlyBrack = '{';
    private final char rightCurlyBrack = '}';
    private final char leftSquareBrack = '[';
    private final char rightSquareBrack = ']';
    private final char leftRoundBrack = '(';
    private final char rightRoundBrack = ')';

    public boolean checkBalance(String input) {
        Stack<Character> stackOfChars = new Stack<Character>();
        char ch;
        for (int i = 0; i < input.length(); i++) {
            ch = input.charAt(i);

            if (ch == leftSquareBrack || ch == leftRoundBrack || ch == leftCurlyBrack) {
                stackOfChars.push(ch);
            } else if(ch == rightSquareBrack) {
                if(stackOfChars.isEmpty() || stackOfChars.pop() != leftSquareBrack)
                    return false;
            } else if(ch == rightRoundBrack) {
                if(stackOfChars.isEmpty() || stackOfChars.pop() != leftRoundBrack)
                    return false;
            } else if (ch == rightCurlyBrack) {
                if(stackOfChars.isEmpty() || stackOfChars.pop() != leftCurlyBrack)
                    return false;
            }
        }
        return stackOfChars.isEmpty();      
    }
    
    public static void main (String[] args) throws IOException {
        BracketCounter counter = new BracketCounter();
        StringBuilder s = new StringBuilder();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String str;
        StdOut.println("Write something here and press Ctrl + Z to terminate the StdIn:");
        while ((str = input.readLine()) != null)
            s.append(str);
        StdOut.println("Entered: " + s.toString());
        boolean balanced = counter.checkBalance(s.toString());
        StdOut.println("Are brackets balanced?: " + balanced);
    }
}
