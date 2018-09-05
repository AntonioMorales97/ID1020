package lab1;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedQueue<Item> implements Iterable<Item>{
    private Node first;
    private Node last;
    private int size;
    
    public void main (String[] args) {
        int i = 0;
        DoublyLinkedQueue<Character> queue = new DoublyLinkedQueue<Character>();
        queue.enqueue('A');
        queue.enqueue('B');
        queue.enqueue('C');
        queue.enqueue('D');
        queue.enqueue('E');
        queue.enqueue('F');
        queue.enqueue('G');
        queue.enqueue('H');
        queue.enqueue('I');
        queue.enqueue('J');
        queue.enqueue('K');
        queue.enqueue('L');
        
        for (char ch : queue)
            System.out.print(queue.dequeue());
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

    @Override
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }
    
    private class QueueIterator implements Iterator<Item>{
        private Node currentFirst = first; //FIFO
        private Node currentLast = last; //LIFO

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
