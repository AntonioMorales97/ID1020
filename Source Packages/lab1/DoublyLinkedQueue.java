package lab1;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * This program implements a generic iterable FIFO-doubly linked-queue with a
 * test client included. For it to be iterable the nodes have reference to the
 * next node and also to the previous node. The iterator implements ListIterator
 * where it can iterate in both directions. Since it is a FIFO queue the enqueue
 * and dequeue methods are implemented as such.
 * 
 * @author Antonio
 *
 * @param <Item>
 *            generic type.
 */
public class DoublyLinkedQueue<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    /**
     * Constructor that initializes the queue with a size of zero.
     */
    public DoublyLinkedQueue() {
        this.size = 0;
    }

    private class Node {
        Item item;
        Node next;
        Node prev;

        /**
         * The constructor for the nodes.
         * 
         * @param item
         *            The generic type which the node will hold.
         * @param next
         *            A reference to the next node, which in a queue is a reference to
         *            the node "behind".
         * @param prev
         *            A reference to the previous node, the node in front if this node.
         */
        public Node(Item item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    /**
     * Checks if the queue is empty.
     * 
     * @return True if the queue is empty, False otherwise.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Checks the size of the queue.
     * 
     * @return the size of the queue.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Enqueues the given type item to the last position of the queue and increments
     * the size, because that is the way a queue works. Also updates the reference
     * to the next node of the old last node to the new last node.
     * 
     * @param item
     *            The type item to enqueue.
     */
    public void enqueue(Item item) {
        Node oldLast = last;
        last = new Node(item, null, oldLast);
        if (isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        size++;
    }

    /**
     * Dequeues the first item in the queue and updates the first node of the queue
     * with the next. If the queue is not empty it dereferences the previous node of
     * the new first node to avoid loitering.
     * 
     * @throws NoSuchElementException
     *             is trying to dequeue and empty queue.
     * @return the dequeued item.
     */
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        if (first != null)
            first.prev = null;
        size--;
        return item;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[");
        if (this.isEmpty())
            return s.append("]").toString();
        Node node = first;
        while (node.next != null) {
            s.append("[" + node.item + "],");
            node = node.next;
        }
        s.append("[" + node.item + "]]");
        return s.toString();
    }

    /**
     * @return the queue iterator.
     */
    @Override
    public ListIterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements ListIterator<Item> {
        private Node current = first; // for iterating starting from the beginning
        private Node currentLast = last; // for iterating starting from the end

        /**
         * Checks if the current node is null or not (starting with the first node).
         * 
         * @return True if it is not null, false if it is null.
         */
        @Override
        public boolean hasNext() {
            return current != null;
        }

        /**
         * Returns the item of the current node and iterates to the next node (moving to
         * the end of the queue).
         * 
         * @return the item of the current node.
         */
        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }

        /**
         * Checks if the current node is null or not (starting with the last node).
         * 
         * @return True if it is not null, false if it is null.
         */
        @Override
        public boolean hasPrevious() {
            return currentLast != null;
        }

        /**
         * Returns the item of the current node and iterates to the previous node
         * (moving to the front of the queue).
         * 
         * @return the item of the current node.
         */
        @Override
        public Item previous() {
            Item item = currentLast.item;
            currentLast = currentLast.prev;
            return item;
        }

        @Override
        public void add(Item e) {
            StdOut.println("Not implemented for this task...");
        }

        @Override
        public int nextIndex() {
            StdOut.println("Not implemented for this task...");
            return 0;
        }

        @Override
        public int previousIndex() {
            StdOut.println("Not implemented for this task...");
            return 0;
        }

        @Override
        public void remove() {
            StdOut.println("Not implemented for this task...");
        }

        @Override
        public void set(Item e) {
            StdOut.println("Not implemented for this task...");
        }
    }

    /**
     * A test client where it reads some characters from the user and enqueues them.
     * Then it iterates reversed in queue using the iterator of the queue. Then
     * iterating through the queue using java's foreach. Then is dequeues one
     * olement from the queue (FIFO) and ends with dequeing the rest of the queue.
     * Remember it will throw exception if no characters are given in from the user
     * because the queue will then be empty.
     * 
     * @param args
     *            Input from terminal, not used.
     */
    public static void main(String[] args) {
        DoublyLinkedQueue<Character> queue = new DoublyLinkedQueue<Character>();
        char ch;
        StdOut.print("Enter some characters:\n");
        while ((ch = (char) StdIn.readChar()) != '\n' && ch != '\r')
            queue.enqueue(ch);

        StdOut.println("Linked queue: " + queue);

        ListIterator<Character> iterator = queue.iterator();
        StdOut.println("Iterating reversed in queue (to the front)...:");
        while (iterator.hasPrevious())
            StdOut.print(iterator.previous());
        StdOut.println();

        StdOut.println("Now using java's foreach (starting from beginning (FIFO)):");
        for (char c : queue)
            StdOut.print(c);
        StdOut.println();

        StdOut.println("Now dequeuing one element from the queue (FIFO):");
        StdOut.println("Element dequeued: " + queue.dequeue());
        StdOut.println("Linked queue: " + queue);

        StdOut.println("Now dequeuing the queue using foreach (FIFO):");
        for (char c : queue)
            StdOut.print(queue.dequeue());
        StdOut.println();

        StdOut.println("Linked queue: " + queue);
    }

}
