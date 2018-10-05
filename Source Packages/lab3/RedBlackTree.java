package lab3;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import lab1.GeneralizedQueueArray;

/**
 * A system table implemented as a red black tree. Only the necessary methods
 * for this task are included in this class. The main method includes a
 * frequency counter of keys and time measurements for comparisons to other
 * algorithms.
 * 
 * @author Antonio
 *
 * @param <Key> For this task the keys are words from stdin.
 * @param <Value> For this task the values are number of occurrences of the key
 *        from stdin.
 */
public class RedBlackTree<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    private class Node {
        private Key key;
        private Value value;
        private Node left;
        private Node right;
        private boolean color;
        private int size;

        /**
         * Creates a <code>Node</code>.
         * 
         * @param key   the key to be put.
         * @param value the value of the key to be put.
         * @param color the color; meaning the link to its parent.
         * @param size  the size of the subtree.
         */
        public Node(Key key, Value value, boolean color, int size) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.size = size;
        }
    }

    /**
     * Initializes the tree.
     */
    public RedBlackTree() {
    }

    private boolean isRed(Node node) {
        if (node == null)
            return false;
        return node.color == RED;
    }

    /**
     * @return the size of the tree.
     */
    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null)
            return 0;
        return node.size;
    }

    /**
     * Checks if the tree is empty.
     * 
     * @return <code>true</code> if the tree is empty, <code>false</code> otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Searches for the key and the returns its value if found.
     * 
     * @param key the key to be searched for.
     * @return the value of the key.
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
     * Checks if the tree contains the key.
     * 
     * @param key the key to be searched for.
     * @return <code>true</code> if the tree contains the tree. <code>false</code>
     *         otherwise.
     */
    public boolean contains(Key key) {
        return get(key) != null;
    }

    /**
     * Puts the key in the correct position in the red black tree. If it is already
     * in the tree the value of the key will be updated with the new value. Makes
     * sure that the root is black and that the tree is correctly balanced.
     * 
     * @param key   The key to be put/updated in the table.
     * @param value The value of the key to be put/updated.
     * @throws IllegalArgumentException if the key is <code>null</code>.
     */
    public void put(Key key, Value value) {
        if (key == null)
            throw new IllegalArgumentException("Key is null");

        root = put(root, key, value);
        root.color = BLACK;
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null)
            return new Node(key, value, RED, 1);

        int compareResult = key.compareTo(node.key);
        if (compareResult < 0)
            node.left = put(node.left, key, value);
        else if (compareResult > 0)
            node.right = put(node.right, key, value);
        else
            node.value = value;

        if (isRed(node.right) && !isRed(node.left))
            node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left))
            node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right))
            flipColors(node);
        node.size = 1 + size(node.left) + size(node.right);

        return node;
    }

    private Node rotateLeft(Node rotateNode) {
        Node node = rotateNode.right;
        rotateNode.right = node.left;
        node.left = rotateNode;
        node.color = RED;
        node.size = rotateNode.size;
        rotateNode.size = 1 + size(rotateNode.left) + size(rotateNode.right);
        return node;
    }

    private Node rotateRight(Node rotateNode) {
        Node node = rotateNode.left;
        rotateNode.left = node.right;
        node.right = rotateNode;
        node.color = RED;
        node.size = rotateNode.size;
        rotateNode.size = 1 + size(rotateNode.left) + size(rotateNode.right);
        return node;
    }

    // Parent must have opposite color of its two children
    private void flipColors(Node node) {
        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
    }

    /**
     * Gets the maximum key; meaning the key leaning most to the right.
     * 
     * @return the maximum key.
     * @throws NoSuchElementException if the tree is empty.
     */
    public Key max() {
        if (isEmpty())
            throw new NoSuchElementException("Tree is empty.");
        Key maxKey = max(root);
        return maxKey;
    }

    private Key max(Node node) {
        if (node.right == null)
            return node.key;
        return max(node.right);
    }

    /**
     * Gets the minimum key; meaning the key leaning most to the left.
     * 
     * @return the minimum key.
     * @throws NoSuchElementException if the tree is empty.
     */
    public Key min() {
        if (isEmpty())
            throw new NoSuchElementException("Tree is empty.");
        Key minKey = min(root);
        return minKey;
    }

    private Key min(Node node) {
        if (node.left == null)
            return node.key;
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
     * Builds a {@link RedBlackValueTree}, similar to a red black tree but instead
     * the keys are this tree's values and the values are this tree's keys, from
     * this tree and returns the max.
     * 
     * @return {@link WordCount} object that holds the value and the keys.
     * @throws NoSuchElementException if the tree is empty.
     */
    public WordCount<Integer, String> maxWordCount() {
        if (isEmpty())
            throw new NoSuchElementException("Tree is empty.");

        RedBlackValueTree<Integer, String> valueTree = new RedBlackValueTree<Integer, String>();
        buildRedBlackValueTree(valueTree, root, min(), max());
        return valueTree.maxWord();
    }

    /**
     * Builds a {@link RedBlackValueTree} from this tree and returns it.
     * 
     * @return the value tree.
     * @throws NoSuchElementException if the tree is empty.
     */
    public RedBlackValueTree<Integer, String> getValueTree() {
        if (isEmpty())
            throw new NoSuchElementException("Tree is empty.");
        RedBlackValueTree<Integer, String> valueTree = new RedBlackValueTree<Integer, String>();
        buildRedBlackValueTree(valueTree, root, min(), max());
        return valueTree;
    }

    private void buildRedBlackValueTree(RedBlackValueTree<Integer, String> valueTree, Node node, Key low, Key high) {
        if (node == null)
            return;
        int compareLow = low.compareTo(node.key);
        int compareHigh = high.compareTo(node.key);
        if (compareLow < 0)
            buildRedBlackValueTree(valueTree, node.left, low, high);
        if (compareLow <= 0 && compareHigh >= 0)
            valueTree.put((Integer) node.value, (String) node.key);
        if (compareHigh > 0)
            buildRedBlackValueTree(valueTree, node.right, low, high);

    }

    /**
     * A text client with a frequency counter to count the words from stdin and
     * measure the time it takes for operations. Uses the <code>RedBlackTree</code>
     * to count and store. The data printed out can be used to compare with other
     * system tables.
     * 
     * @param args args[0] The length of the stdin to read. The whole stdin will be
     *             read if 0.
     * @throws IllegalArgumentException if the given length is negative.
     */
    public static void main(String[] args) {
        RedBlackTree<String, Integer> redBlackTree = new RedBlackTree<String, Integer>();
        int wordLimit = Integer.parseInt(args[0]);
        if (wordLimit < 0)
            throw new IllegalArgumentException("Please enter a valid lenght!");

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
                if (redBlackTree.contains(key)) {
                    redBlackTree.put(key, redBlackTree.get(key) + 1);
                } else {
                    redBlackTree.put(key, 1);
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
                if (redBlackTree.contains(key)) {
                    redBlackTree.put(key, redBlackTree.get(key) + 1);
                } else {
                    redBlackTree.put(key, 1);
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
        redBlackTree.put(max, 0);
        startTime = System.nanoTime();
        for (String word : redBlackTree.keys())
            if (redBlackTree.get(word) > redBlackTree.get(max))
                max = word;
        totalTime = System.nanoTime() - startTime;
        time = (double) totalTime / 1E9;
        System.out.println("Word with max count: " + max);
        System.out.printf("Execution time (s) for finding max word: %.9f\n", time);

        totalTime = 0; // reset
        startTime = System.nanoTime();
        WordCount<Integer, String> maxWord = redBlackTree.maxWordCount();
        totalTime = System.nanoTime() - startTime;
        time = (double) totalTime / 1E9;
        System.out.println("Word(s) with max count:\n" + maxWord);
        System.out.printf("Execution time (s) for building value tree and getting max word: %.9f\n", time);

        totalTime = 0; // reset
        RedBlackValueTree<Integer, String> valueTree = redBlackTree.getValueTree();
        startTime = System.nanoTime();
        maxWord = valueTree.maxWord();
        totalTime = System.nanoTime() - startTime;
        time = (double) totalTime / 1E9;
        System.out.println(maxWord);
        System.out.printf("Execution time (s) for getting the max word in an already existing value tree: %.9f\n",
                time);

    }

}
