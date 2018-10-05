package lab3;

import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class FrequencyCounter {
    // Do not instantiate.
    private FrequencyCounter() {
    }

    /**
     * Reads in a command-line integer and sequence of words from standard input and
     * prints out a word (whose length exceeds the threshold) that occurs most
     * frequently to standard output. It also prints out the number of words whose
     * length exceeds the threshold and the number of distinct such words.
     *
     * @param args the command-line arguments (quantity of words to read)
     */
    public static void main(String[] args) {
        int distinct = 0, words = 0;
        int length = Integer.parseInt(args[0]);
        if (length < 0)
            throw new IllegalArgumentException("Enter a valid length!");

        ST<String, Integer> st = new ST<String, Integer>();
        String key;

        if (length == 0) {
            while (!StdIn.isEmpty()) {
                key = StdIn.readString().toLowerCase();
                if (key.length() < 2)
                    continue;
                words++;
                if (st.contains(key)) {
                    st.put(key, st.get(key) + 1);
                } else {
                    st.put(key, 1);
                    distinct++;
                }
            }
        } else {
            // compute limited words
            for (int i = 0; i < length; i++) {
                key = StdIn.readString().toLowerCase();
                if (key.length() < 2)
                    continue;
                words++;
                if (st.contains(key)) {
                    st.put(key, st.get(key) + 1);
                } else {
                    st.put(key, 1);
                    distinct++;
                }
            }
        }

        // find a key with the highest frequency count
        String max = "";
        st.put(max, 0);
        for (String word : st.keys()) {
            if (st.get(word) > st.get(max))
                max = word;
        }

        StdOut.println(max + " " + st.get(max));
        StdOut.println("distinct = " + distinct);
        StdOut.println("words    = " + words);
    }

}
