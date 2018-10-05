package lab3;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import lab1.GeneralizedQueueArray;

/**
 * A system table implemented as an array for the keys and an array for the
 * values of the keys. The keys in the key-array are ordered in an ascending
 * order. Only the necessary methods for this task are included in this class.
 * The main method includes a frequency counter of keys and time measurements
 * for comparisons to other algorithms.
 * 
 * @author Antonio
 *
 * @param <Key> For this task the keys are words from stdin.
 * @param <Value> For this task the values are number of occurrences of the key
 *        from stdin.
 */
@SuppressWarnings("unchecked")
public class OrderedArrayST<Key extends Comparable<Key>, Value> {
    private Key[] keys = (Key[]) new Comparable[1];
    private Value[] values = (Value[]) new Object[1];
    private int size;

    /**
     * Creates and initializes.
     */
    public OrderedArrayST() {
        size = 0;
    }

    /**
     * Returns the size of the array(s).
     * 
     * @return the size.
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the arrays are empty.
     * 
     * @return <code>true</code> if it is empty, <code>false</code> is not.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    private void resizeArrays(int newSize) {
        Key[] newKeyArray = (Key[]) new Comparable[newSize];
        Value[] newValueArray = (Value[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            newKeyArray[i] = keys[i];
            newValueArray[i] = values[i];
        }
        keys = newKeyArray;
        values = newValueArray;
    }

    /**
     * Checks the rank of the given key in the interval between the given limits.
     * The rank is the number of keys that are smaller than the given key in the
     * interval. Because the array is ordered we can use binary search for finding
     * the rank. If the key is not in the array of keys it will still return the
     * rank for it.
     * 
     * @param key  The given key to find its rank.
     * @param low  Low limit of the interval.
     * @param high High limit of the interval.
     * @return the rank of the given key; meaning the number of keys smaller than
     *         the given key.
     * @throws IllegalArgumentException if the key is <code>null</code>.
     */
    public int rank(Key key, int low, int high) {
        if (key == null)
            throw new IllegalArgumentException("Key is null.");
        if (high < low)
            return low;
        int mid = low + (high - low) / 2;
        int compareResult = key.compareTo(keys[mid]);
        if (compareResult < 0)
            return rank(key, low, mid - 1);
        else if (compareResult > 0)
            return rank(key, mid + 1, high);
        else
            return mid;
    }

    /**
     * Puts the given key with its given value in the correct position in the table
     * if it is not already in it; meaning that the key in the position and the keys
     * to the right of it are moved one position to the right. If it is already in
     * the table the value of the key will be updated with the new given value. If
     * necessary the arrays will be resized.
     * 
     * @param key   The key to be put/updated in the table.
     * @param value The value of the key to be put/updated.
     * @throws IllegalArgumentException if the key is <code>null</code>.
     */
    public void put(Key key, Value value) {
        if (key == null)
            throw new IllegalArgumentException("Key is null.");

        int pos = rank(key, 0, size - 1);
        if ((pos < size) && keys[pos].compareTo(key) == 0) {
            values[pos] = value;
            return;
        }

        if (size == keys.length)
            resizeArrays(size * 2);

        for (int i = size; i > pos; i--) {
            keys[i] = keys[i - 1];
            values[i] = values[i - 1];
        }

        keys[pos] = key;
        values[pos] = value;
        size++;
    }

    /**
     * Searches for the given key in the table and returns its value if found.
     * 
     * @param key The key to be searched for.
     * @return the value of the key. Returns <code>null</code> if the table is
     *         empty.
     * @throws IllegalArgumentException if the key is <code>null</code>.
     */
    public Value get(Key key) {
        if (key == null)
            throw new IllegalArgumentException("Key is null.");
        if (isEmpty())
            return null;

        int pos = rank(key, 0, size - 1);
        if ((pos < size) && keys[pos].compareTo(key) == 0)
            return values[pos];

        return null;
    }

    /**
     * Checks if the table already contains the key.
     * 
     * @param key The key to be searched for.
     * @return <code>true</code> if the table already contains the key, otherwise
     *         <code>false</code>.
     * @throws IllegalArgumentException if the key is <code>null</code>.
     */
    public boolean contains(Key key) {
        if (key == null)
            throw new IllegalArgumentException("Key is null.");
        return get(key) != null;
    }

    /**
     * Returns the key in the last position, which is the maximum key.
     * 
     * @return the maximum key.
     * @throws NoSuchElementException if the system table is empty.
     */
    public Key max() {
        if (isEmpty())
            throw new NoSuchElementException("The ST is empty!");
        return keys[size - 1];
    }

    /**
     * Returns the key in the first position, which is the minimum key.
     * 
     * @return the minimum key.
     * @throws NoSuchElementException if the system table is empty.
     */
    public Key min() {
        if (isEmpty())
            throw new NoSuchElementException("The ST is empty!");
        return keys[0];
    }

    /**
     * @return an iterable queue holding all of the keys.
     */
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    /**
     * Inserts all keys in the given key interval into an iterable queue.
     * 
     * @param low  Low limit.
     * @param high High limit.
     * @return an iterable queue holding the the keys in the given range.
     * @throws IllegalArgumentException if one of the keys is <code>null</code>.
     */
    public Iterable<Key> keys(Key low, Key high) {
        if (low == null)
            throw new IllegalArgumentException("Low key is null.");
        if (high == null)
            throw new IllegalArgumentException("High key is null.");

        GeneralizedQueueArray<Key> queue = new GeneralizedQueueArray<Key>();
        if (low.compareTo(high) > 0)
            return queue;
        for (int i = rank(low, 0, size - 1); i < rank(high, 0, size - 1); i++)
            queue.insert(keys[i]);
        return queue;
    }

    /**
     * Builds an array of <code>String</code> holding every key and its value in a
     * <code>String</code> format and in a sorted ascending order after the value
     * instead of the key. Uses shellsort for sorting the array and prints out the
     * positions in the given range; meaning the keys with the nth to n+xth highest
     * values.
     * 
     * @param start Start position.
     * @param end   End position.
     * @throws IllegalArgumentException is the range is invalid.
     */
    public void getValueInRange(int start, int end) {
        if (((start < 1 || start > size)) || ((end < start) || (end > size)))
            throw new IllegalArgumentException("Not valid range!");

        String[] keys = new String[size];
        int[] sortedValues = new int[size];
        for (int i = 0; i < size; i++) {
            keys[i] = (String) this.keys[i];
            sortedValues[i] = (int) this.values[i];
        }
        shellSort(keys, sortedValues);

        for (int i = start - 1; i < end; i++) {
            System.out.println("Rank " + (i + 1) + ": Word: \"" + keys[i] + "\", Count: " + sortedValues[i]);
        }
    }

    private void shellSort(String[] stringArray, int[] valueArray) {

        int interval = 1;
        while (interval < valueArray.length / 3)
            interval = interval * 3 + 1;
        while (interval > 0) {
            // h-sort the list
            for (int i = interval; i < valueArray.length; i++) {
                for (int j = i; j >= interval && greater(valueArray[j], valueArray[j - interval]); j = j - interval) {
                    swap(stringArray, valueArray, j, j - interval);
                }
            }
            interval = interval / 3;
        }

    }

    private boolean greater(int first, int second) {
        return first > second;
    }

    private void swap(String[] stringArray, int[] valueArray, int i, int j) {
        String tempString = stringArray[i];
        stringArray[i] = stringArray[j];
        stringArray[j] = tempString;
        int tempInt = valueArray[i];
        valueArray[i] = valueArray[j];
        valueArray[j] = tempInt;
    }

    /**
     * A text client with a frequency counter to count the words from stdin and
     * measure the time it takes for operations. Uses the
     * <code>OrderedArrayST</code> to count and store. The data printed out can be
     * used to compare with other system tables.
     * 
     * @param args args[0] The length of the stdin to read. The whole stdin will be
     *             read if 0. args[1] and args[2] is the interval range to get the
     *             nth to n+xth most frequent words, where 1 is the most frequent
     *             word.
     * @throws IllegalArgumentException if the given length is negative.
     */
    public static void main(String[] args) {
        OrderedArrayST<String, Integer> st = new OrderedArrayST<String, Integer>();
        int wordLimit = Integer.parseInt(args[0]);
        if (wordLimit < 0)
            throw new IllegalArgumentException("Please enter a valid lenght!");

        // For getting the nth to n+xth most frequent words
        int lowLimit = Integer.parseInt(args[1]);
        int highLimit = Integer.parseInt(args[2]);

        String key;
        int words = 0;
        int distinctWords = 0;
        long totalTime = 0;
        long startTime;

        if (wordLimit == 0) {
            while (!StdIn.isEmpty()) {
                key = StdIn.readString().toLowerCase();
                if (key.length() < 2)
                    continue;

                words++;
                startTime = System.nanoTime();
                if (st.contains(key)) {
                    st.put(key, st.get(key) + 1);
                } else {
                    st.put(key, 1);
                    distinctWords++;
                }
                totalTime += System.nanoTime() - startTime;
            }
        } else {
            while (words < wordLimit) {
                key = StdIn.readString().toLowerCase();

                // don't read words 1-letters word.
                if (key.length() < 2)
                    continue;

                words++;
                startTime = System.nanoTime();
                if (st.contains(key)) {
                    st.put(key, st.get(key) + 1);
                } else {
                    st.put(key, 1);
                    distinctWords++;
                }
                totalTime += System.nanoTime() - startTime;
            }
        }

        double time = (double) totalTime / 1E9;
        System.out.println("Words: " + words + "\nDisctinct Words: " + distinctWords);
        System.out.printf("Execution time (s) for counting words: %.9f\n", time);

        totalTime = 0; // reset
        String max = "";
        st.put(max, 0);
        startTime = System.nanoTime();
        for (String word : st.keys())
            if (st.get(word) > st.get(max))
                max = word;
        totalTime = System.nanoTime() - startTime;
        time = (double) totalTime / 1E9;
        System.out.println("Word with max count: " + max);
        System.out.printf("Execution time (s) for finding max word: %.9f\n", time);

        totalTime = 0; // reset
        System.out.println("TOP 5 Words:");
        startTime = System.nanoTime();
        st.getValueInRange(lowLimit, highLimit);
        totalTime = System.nanoTime() - startTime;
        time = (double) totalTime / 1E9;
        System.out.printf("Execution time (s) for sorting and getting top X Words (s): %.9f\n", time);
    }

}
