package lab4;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Just a simple stack that is used for this lab.
 * 
 * @author Antonio
 *
 * @param <Item> The data type this stack will hold.
 */
public class Stack<Item> implements Iterable<Item> {
    private Node<Item> first;
    private int size;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    /**
     * Initializes an empty stack.
     */
    public Stack() {
        first = null;
        size = 0;
    }

    /**
     * Checks if the stack is empty.
     *
     * @return <code>true</code> if this stack is empty, <code>false</code>
     *         otherwise.
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the size of the stack.
     *
     * @return the number of items in the stack.
     */
    public int size() {
        return size;
    }

    /**
     * Adds the item to this stack and increments the size number.
     *
     * @param item the item to add
     */
    public void push(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        size++;
    }

    /**
     * Removes and returns the item most recently added to this stack and decrements
     * the size number.
     *
     * @return the item most recently added.
     * @throws NoSuchElementException if the stack is empty and there is nothing to
     *                                return.
     */
    public Item pop() {
        if (isEmpty())
            throw new NoSuchElementException("Stack underflow");
        Item item = first.item;
        first = first.next;
        size--;
        return item;
    }

    /**
     * Returns a <code>String</code> representation of this stack.
     *
     * @return a sequence of items in this stack in LIFO order.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item);
            s.append(' ');
        }
        return s.toString();
    }

    /**
     * Returns the iterator of this stack that iterates through the items in LIFO
     * order.
     *
     * @return an iterator.
     */
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(first);
    }

    @SuppressWarnings("hiding")
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    /**
     * Just a test client.
     *
     * @param args Not used here.
     */
    public static void main(String[] args) {
        Stack<String> stack = new Stack<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-"))
                stack.push(item);
            else if (!stack.isEmpty())
                StdOut.print(stack.pop() + " ");
        }
        StdOut.println("(" + stack.size() + " left on stack)");
    }
}
