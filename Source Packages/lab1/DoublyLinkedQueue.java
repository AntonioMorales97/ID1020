package lab1;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class DoublyLinkedQueue<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    public DoublyLinkedQueue() {
        this.size = 0;
    }

    private class Node {
        Item item;
        Node next;
        Node prev;

        public Node(Item item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int getSize() {
        return this.size;
    }

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
        if(this.isEmpty())
            return s.append("]").toString();
        Node node = first;
        while(node.next != null) {
            s.append("["+node.item+"],");
            node = node.next;
        }
        s.append("["+node.item+"]]");
        return s.toString();
    }

    @Override
    public ListIterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements ListIterator<Item> {
        private Node current = first; // FIFO
        private Node currentLast = last; //LIFO
        
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
        
        @Override
        public boolean hasPrevious() {
            return currentLast != null;
        }
        
        @Override
        public Item previous() {
            Item item = currentLast.item;
            currentLast = currentLast.prev;
            return item;
        }

        @Override
        public void add(Item e) {
        }
        
        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void remove() {
        }

        @Override
        public void set(Item e) {
        }
    }

    /**
     * A test client
     * 
     * @param args Input from terminal
     */
    public static void main(String[] args) {
        DoublyLinkedQueue<Character> queue = new DoublyLinkedQueue<Character>();
        char ch;
        StdOut.print("Enter some characters:\n");
        while ((ch = (char) StdIn.readChar()) != '\n' && ch != '\r')
            queue.enqueue(ch);

        StdOut.println("Linked queue: " + queue);
        
        ListIterator<Character> iterator = queue.iterator();
        StdOut.println("Iterating backwards in queue...:");
        while(iterator.hasPrevious())
            StdOut.print(iterator.previous());
        StdOut.println();
        
        StdOut.println("Now using java's foreach (starting from beginning (FIFO)):");
        for (char c : queue)
            StdOut.print(c);
        StdOut.println();
        
        StdOut.println("Now dequeuing one element from the queue (FIFO):");
        StdOut.println("Element dequeued: "+queue.dequeue());
        StdOut.println("Linked queue: " + queue);
        
        StdOut.println("Now dequeuing the queue using foreach (FIFO):");
        for(char c : queue)
            StdOut.print(queue.dequeue());
        StdOut.println();
        
        StdOut.println("Linked queue: "+queue);
    }

}
