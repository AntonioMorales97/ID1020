package lab4;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Just a Bag data structure that is used for this lab.
 * 
 * @author Antonio
 *
 * @param <Item> The data type this Bag will hold.
 */
public class Bag<Item> implements Iterable<Item> {
    private Node<Item> first;
    private int size;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    /**
     * Initialize a Bag
     */
    public Bag() {
        first = null;
        size = 0;
    }

    /**
     * Iterates through the bag to check if it contains the given item.
     * 
     * @return <code>true</code> if the bag contains the given item;
     *         <code>false</code> otherwise.
     */
    public boolean contains(Item item) {
        for (Item i : this)
            if (item.equals(i))
                return true;
        return false;
    }

    /**
     * Checks if the bag is empty.
     *
     * @return <code>true</code> if it is empty, <code>false</code> otherwise.
     */
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns the size of the bag.
     *
     * @return the number of items in the bag i.e the size of the bag.
     */
    public int size() {
        return size;
    }

    /**
     * Adds the given item to the bag and increments the size.
     *
     * @param item the item to be added to the bag.
     */
    public void add(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        size++;
    }

    /**
     * Returns an iterator that iterates over the items in this bag.
     *
     * @return an iterator of this bag.
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
     * Just a simple test client.
     *
     * @param args Not used here.
     */
    public static void main(String[] args) {
        Bag<String> bag = new Bag<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            bag.add(item);
        }

        StdOut.println("Size of bag is: " + bag.size());
        for (String s : bag) {
            StdOut.println(s);
        }
    }

}
