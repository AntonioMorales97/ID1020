package lab1;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class DoublyLinkedQueue<Item> implements Iterable<Item>{
    private Node first;
    private Node last;
    private int size;
    
    public void main (String[] args) {
        DoublyLinkedQueue<Character> queue = new DoublyLinkedQueue<Character>();
        char ch;
        StdOut.print("Enter some characters:\n");
        while((ch = (char) StdIn.readChar()) != '\n' && ch != '\r')
            queue.enqueue(ch);

        queue.iterateFromBehind();
        StdOut.print("Now iterating with iterator (FIFO) with dequeue...:\n");
        for (char c : queue)
            StdOut.print(queue.dequeue());
        StdOut.print('\n');
    }
    
    public DoublyLinkedQueue() {
        this.size = 0;
    }
    
    private class Node{
        Item item;
        Node next;
        Node prev;
        
        public Node (Item item, Node next, Node prev) {
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
        if(isEmpty()) {
            first = last;
        } else {
            oldLast.next = last;
        }
        size++;
    }
    
    public Item dequeue() {
        if(isEmpty())
            throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        if(first != null)
            first.prev = null;
        size--;
        return item;  
    }

    public void iterateFromBehind() {
        StdOut.print("Iterating from behind without removing anything...:\n");
        Node currentLastNode = last;
        while(currentLastNode != null) {
            StdOut.print(currentLastNode.item);
            currentLastNode = currentLastNode.prev;
        }
        StdOut.print('\n');    
    }
    
    @Override
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }
    
    private class QueueIterator implements Iterator<Item>{
        private Node currentFirst = first; //FIFO

        @Override
        public boolean hasNext() {
            return currentFirst != null;
        }

        @Override
        public Item next() {
            Item item = currentFirst.item;
            currentFirst = first.next;
            return item;
        }  
    }

}
