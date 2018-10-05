package lab3;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import lab1.GeneralizedQueueArray;

/**
 * A system table implemented as a binary search tree. Only the necessary
 * methods for this task are included in this class. The main method includes a
 * frequency counter of keys and time measurements for comparisons to other
 * algorithms.
 * 
 * @author Antonio
 *
 * @param <Key> For this task the keys are words from stdin.
 * @param <Value> For this task the values are number of occurrences of the key
 *        from stdin.
 */
public class BinarySearchTree<Key extends Comparable<Key>, Value> {
    private Node root;

    private class Node {
        private Key key;
        private Value value;
        private Node left;
        private Node right;
        private int size;

        public Node(Key key, Value value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    /**
     * Initializes the tree.
     */
    public BinarySearchTree() {
    }

    /**
     * Checks if the tree is empty.
     * 
     * @return <code>true</code> if it is empty, <code>false</code> otherwise.
     */
    public boolean isEmpty() {
        return size(root) == 0;
    }

    private int size(Node node) {
        if (node == null)
            return 0;
        return node.size;
    }

    /**
     * Puts the key in the correct position in the binary search tree. If it is
     * already in the tree the value of the key will be updated with the new value.
     * 
     * @param key   The key to be put/updated in the table.
     * @param value The value of the key to be put/updated.
     * @throws IllegalArgumentException if the key is <code>null</code>.
     */
    public void put(Key key, Value value) {
        if (key == null)
            throw new IllegalArgumentException("Key is null.");
        root = put(root, key, value);
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null)
            return new Node(key, value, 1);

        int compareResult = key.compareTo(node.key);
        if (compareResult < 0)
            node.left = put(node.left, key, value);
        else if (compareResult > 0)
            node.right = put(node.right, key, value);
        else
            node.value = value;

        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    /**
     * Searches for the given key in the tree and returns its value if found.
     * 
     * @param key The key to be searched for.
     * @return the value of the key. Returns <code>null</code> if the table is
     *         empty.
     * @throws IllegalArgumentException if the key is <code>null</code>.
     */

    public Value get(Key key) {
        if (key == null)
            throw new IllegalArgumentException("Key is null.");
        return get(root, key);
    }

    private Value get(Node node, Key key) {
        if (node == null)
            return null;
        int compareResult = key.compareTo(node.key);
        if (compareResult < 0)
            return get(node.left, key);
        else if (compareResult > 0)
            return get(node.right, key);
        else
            return node.value;
    }

    /**
     * Checks if the tree already contains the given key.
     * 
     * @param key The key to be searched for.
     * @return true if the tree contains the key, false otherwise.
     * @throws IllegalArgumentException if the key is <code>null</code>.
     */
    public boolean contains(Key key) {
        if (key == null)
            throw new IllegalArgumentException("Key is null.");
        return get(key) != null;
    }

    /**
     * Gets the maximum key; meaning the key leaning most to the right.
     * 
     * @return the maximum key.
     * @throws NoSuchElementException if the tree is empty.
     */
    public Key max() {
        if (isEmpty())
            throw new NoSuchElementException("The BST is empty.");
        Key maxKey = max(root);
        return maxKey;
    }

    private Key max(Node node) {
        if (node.right == null)
            return node.key;
        else
            return max(node.right);
    }

    /**
     * Gets the minimum key; meaning the key leaning most to the left.
     * 
     * @return the minimum key.
     * @throws NoSuchElementException is the tree is empty.
     */
    public Key min() {
        if (isEmpty())
            throw new NoSuchElementException("The BST is emtpy.");
        Key minKey = min(root);
        return minKey;
    }

    private Key min(Node node) {
        if (node.left == null)
            return node.key;
        else
            return min(node.left);
    }

    /**
     * @return an iterable queue holding all of the keys.
     */
    public Iterable<Key> keys() {
        if (isEmpty())
            return new GeneralizedQueueArray<Key>();
        return keys(min(), max());
    }

    /**
     * Inserts all keys in the given key interval into an iterable queue.
     * 
     * @param low  Low limit.
     * @param high High limit.
     * @return an iterable queue holding the the keys in the given range.
     * @throws IllegalArgumentException if one of the keys is <code>null</code>.
     */
    public Iterable<Key> keys(Key low, Key high) {
        if (low == null)
            throw new IllegalArgumentException("Low key is null.");
        if (high == null)
            throw new IllegalArgumentException("High key is null.");

        GeneralizedQueueArray<Key> queue = new GeneralizedQueueArray<Key>();
        keys(root, queue, low, high);
        return queue;
    }

    private void keys(Node node, GeneralizedQueueArray<Key> queue, Key low, Key high) {
        if (node == null)
            return;
        int compareLow = low.compareTo(node.key);
        int compareHigh = high.compareTo(node.key);
        if (compareLow < 0)
            keys(node.left, queue, low, high);
        if ((compareLow <= 0) && (compareHigh >= 0))
            queue.insert(node.key);
        if (compareHigh > 0)
            keys(node.right, queue, low, high);
    }

    /**
     * Builds a {@link BinarySearchValueTree}, similar to a binary search tree but
     * instead the keys are this tree's values and the values are this tree's keys,
     * from this tree and returns the max.
     * 
     * @return {@link WordCount} object that holds the value and the keys.
     * @throws NoSuchElementException if the tree is empty.
     */
    public WordCount<Integer, String> maxWordCount() {
        if (isEmpty())
            throw new NoSuchElementException("The BST is empty.");

        BinarySearchValueTree<Integer, String> valueTree = new BinarySearchValueTree<Integer, String>();
        buildValueTree(valueTree, root, min(), max());
        return valueTree.maxWord();
    }

    /**
     * Builds a {@link BinarySearchValueTree} from this tree and returns it.
     * 
     * @return the value tree.
     * @throws NoSuchElementException if the tree is empty.
     */
    public BinarySearchValueTree<Integer, String> getValueTree() {
        if (isEmpty())
            throw new NoSuchElementException("Tree is empty.");
        BinarySearchValueTree<Integer, String> valueTree = new BinarySearchValueTree<Integer, String>();
        buildValueTree(valueTree, root, min(), max());
        return valueTree;
    }

    private void buildValueTree(BinarySearchValueTree<Integer, String> valueTree, Node node, Key low, Key high) {
        if (node == null)
            return;
        int compareLow = low.compareTo(node.key);
        int compareHigh = high.compareTo(node.key);
        if (compareLow < 0)
            buildValueTree(valueTree, node.left, low, high);
        if (compareLow <= 0 && compareHigh >= 0)
            valueTree.put((Integer) node.value, (String) node.key);
        if (compareHigh > 0)
            buildValueTree(valueTree, node.right, low, high);
    }

    /**
     * Builds a {@link BinarySearchValueTree} and prints out the nth to n+xth most
     * frequent words; where 1 is the most frequent word.
     * 
     * @param start Start position.
     * @param end   End position.
     * @throws IllegalArgumentException if the range is invalid.
     */
    public void getWordsInRange(int start, int end) {
        if (((start < 1 || start > root.size)) || ((end < start) || (end > root.size)))
            throw new IllegalArgumentException("Not valid range!");

        BinarySearchValueTree<Integer, String> valueTree = new BinarySearchValueTree<Integer, String>();
        buildValueTree(valueTree, root, min(), max());
        GeneralizedQueueArray<String> orderedQueue = valueTree.ascendingQueue();
        for (int i = start - 1; i < end; i++)
            System.out.println("Rank " + (i + 1) + ": " + orderedQueue.get(i));
    }

    /**
     * A text client with a frequency counter to count the words from stdin and
     * measure the time it takes for operations. Uses the
     * <code>BinarySearchTree</code> to count and store. The data printed out can be
     * used to compare with other system tables.
     * 
     * @param args args[0] The length of the stdin to read. The whole stdin will be
     *             read if 0. args[1] and args[2] is the interval range to get the
     *             nth to n+xth most frequent words, where 1 is the most frequent
     *             word.
     * @throws IllegalArgumentException if the given length is negative.
     */
    public static void main(String[] args) {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, Integer>();
        int wordLimit = Integer.parseInt(args[0]);
        if (wordLimit < 0)
            throw new IllegalArgumentException("Please enter a valid lenght!");

        // For getting the nth to n+xth most frequent words
        int lowLimit = Integer.parseInt(args[1]);
        int highLimit = Integer.parseInt(args[2]);

        String key;
        int words = 0;
        int distinctWords = 0;
        long totalTime = 0;
        long startTime;

        if (wordLimit == 0) {
            while (!StdIn.isEmpty()) {
                key = StdIn.readString().toLowerCase();
                if (key.length() < 2)
                    continue;

                words++;
                startTime = System.nanoTime();
                if (bst.contains(key)) {
                    bst.put(key, bst.get(key) + 1);
                } else {
                    bst.put(key, 1);
                    distinctWords++;
                }
                totalTime += System.nanoTime() - startTime;
            }
        } else {
            while (words < wordLimit) {
                key = StdIn.readString().toLowerCase();
                if (key.length() < 2)
                    continue;

                words++;
                startTime = System.nanoTime();
                if (bst.contains(key)) {
                    bst.put(key, bst.get(key) + 1);
                } else {
                    bst.put(key, 1);
                    distinctWords++;
                }
                totalTime += System.nanoTime() - startTime;
            }
        }

        double time = (double) totalTime / 1E9;
        System.out.println("Words: " + words + "\nDisctinct Words: " + distinctWords);
        System.out.printf("Execution time (s) for counting words: %.9f\n", time);

        totalTime = 0; // reset
        String max = "";
        bst.put(max, 0);
        startTime = System.nanoTime();
        for (String word : bst.keys())
            if (bst.get(word) > bst.get(max))
                max = word;
        totalTime = System.nanoTime() - startTime;
        time = (double) totalTime / 1E9;
        System.out.println("Word with max count: " + max);
        System.out.printf("Execution time (s) for finding max word: %.9f\n", time);

        System.out.println("TOP 5 Words:");
        totalTime = 0; // reset
        startTime = System.nanoTime();
        bst.getWordsInRange(lowLimit, highLimit);
        totalTime = System.nanoTime() - startTime;
        time = (double) totalTime / 1E9;
        System.out.printf("Execution time (s) for building value tree and getting top X words: %.9f\n", time);

        totalTime = 0; // reset
        startTime = System.nanoTime();
        BinarySearchValueTree<Integer, String> valueTree = bst.getValueTree();
        WordCount<Integer, String> maxWord = valueTree.maxWord();
        totalTime = System.nanoTime() - startTime;
        time = (double) totalTime / 1E9;
        System.out.println(maxWord);
        System.out.printf("Execution time (s) for building value tree and getting the max word: %.9f\n", time);

        totalTime = 0; // reset
        startTime = System.nanoTime();
        maxWord = valueTree.maxWord();
        totalTime = System.nanoTime() - startTime;
        time = (double) totalTime / 1E9;
        System.out.println(maxWord);
        System.out.printf("Execution time (s) for getting the max word in an already existing value tree: %.9f\n",
                time);

    }

}
