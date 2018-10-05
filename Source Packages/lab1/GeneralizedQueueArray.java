package lab1;

import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * This program implements a generalized iterable queue, giving the user the
 * possibility to insert to the queue (FIFO, then inserts at the end) and delete
 * an item at the kth position of the queue.
 * 
 * @author Antonio
 *
 * @param <Item> generic type.
 */
public class GeneralizedQueueArray<Item> implements Iterable<Item> {
    private Item[] items = (Item[]) new Object[1];
    private int size;

    /**
     * Constructor initializes the queue with a size of zero. The queue is an array
     * queue.
     */
    public GeneralizedQueueArray() {
        size = 0;
    }

    private void resize(int newSize) {
        Item[] newArray = (Item[]) new Object[newSize];
        for (int i = 0; i < size; i++)
            newArray[i] = items[i];
        items = newArray;
    }

    /**
     * Checks if the queue is full and if so resizes it to the double size and
     * inserts the item to the end of the queue (not the end of the list!)
     * 
     * @param item Item to be inserted to the queue.
     */
    public void insert(Item item) {
        if (size == items.length)
            resize(size * 2);
        items[size++] = item;
    }

    /**
     * The recently added item has index 1. So it saves the item at index (size - k)
     * and then deletes that position by setting it to null to avoid loitering. Then
     * moves all items of the right of the deleted position to left and decrements
     * the size. At the end it checks if the length of the queue array is
     * unnecessary big and halves it if possible.
     * 
     * @param k the position of the item to be deleted, where the recently added has
     *          index 1.
     * @throws IndexOutOfBoundsException if the index position is out of the queue
     *                                   array.
     * @return the item in the deleted position.
     */
    public Item delete(int k) {
        if (k > size || k == 0)
            throw new IndexOutOfBoundsException();
        Item item = items[size - k];
        items[size - k] = null;
        for (int i = size - k; i < size - 1; i++)
            items[i] = items[i + 1];
        size--;

        if (size > 0 && size == items.length / 4)
            resize(items.length / 2);

        return item;
    }

    /**
     * Access to item in the given position in the array.
     * 
     * @param pos position of the item to get.
     * @throws IndexOutOfBoundsException if the index position is out the queue
     *                                   array.
     * @return The item in the position
     */
    public Item get(int pos) {
        if (pos > size)
            throw new IndexOutOfBoundsException();
        return items[pos];
    }

    /**
     * Checks if the queue is empty.
     * 
     * @return true if the size is zero, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[");
        if (isEmpty())
            return s.append("]").toString();
        int i = 0;
        while (i < size - 1)
            s.append("[" + items[i++] + "],");
        s.append("[" + items[i] + "]]");
        return s.toString();
    }

    /**
     * @return the iterator of the queue.
     */
    @Override
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        int index = 0;

        /**
         * Checks if the current index is at the end of the queue array.
         * 
         * @return true if the current index is less than the size of the queue array,
         *         false otherwise.
         */
        @Override
        public boolean hasNext() {
            return index < size;
        }

        /**
         * Return the item at the current index position and iterates the the next
         * position of the queue array. Starting from the beginning of the queue.
         * 
         * @return the item at the current index position.
         */
        @Override
        public Item next() {
            return items[index++];
        }

    }

    /**
     * A test client that reads some characters from the user and inserts each one
     * to the queue array (FIFO, so to the end of the queue). Then the user can
     * enter which item to remove by entering the index position of the item where
     * the last recently added has index 1. Then the last item is removed
     * (hardcoded) and finally it iterates through the queue using java's foreach.
     * 
     * @param args Input from command line, not used here.
     */
    public static void main(String[] args) {
        GeneralizedQueueArray<Character> queue = new GeneralizedQueueArray<Character>();
        char ch;
        StdOut.println("Enter some characters to queue:");
        while ((ch = (char) StdIn.readChar()) != '\n' && ch != '\r')
            queue.insert(ch);

        StdOut.println("The generalized queue: " + queue);
        StdOut.println("Enter which element to remove (most recently added has index 1): ");
        int index = StdIn.readInt();
        StdOut.println("Removed: " + queue.delete(index));
        StdOut.println("The queue is now: " + queue);
        StdOut.println("Removing the last element (if empty expect IndexOutOf...Exception)...");
        StdOut.println("Removed: " + queue.delete(1));
        StdOut.println("The queue is now: " + queue);
        StdOut.println("Iterating with foreach:");
        for (char c : queue)
            StdOut.print(c);
        StdOut.println();
    }

}
