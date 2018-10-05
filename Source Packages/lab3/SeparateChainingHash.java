package lab3;

import edu.princeton.cs.algs4.SequentialSearchST;
import edu.princeton.cs.algs4.StdIn;

import lab1.GeneralizedQueueArray;

/**
 * A system table implemented as a separate chaining hash table. Only the
 * necessary methods for this task are included in this class. The main method
 * includes a frequency counter of keys and time measurements for comparisons to
 * other algorithms. For the chained linked list {@link SequentialSearchST} is
 * used from the edu.princeton.cs.algs4 library.
 * 
 * @author Antonio
 *
 * @param <Key> For this task the keys are words from stdin.
 * @param <Value> For this task the values are number of occurrences of the key
 *        from stdin.
 */

public class SeparateChainingHash<Key, Value> {
    private static final int INIT_CAPACITY = 4;
    private int pairs;
    private int hashSize;
    private SequentialSearchST<Key, Value>[] array;

    /**
     * Initializes the hash table.
     */
    public SeparateChainingHash() {
        this(INIT_CAPACITY);
    }

    /**
     * Creates the hash table with the given size.
     * 
     * @param size the size of the table.
     */
    @SuppressWarnings("unchecked")
    public SeparateChainingHash(int size) {
        this.hashSize = size;
        array = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[size];
        for (int i = 0; i < size; i++)
            array[i] = new SequentialSearchST<Key, Value>();
    }

    private void resize(int chains) {
        SeparateChainingHash<Key, Value> temp = new SeparateChainingHash<Key, Value>(chains);
        for (int i = 0; i < hashSize; i++) {
            for (Key key : array[i].keys()) {
                temp.put(key, array[i].get(key));
            }
        }
        this.hashSize = temp.hashSize;
        this.pairs = temp.pairs;
        this.array = temp.array;
    }

    private int hash(Key key) {
        return ((key.hashCode() & 0x7fffffff) % hashSize);
    }

    /**
     * @return the number of key-values pairs in the hash table.
     */
    public int size() {
        return pairs;
    }

    /**
     * @return <code>true</code> if the size is 0, <code>false</code> otherwise.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Checks if the hash table contains the given key.
     * 
     * @param key the key to be searched for.
     * @return <code>true</code> if the hash table contains the key,
     *         <code>false</code> otherwise.
     * @throws IllegalArgumentException is the key is null.
     */
    public boolean contains(Key key) {
        if (key == null)
            throw new IllegalArgumentException("Key is null.");

        return get(key) != null;
    }

    /**
     * Checks for the given key in the linked list in the hash position of the hash
     * table and returns its value.
     * 
     * @param key the key to be searched for.
     * @return the key's value. Returns <code>null</code> if not found.
     * @throws IllegalArgumentException if the key is <code>null</code>.
     */
    public Value get(Key key) {
        if (key == null)
            throw new IllegalArgumentException("Key is null.");

        int pos = hash(key);
        return array[pos].get(key);
    }

    /**
     * Puts the given key and its value in the hash table if it is not already in
     * it. Otherwise the value of the key will be updated with the new given value.
     * If the number of key-value pairs is more than or equal to 10 * the table
     * size, the hash table will be resized to the double.
     * 
     * @param key   the key to be put/updated.
     * @param value the value of the key to be put/updated.
     * @throws IllegalArgumentException if the key is <code>null</code>.
     */
    public void put(Key key, Value value) {
        if (key == null)
            throw new IllegalArgumentException("Key is null.");

        if (pairs >= hashSize * 10)
            resize(2 * hashSize);

        int pos = hash(key);
        if (!array[pos].contains(key))
            pairs++;
        array[pos].put(key, value);
    }

    /**
     * Creates an iterable queue and inserts all of the keys in the hash table into
     * the queue.
     * 
     * @return the created queue.
     */
    public Iterable<Key> keys() {
        GeneralizedQueueArray<Key> queue = new GeneralizedQueueArray<Key>();
        for (int i = 0; i < hashSize; i++)
            for (Key key : array[i].keys())
                queue.insert(key);
        return queue;
    }

    /**
     * A text client with a frequency counter to count the words from stdin and
     * measure the time it takes for operations. Uses the
     * <code>SeparateChainingHash</code> to count and store. The data printed out can be
     * used to compare with other system tables.
     * 
     * @param args Not used here.
     */
    public static void main(String[] args) {
        int distinct = 0;
        int words = 0;

        SeparateChainingHash<String, Integer> hashST = new SeparateChainingHash<String, Integer>();
        String key;

        long totalTime = 0;
        long startTime;

        while (!StdIn.isEmpty()) {
            key = StdIn.readString().toLowerCase();
            if (key.length() < 2)
                continue;

            words++;
            startTime = System.nanoTime();
            if (hashST.contains(key)) {
                hashST.put(key, hashST.get(key) + 1);
            } else {
                hashST.put(key, 1);
                distinct++;
            }
            totalTime += System.nanoTime() - startTime;
        }

        double time = (double) totalTime / 1E9;
        System.out.printf("Execution time (s) for counting words: %.9f\n", time);

        String max = "";
        hashST.put(max, 0);
        totalTime = 0; // reset
        startTime = System.nanoTime();
        for (String word : hashST.keys()) {
            if (hashST.get(word) > hashST.get(max))
                max = word;
        }
        totalTime = System.nanoTime() - startTime;
        time = (double) totalTime / 1E9;
        System.out.printf("Time to find max word: %.9f\n", time);

        System.out.println(max + " " + hashST.get(max));
        System.out.println("distinct = " + distinct);
        System.out.println("words    = " + words);
    }

}
