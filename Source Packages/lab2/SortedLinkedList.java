package lab2;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A Queue implemented as linked list which only enqueues integers (for this
 * task) and in an ascending order.
 * 
 * @author Antonio
 *
 */
public class SortedLinkedList implements Iterable<Integer> {
    private Node first;
    private Node last;
    private int size;

    private class Node implements Comparable<Node> {
        int number;
        Node next;

        /**
         * Node constructor which initializes the created node.
         * 
         * @param number The number which the node will hold.
         */
        public Node(int number) {
            this.number = number;
            this.next = null;
        }

        /**
         * Compares the number of this node and an other node.
         * 
         * @param other The other node to compare with.
         */
        @Override
        public int compareTo(Node other) {
            if (this.number > other.number)
                return 1;
            if (this.number < other.number)
                return -1;
            return 0;
        }
    }

    /**
     * Constructor of the Queue. Initializes with size 0.
     */
    public SortedLinkedList() {
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[");
        if (this.isEmpty())
            return s.append("]").toString();
        Node node = first;
        while (node.next != null) {
            s.append("[" + node.number + "],");
            node = node.next;
        }
        s.append("[" + node.number + "]]");
        return s.toString();
    }

    /**
     * Checks if the queue is empty.
     * 
     * @return true if empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks the size.
     * 
     * @return the size of the queue.
     */
    public int getSize() {
        return size;
    }

    /**
     * Enqueues the given number to the queue in ascending order. Prints out the
     * queue.
     * 
     * @param number The number to be enqueued.
     */
    public void enqueue(int number) {
        Node newNode = new Node(number);
        if (isEmpty()) {
            first = newNode;
            last = newNode;
            size++;
            show();
            return;
        } else if (newNode.compareTo(first) < 0) {
            newNode.next = first;
            first = newNode;
            size++;
            show();
            return;
        } else if(newNode.compareTo(last) > 0) {
            Node oldLast = last;
            oldLast.next = newNode;
            last = newNode;
            size++;
            show();
            return;
        }
        Node current = first.next;
        Node currentPrev = first;
        while (current != last && newNode.compareTo(current) > 0) {
            currentPrev = current;
            current = current.next;
        }

        currentPrev.next = newNode;
        newNode.next = current;
        if (current == null)
            last = newNode;
        size++;
        show();
    }

    /**
     * Dequeues the first element in the queue (FIFO).
     * 
     * @return the number of the node in the first place.
     */
    public int dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        int number = first.number;
        first = first.next;
        if(first == null)
            last = null;
        size--;
        show();
        return number;
    }

    private void show() {
        System.out.println(this.toString());
    }

    /**
     * @return the a queue iterator.
     */
    @Override
    public Iterator<Integer> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Integer> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Integer next() {
            int number = current.number;
            current = current.next;
            return number;
        }

    }

    /**
     * A test client that creates a queue and enqueues integers from the user to the
     * queue. Dequeues the first integers in the queue and prints out the queue in the end.
     * 
     * @param args Not used here.
     */
    public static void main(String[] args) {
        SortedLinkedList queue = new SortedLinkedList();
        Scanner scan = new Scanner(System.in);
        int number;
        System.out.println("Enter the size of the queue: ");
        int size = scan.nextInt();
        if(size <= 0) {
            System.out.println("Please enter a size!");
            scan.close();
            return;
        }
        
        System.out.println("Enter the numbers to enqueue: ");
        for(int i = 0; i < size; i++) {
            number = scan.nextInt();
            System.out.println("Queue is now: ");
            queue.enqueue(number);
        }
        scan.close();
        System.out.println("Dequeuing the first integer...");
        queue.dequeue();
        System.out.println("Printing out the queue using foreach...");
        for (int i : queue)
            System.out.print("[" + i + "]->");
        System.out.println();
    }

}
