package lab3;

import java.util.List;

/**
 * Just a class to hold a key and a list of its values. Is used in the
 * {@link BinarySearchValueTree} and in the {@link RedBlackValueTree} for
 * returning more information from methods.
 * 
 * @author Antonio
 *
 * @param <Key> the key type.
 * @param <Value> the key's value type.
 */
public class WordCount<Key, Value> {
    private int count;
    private List<Value> words;

    /**
     * Simple constructor.
     * 
     * @param key    the key.
     * @param values the key's values.
     */
    public WordCount(Key key, List<Value> values) {
        this.count = (int) key;
        this.words = values;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Count: " + count + "\nWord(s):");
        str.append(words.toString());
        return str.toString();
    }
}
