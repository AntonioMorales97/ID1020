package lab1;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * This program implements a generic iterable circular linked list (meaning that
 * the last node has a reference to the first node) with the possibility to
 * add/remove to the front of the list and also add/remove to the end of the
 * list.
 * 
 * @author Antonio
 *
 * @param <Item>
 *            generic type
 */
public class CircularLinkedList<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    /**
     * Constructor of the list, initializing the list with the size zero.
     */
    public CircularLinkedList() {
        this.size = 0;
    }

    private class Node {
        Item item;
        Node next;

        /**
         * Constructor of the node that will hold the given item.
         * 
         * @param item
         *            Something, for example a character.
         * @param next
         *            Reference to the next node.
         */
        public Node(Item item, Node next) {
            this.item = item;
            this.next = next;
        }
    }

    /**
     * Checks if the list is empty.
     * 
     * @return true if the list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Checks the size of the list.
     * 
     * @return the size of the list.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Adds to the front of the list by creating a new node with the item and a
     * reference to the first node and also increments the size. It the node is
     * empty the first and last node will be the same, otherwise the last node in
     * the list is updated so it has a reference to the new first node.
     * 
     * @param item
     *            Item to be added to the list.
     */
    public void addToFront(Item item) {
        Node oldFirst = first;
        first = new Node(item, oldFirst);
        if (isEmpty()) {
            last = first;
            last.next = first;
        } else {
            last.next = first;
        }
        size++;
    }

    /**
     * Adds to the back of the list by creating a node with the given item and a
     * reference to the first node and also updating the reference of the old last
     * node to the new last node if the list was not empty before. Also increments
     * the size.
     * 
     * @param item
     *            Item to be added to the list.
     */
    public void addToBack(Item item) {
        Node oldLast = last;
        last = new Node(item, first);
        if (isEmpty()) {
            first = last;
            first.next = first;
        } else {
            oldLast.next = last;
        }
        size++;
    }

    /**
     * Removes from the front of the list. If the list only has one node, meaning
     * that the first and last node is the same node, it will dereference them (to
     * null). Otherwise it will update the new first node and reference of the last
     * node to the new first node. Decrements the size of the list at the end.
     * 
     * @throws NoSuchElementException
     *             if trying to remove from an empty list.
     * @return the item of the removed node.
     */
    public Item removeFront() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = first.item;
        if (first == last) {
            first = null;
            last = null;
        } else {
            first = first.next;
            last.next = first;
        }
        size--;
        return item;
    }

    /**
     * Removes from the back of the list. If the list only has one node it will call
     * the method removeFront, since it is the same. Otherwise it will remove the
     * last node by iterating through the list until the reference of the next node
     * of the next node is the first node (which will be the new last node) and
     * updates the reference of it to the first node. Decrements the size at the
     * end.
     * 
     * @return the item of the removed node.
     */
    public Item removeBack() {
        if (isEmpty())
            throw new NoSuchElementException();
        else if (size == 1)
            return removeFront();
        Item item = last.item;
        Node newLast = first;
        while (newLast.next.next != first)
            newLast = newLast.next;
        last = newLast;
        last.next = first;
        size--;
        return item;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[");
        Node currentNode = first;
        if (currentNode == null)
            return s.append("]").toString();
        while (currentNode.next != first) {
            s.append("[" + currentNode.item + "],");
            currentNode = currentNode.next;
        }
        s.append("[" + currentNode.item + "]" + "]");
        return s.toString();
    }

    /**
     * @return the iterator of the circular linked list.
     */
    @Override
    public Iterator<Item> iterator() {
        return new CircularListIterator();
    }

    private class CircularListIterator implements Iterator<Item> {
        private boolean completeCircle = false;
        private Node current = first;

        /**
         * Checks if the current node is not the same as the first node and if the
         * completeCircle flag is true or false.
         * 
         * @return true if the current node is not the same as the first and if the
         *         circle has not been iterated already. False otherwise.
         */
        @Override
        public boolean hasNext() {
            if (size == 0)
                return false;
            return current != first || !completeCircle;
        }

        /**
         * Checks if the reference of the next node of the current node is the first
         * node (meaning it is the last node and the circle has been completed), return
         * the item of the current node and iterates to the next node.
         * 
         * @return the item of the current node.
         */
        @Override
        public Item next() {
            if (current.next == first)
                completeCircle = true;
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    /**
     * A test client that takes input from the user and adds each character to the
     * back of the linked list. Then iterates using java's foreach and then adds one
     * character to the front of the list. The removes the first and last item of
     * the list and prints out the list. Finally removes the remaining items in the
     * list. Starting from the beginning.
     * 
     * @param args
     *            Input from terminal, not used here.
     */
    public static void main(String[] args) {
        CircularLinkedList<Character> list = new CircularLinkedList<Character>();
        char ch;
        StdOut.println("Enter some characters where each will be added to the back of the linked list:");
        while ((ch = (char) StdIn.readChar()) != '\n' && ch != '\r')
            list.addToBack(ch);

        StdOut.println("Circular linked list: " + list);
        StdOut.println("Iterating the list using foreach: ");
        for (char c : list)
            StdOut.print(c);
        StdOut.println();

        StdOut.println("Enter one character that will be added to the front of the list: ");
        ch = StdIn.readChar();
        if (ch == '\n' || ch == '\r') // if remaining in StdIn...
            ch = StdIn.readChar();
        list.addToFront(ch);
        StdOut.println("List is now: " + list);
        StdOut.println("Removing the first element...");
        list.removeFront();
        StdOut.println("List is now: " + list);
        StdOut.println("Removing the last element...");
        list.removeBack();
        StdOut.println("List is now: " + list);

        StdOut.println("Removing the remaining elements in the list. Starting from the beginning: ");
        for (char c : list)
            StdOut.print(list.removeFront());
        StdOut.println();
        StdOut.println("Empty list: " + list);

    }
}
