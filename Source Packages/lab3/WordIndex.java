package lab3;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.StdIn;

/**
 * A program that puts the index of each word counting from the number of
 * characters from the beginning of stdin using a {@link RedBlackTree}. For
 * example if a word X occurs in several places the tree will hold all of its
 * indices in a <code>List</code>.
 * 
 * @author Antonio
 *
 */
public class WordIndex {

    /**
     * A test client that asks for a word and all of its indices are printed out;
     * where each index is the number of characters from the beginning of stdin.
     * 
     * @param args args[0] is the word to be searched for.
     */
    public static void main(String[] args) {
        String word = args[0];
        RedBlackTree<String, List<Integer>> tree = new RedBlackTree<String, List<Integer>>();

        char c;
        StringBuilder str = new StringBuilder();
        int index = 0;
        String currentWord;

        while (!StdIn.isEmpty()) {
            c = StdIn.readChar();
            if (Character.isAlphabetic(c)) {
                str.append(c);
            } else {
                currentWord = str.toString().toLowerCase();
                if (!tree.contains(currentWord))
                    tree.put(currentWord, new ArrayList<Integer>());
                List<Integer> list = tree.get(currentWord);
                list.add(index - currentWord.length());
                str.delete(0, str.length());
            }
            index++;
        }

        System.out.println("The word \"" + word + "\" occurs in following " + "positions (chars from the beginning):\n"
                + tree.get(word));
    }

}
