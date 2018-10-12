package lab4;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.In;
import lab1.GeneralizedQueueArray;
import lab3.RedBlackTree;

/**
 * This class represents a symbol directed graph where the edges are without
 * weights. In the main method there is a test client for finding paths from a
 * source vertex to a given vertex in such graph.
 * 
 * @author Antonio
 *
 */
public class SymbolDigraph {
    private RedBlackTree<String, Integer> tree; // Each string key has a unique index value
    private String[] keys; // Each index corresponds to a string
    private Digraph digraph; // the digraph associated with this symbol digraph

    /**
     * Initializes a graph from a file named after the given filename and using the
     * specified delimiter. Every line in the file should contain the name of a
     * vertex followed by the names of vertices adjacent to the vertex and separated
     * by the given delimiter.
     * 
     * @param filename  the name of the file that the graph will represent.
     * @param delimiter the delimiter between the vertex and its adjacent vertices.
     */
    public SymbolDigraph(String filename, String delimiter) {
        tree = new RedBlackTree<String, Integer>();

        // Build tree by setting a unique value to distinct strings
        In in = new In(filename);
        while (!in.isEmpty()) {
            String[] a = in.readLine().split(delimiter);
            for (int i = 0; i < a.length; i++)
                if (!tree.contains(a[i]))
                    tree.put(a[i], tree.size());

        }

        // Invert tree to get the string keys in an array
        keys = new String[tree.size()];
        for (String name : tree.keys())
            keys[tree.get(name)] = name;

        // Build graph by adding edge from first vertex on each line to its adjacent
        // vertices.
        digraph = new Digraph(tree.size());
        in = new In(filename);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(delimiter);
            int v = tree.get(a[0]);
            for (int i = 1; i < a.length; i++) {
                int w = tree.get(a[i]);
                digraph.addEdge(v, w);
            }
        }
    }

    /**
     * Checks if the graph contains the given <code>String</code> vertex by checking
     * if the tree has it.
     * 
     * @param vertex the name of the vertex.
     * @return <code>true</code> if it contains the vertex, <code>false</code>
     *         otherwise.
     */
    public boolean contains(String vertex) {
        return tree.contains(vertex);
    }

    /**
     * Returns the integer associated with the <code>String</code> vertex.
     * 
     * @param vertex the name of the vertex.
     * @return the integer associated with the vertex.
     */
    public int indexOf(String vertex) {
        return tree.get(vertex);
    }

    /**
     * Returns the name of the vertex associated with the integer position.
     * 
     * @param pos the integer corresponding to a vertex (a position in the key
     *            array).
     * @return the name of the vertex associated with the integer pos.
     * @throws IllegalArgumentException if pos is negative or out of bounds.
     */
    public String nameOf(int pos) {
        validateVertex(pos);
        return keys[pos];
    }

    /**
     * Returns the graph associated with this symbol graph.
     * 
     * @return the {@link Digraph} associated with this symbol graph.
     */
    public Digraph digraph() {
        return digraph;
    }

    private void validateVertex(int v) {
        int V = digraph.V();
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("The vertex " + v + " is not in valid range!");
    }

    /**
     * Unit test that tests if there is a directed path between a given source
     * vertex and a destination vertex in the file holding the graph (Uses a symbol
     * directed graph).
     *
     * @param args the command-line arguments: args[0] the name of the file; args[1]
     *             the delimiter; args[2] the source vertex; args[3] the destination
     *             vertex.
     */
    public static void main(String[] args) {
        String filename = args[0];
        String delimiter = args[1];
        String sourceVertex = args[2];
        String destinationVertex = args[3];
        SymbolDigraph symbolDigraph = new SymbolDigraph(filename, delimiter);
        Digraph digraph = symbolDigraph.digraph();
        DepthFirstDirectedPaths dfs = new DepthFirstDirectedPaths(digraph, symbolDigraph.indexOf(sourceVertex));

        if (!dfs.hasPathTo(symbolDigraph.indexOf(destinationVertex)))
            throw new NoSuchElementException("No path between the given vertices.");

        GeneralizedQueueArray<String> queue = new GeneralizedQueueArray<String>();

        for (int i : dfs.pathTo(symbolDigraph.indexOf(destinationVertex)))
            if (i == symbolDigraph.indexOf(sourceVertex))
                queue.insert(sourceVertex);
            else
                queue.insert(symbolDigraph.nameOf(i));

        System.out.println("The path between the vertices " + sourceVertex + " and " + destinationVertex
                + " includes following vertices:");
        System.out.println(queue);
        System.out.println("Meaning that the path is: ");
        for (String i : queue)
            if (i.equals(destinationVertex))
                System.out.print(i);
            else
                System.out.print(i + " - ");
        System.out.println();

    }

}
