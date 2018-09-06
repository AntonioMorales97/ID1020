package lab1;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class CircularLinkedList<Item> implements Iterable<Item> {
    public Node first;
    public Node last;
    private int size;

    public CircularLinkedList() {
        this.size = 0;
    }

    private class Node {
        Item item;
        Node next;

        public Node(Item item, Node next) {
            this.item = item;
            this.next = next;
        }
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int getSize() {
        return this.size;
    }

    public void addToFront(Item item) {
        Node oldFirst = first;
        first = new Node(item, oldFirst);
        if(isEmpty()) {
            last = first;
            last.next = first;
        } else {
            last.next = first;
        }
        size++;
    }
    
    public void addToBack(Item item) {
        Node oldLast = last;
        last = new Node(item, first);
        if(isEmpty()) {
            first = last;
            first.next = first;
        } else {
            oldLast.next = last;
        }
        size++;
    }
    
    public Item removeFront() {
        if(isEmpty())
            throw new NoSuchElementException();
        Item item = first.item;
        if(first == last) {
            first = null;
            last = null;
        } else {
            first = first.next;
            last.next = first;
        }
        size--;
        return item;     
    }
    
    public Item removeBack() {
        if(isEmpty())
            throw new NoSuchElementException();
        else if (size == 1)
            return removeFront();
        Item item = last.item;
        Node newLast = first;
        while(newLast.next.next != first)
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
        while(currentNode.next != first) {
            s.append("["+currentNode.item+"],");
            currentNode = currentNode.next;
        }
        s.append("["+currentNode.item+"]" +"]");
        return s.toString();
    }

    @Override
    public Iterator<Item> iterator() {
        return new CircularListIterator();
    }

    private class CircularListIterator implements Iterator<Item> {
        private boolean completeCircle = false;
        private Node current = first;

        @Override
        public boolean hasNext() {  
            if(size == 0)
                return false;
            return current != first || !completeCircle;
        }

        @Override
        public Item next() {
            if(current.next == first)
                completeCircle = true;
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        CircularLinkedList<Character> list = new CircularLinkedList<Character>();
        char ch;
        StdOut.println("Enter some characters where each will be added to the back of the linked list:");
        while((ch = (char) StdIn.readChar()) != '\n' && ch != '\r')
            list.addToBack(ch);
        
        StdOut.println("Circular linked list: " + list);
        StdOut.println("Iterating the list using foreach: ");
        for (char c : list)
            StdOut.print(c);
        StdOut.println();
        
        StdOut.println("Adding 'Z' to the front of the list...");
        list.addToFront('Z');
        StdOut.println("Circular linked list: " + list);
        
        StdOut.println("Removing last element in the circular linked list...");
        list.removeBack();
        StdOut.println("Circular linked list: " + list);
        StdOut.println("Removing the first element in the circular linked list...");
        list.removeFront();
        StdOut.println("Circular linked list: " + list);
        
        StdOut.println("Removing the remaining elements in the list. Starting from the beginning: ");
        for (char c : list)
            StdOut.print(list.removeFront());
        StdOut.println();
        StdOut.println("Circular linked list: " + list);
    }
}
