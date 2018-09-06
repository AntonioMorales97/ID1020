package lab1;

import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class GeneralizedQueueArray<Item> implements Iterable<Item> {
    private Item[] elements = (Item[]) new Object[1];
    private int size;

    public GeneralizedQueueArray() {
        size = 0;
    }
    
    private void resize(int newSize) {
        Item[] temp = (Item[])new Object[newSize];
        for (int i = 0; i < size; i++)
            temp[i] = elements[i];
        elements = temp;
    }
    
    public void insert(Item item) {
        if(size == elements.length)
            resize(size*2);
        elements[size++] = item;
    }
    
    public Item delete(int k) {
        if(k > size || k == 0)
            throw new IndexOutOfBoundsException();
        Item item = elements[size-k]; //recently added is element with index 1
        elements[size-k] = null;
        for (int i = size-k; i < size-1; i++)
            elements[i] = elements[i+1];
        size--;
        
        if(size > 0 && size == elements.length / 4)
            resize(elements.length/2);
        
        return item;
    }

    public boolean isEmpty() {
        return size == 0;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("[");
        if(isEmpty())
            return s.append("]").toString();
        int i = 0;
        while(i < size - 1)
            s.append("["+elements[i++]+"],");
        s.append("["+elements[i]+"]]");
        return s.toString();
    }


    @Override
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }
    
    private class QueueIterator implements Iterator<Item>{
        int index = 0;
        
        @Override
        public boolean hasNext() {   
            return index < size;
        }

        @Override
        public Item next() {
            return elements[index++];    
        }
        
    }

    public static void main(String[] args) {
        GeneralizedQueueArray<Character> queue = new GeneralizedQueueArray<Character>();
        char ch;
        StdOut.println("Enter some characters to queue:");
        while((ch = (char) StdIn.readChar()) != '\n' && ch != '\r')
            queue.insert(ch);
        
        StdOut.println("The generalized queue: " + queue);
        StdOut.println("Enter which element to remove (most recently added has index 1): ");
        int index = StdIn.readInt();
        queue.delete(index);
        StdOut.println("The queue is now: " + queue);
        StdOut.println("Removing the last element (if empty expect IndexOutOf...Exception)...");
        queue.delete(1);
        StdOut.println("The queue is now: " + queue);
        StdOut.println("Iterating with foreach:");
        for (char c : queue)
            StdOut.print(c);
        StdOut.println();      
    }

}
