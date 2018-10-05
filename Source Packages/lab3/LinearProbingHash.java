package lab3;

import edu.princeton.cs.algs4.StdIn;
import lab1.GeneralizedQueueArray;

/**
 * A system table implemented as a linear probing hash table. Only the necessary
 * methods for this task are included in this class. The main method includes a
 * frequency counter of keys and time measurements for comparisons to other
 * algorithms.
 * 
 * @author Antonio
 *
 * @param <Key> For this task the keys are words from stdin.
 * @param <Value> For this task the values are number of occurrences of the key
 *        from stdin.
 */
public class LinearProbingHash<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    private int pairs;
    private int hashSize;
    private Key[] keys;
    private Value[] values;

    /**
     * Initializes the hash table.
     */
    public LinearProbingHash() {
        this(INIT_CAPACITY);
    }

    /**
     * Creates the hash table with the given size.
     * 
     * @param size the size of the table.
     */
    @SuppressWarnings("unchecked")
    public LinearProbingHash(int size) {
        hashSize = size;
        pairs = 0;
        keys = (Key[]) new Object[hashSize];
        values = (Value[]) new Object[hashSize];
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

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % hashSize;
    }

    private void resize(int size) {
        LinearProbingHash<Key, Value> tmp = new LinearProbingHash<Key, Value>(size);
        for (int i = 0; i < hashSize; i++) {
            if (keys[i] != null)
                tmp.put(keys[i], values[i]);
        }
        keys = tmp.keys;
        values = tmp.values;
        hashSize = tmp.hashSize;
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
     * Puts the given key and its value in the hash table if it is not already in
     * it. Otherwise the value of the key will be updated with the new given value.
     * If the number of key-value pairs is more than or equal to the half of the
     * table size, the hash table will be resized to the double.
     * 
     * @param key   the key to be put/updated.
     * @param value the value of the key to be put/updated.
     * @throws IllegalArgumentException if the key is null.
     */
    public void put(Key key, Value value) {
        if (key == null)
            throw new IllegalArgumentException("Key is null.");

        if (pairs >= hashSize / 2)
            resize(2 * hashSize);

        int pos;
        // Iterate until an empty spot is found or the key is found in the table.
        for (pos = hash(key); keys[pos] != null; pos = (pos + 1) % hashSize)
            if (keys[pos].equals(key)) {
                values[pos] = value;
                return;
            }

        keys[pos] = key;
        values[pos] = value;
        pairs++;
    }

    /**
     * Checks for the given key in the hash table and returns its value.
     * 
     * @param key the key to be searched for.
     * @return the key's value. Returns <code>null</code> if not found.
     * @throws IllegalArgumentException if the key is <code>null</code>.
     */
    public Value get(Key key) {
        if (key == null)
            throw new IllegalArgumentException("Key is null.");

        for (int i = hash(key); keys[i] != null; i = (i + 1) % hashSize)
            if (keys[i].equals(key))
                return values[i];
        return null;
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
            if (keys[i] != null)
                queue.insert(keys[i]);
        return queue;
    }

    /**
     * A text client with a frequency counter to count the words from stdin and
     * measure the time it takes for operations. Uses the
     * <code>LinearProbingHash</code> to count and store. The data printed out can be
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

        // Find the key with the highest frequency count
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
