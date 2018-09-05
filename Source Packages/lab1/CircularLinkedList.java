package lab1;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class CircularLinkedList<Item> implements Iterable<Item> {
    public Node first;
    public Node last;
    private int size;

    public void main(String[] args) {
        CircularLinkedList<Character> list = new CircularLinkedList<Character>();
        char ch;
        StdOut.print("Enter some characters:\n");
        while((ch = (char) StdIn.readChar()) != '\n' && ch != '\r') {
            list.addToBack(ch);
        }
        
        for (char c : list)
            System.out.print(c);
        /*
        System.out.println(list);
        System.out.println("First: " + list.first.item + " Last: " + list.last.item);
        System.out.println(list.removeFront());
        //System.out.println("First: " + list.first.item + " Last: " + list.last.item);
        
        System.out.println(list);
        */
    }

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

}
