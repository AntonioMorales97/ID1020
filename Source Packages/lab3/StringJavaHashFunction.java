package lab3;

import edu.princeton.cs.algs4.StdIn;

/**
 * A program that shows how evenly the java built-in hash function for
 * <code>String</code> distributes the hashes for the words found in the stdin.
 * 
 * @author Antonio
 *
 */
public class StringJavaHashFunction {
    private static final int M = 97;

    private static int hash(String str) {
        return (str.hashCode() & 0x7fffffff) % M;
    }

    /**
     * A test client that uses a {@link RedBlackTree} to count and store the words
     * found. After a new word is found it hashes the <code>String</code> into a
     * position in an array of size M (97) and increments that position with one.
     * Finally it iterates through that array and prints out each value to show how
     * evenly distributed the words would have been if using a real hash table.
     * 
     * @param args Not used here.
     */
    public static void main(String[] args) {
        RedBlackTree<String, Integer> tree = new RedBlackTree<String, Integer>();
        int[] hashCountArray = new int[M];
        String key;
        int wordCount = 0;
        int distinct = 0;
        while (!StdIn.isEmpty()) {
            key = StdIn.readString().toLowerCase();
            if (key.length() < 2)
                continue;

            wordCount++;
            if (tree.contains(key)) {
                tree.put(key, tree.get(key) + 1);
            } else {
                tree.put(key, 1);
                distinct++;
                hashCountArray[hash(key)]++;
            }
        }

        for (int i = 0; i < M; i++)
            System.out.println(hashCountArray[i]);
        System.out.println("Total words: " + wordCount + "\nDistinct words: " + distinct);
    }
}
