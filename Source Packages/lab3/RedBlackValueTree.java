package lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This is a red black tree of the values for the {@link RedBlackTree}.
 * Operations are very similar; the biggest difference is that the values are
 * stored in a <code>List</code>, since multiple values can be associated to the
 * same key. For example if the keys are words and values are the count in the
 * {@link RedBlackTree}, several words may occur the same amount of times.
 * For simplicity some methods are left uncommented; please see
 * {@link RedBlackTree}.
 * 
 * @author Antonio
 */
public class RedBlackValueTree<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    private class Node {
        private Key key;
        private List<Value> values;
        private Node left;
        private Node right;
        private boolean color;
        private int size;

        public Node(Key key, Value value, boolean color, int size) {
            this.key = key;
            this.values = new ArrayList<>();
            this.values.add(value);
            this.color = color;
            this.size = size;
        }
    }

    public RedBlackValueTree() {
    }

    private boolean isRed(Node node) {
        if (node == null)
            return false;
        return node.color == RED;
    }

    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null)
            return 0;
        return node.size;
    }

    public boolean isEmpty() {
        return root == null;
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

    public boolean contains(Key key) {
        return get(key) != null;
    }

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
            node.values.add(value);

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

    public WordCount<Key, Value> maxWord() {
        if (isEmpty())
            throw new NoSuchElementException("Tree is empty.");
        return maxWord(root);
    }

    private WordCount<Key, Value> maxWord(Node node) {
        if (node.right == null)
            return new WordCount<Key, Value>(node.key, node.values);
        return maxWord(node.right);
    }

    public static void main(String[] args) {
        RedBlackValueTree<Integer, String> valueTree = new RedBlackValueTree<Integer, String>();
        valueTree.put(10, "Hi");
        valueTree.put(10, "Hello");
        valueTree.put(100, "Queso");
        valueTree.put(0, "Salchicha");
        valueTree.put(100, "One");
        System.out.println(valueTree.maxWord());
    }

}
