package lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import lab1.GeneralizedQueueArray;

/**
 * This is a binary search tree of the values for the {@link BinarySearchTree}.
 * Operations are very similar; the biggest difference is that the values are
 * stored in a <code>List</code>, since multiple values can be associated to the
 * same key. For example if the keys are words and values are the count in the
 * {@link BinarySearchTree}, several words may occur the same amount of times.
 * For simplicity some methods are left uncommented; please see
 * {@link BinarySearchTree}.
 * 
 * @author Antonio
 *
 */
public class BinarySearchValueTree<Key extends Comparable<Key>, Value> {
    private Node root;

    private class Node {
        private Key key;
        private List<Value> values;
        private Node left;
        private Node right;
        private int size;

        public Node(Key key, Value value, int size) {
            this.key = key;
            this.values = new ArrayList<>();
            this.values.add(value);
            this.size = size;
        }
    }

    public BinarySearchValueTree() {
    }

    public boolean isEmpty() {
        return size(root) == 0;
    }

    private int size(Node node) {
        if (node == null)
            return 0;
        return node.size;
    }

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
            node.values.add(value);

        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    public List<Value> get(Key key) {
        if (key == null)
            throw new IllegalArgumentException("Key is null.");
        return get(root, key);
    }

    private List<Value> get(Node node, Key key) {
        if (node == null)
            return null;
        int compareResult = key.compareTo(node.key);
        if (compareResult < 0)
            return get(node.left, key);
        else if (compareResult > 0)
            return get(node.right, key);
        else
            return node.values;
    }

    public WordCount<Key, Value> maxWord() {
        if (isEmpty())
            throw new NoSuchElementException("The BST is empty.");
        return maxWord(root);
    }

    private WordCount<Key, Value> maxWord(Node node) {
        if (node.right == null)
            return new WordCount<Key, Value>(node.key, node.values);
        return maxWord(node.right);
    }

    public Key max() {
        if (isEmpty())
            throw new NoSuchElementException("The BST is empty.");

        Key maxKey = max(root);
        return maxKey;
    }

    private Key max(Node node) {
        if (node.right == null)
            return node.key;
        return max(node.right);
    }

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
     * Orders all of nodes in this tree in a {@link GeneralizedQueueArray} of
     * <code>String</code>s and in ascending order.
     * 
     * @return the ordered <code>List</code> of <code>String</code>s.
     */
    public GeneralizedQueueArray<String> ascendingQueue() {
        if (isEmpty())
            throw new NoSuchElementException("The BST is empty.");

        GeneralizedQueueArray<String> queue = new GeneralizedQueueArray<String>();
        ascendingQueue(queue, root, min(), max());
        return queue;
    }

    private void ascendingQueue(GeneralizedQueueArray<String> queue, Node node, Key low, Key high) {
        if (node == null)
            return;
        int compareLow = low.compareTo(node.key);
        int compareHigh = high.compareTo(node.key);
        if (compareHigh > 0)
            ascendingQueue(queue, node.right, low, high);
        if (compareLow <= 0 && compareHigh >= 0) {
            int countKey = (int) node.key;
            for (Value v : node.values)
                queue.insert("Word: \"" + v + "\", Count: " + countKey);
        }
        if (compareLow < 0)
            ascendingQueue(queue, node.left, low, high);
    }

    public static void main(String[] args) {
        BinarySearchValueTree<Integer, String> tree = new BinarySearchValueTree<Integer, String>();
        tree.put(10, "Hi");
        tree.put(30, "Hello");
        tree.put(40, "Sausage");
        tree.put(45, "Bread");
        System.out.println("Word(s) with highest frequency:\n" + tree.maxWord().toString());
        GeneralizedQueueArray<String> list = tree.ascendingQueue();
        System.out.println(list);
    }
}
