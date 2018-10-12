package lab4;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.In;
import lab1.GeneralizedQueueArray;
import lab3.RedBlackTree;

/**
 * This class represents an edge-weighted symbol graph, where the weights of the
 * edges is hardcoded to increment with 1 after each new edge of a graph is read
 * in from the given file containing the symbol graph. The main method is a test
 * client to find the shortest path between a source vertex and a given vertex
 * using Dijsktra's algorithm. See {@link DijkstraUndirectedSP}.
 * 
 * @author Antonio
 *
 */
public class SymbolWeightedGraph {
    private double uniqueWeight = 1.0; // increases for every edge to give each edge a unique weight.
    private RedBlackTree<String, Integer> tree; // Each string key has a unique index value.
    private String[] keys; // Each index corresponds to a string.
    private EdgeWeightedGraph graph; // the graph associated with this symbol graph.

    /**
     * Initializes a graph from a file named after the given filename and using the
     * specified delimiter. Every line in the file should contain the name of a
     * vertex followed by the names of vertices adjacent to the vertex and separated
     * by the given delimiter.
     * 
     * @param filename  the name of the file that the graph will represent.
     * @param delimiter the delimiter between the vertex and its adjacent vertices.
     */
    public SymbolWeightedGraph(String filename, String delimiter) {
        tree = new RedBlackTree<String, Integer>();

        // Build tree by setting an index to distinct strings
        In in = new In(filename);
        while (!in.isEmpty()) {
            String[] a = in.readLine().split(delimiter);
            for (int i = 0; i < a.length; i++)
                if (!tree.contains(a[i]))
                    tree.put(a[i], tree.size());

        }

        // Invert tree to get the String keys in an array
        keys = new String[tree.size()];
        for (String name : tree.keys())
            keys[tree.get(name)] = name;

        // Build graph by adding edge from first vertex on each line to its adjacent
        // vertices.
        graph = new EdgeWeightedGraph(tree.size());
        in = new In(filename);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(delimiter);
            int v = tree.get(a[0]);
            for (int i = 1; i < a.length; i++) {
                int w = tree.get(a[i]);
                graph.addEdge(new Edge(v, w, uniqueWeight++));
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
     * @return the {@link Graph} associated with this symbol graph.
     */
    public EdgeWeightedGraph graph() {
        return graph;
    }

    private void validateVertex(int v) {
        int V = graph.V();
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("The vertex v is not in valid range!");
    }

    /**
     * Unit test that searches for the shortest path in a graph from a given source
     * vertex and a given destination vertex in a given file that holds the graph.
     * Uses a symbol weighted graph and Dijkstra's algorithm for finding the
     * shortest weighted path in a undirected graph, see
     * {@link DijkstraUndirectedSP}.
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
        SymbolWeightedGraph symbolWeightedGraph = new SymbolWeightedGraph(filename, delimiter);
        EdgeWeightedGraph graph = symbolWeightedGraph.graph();
        DijkstraUndirectedSP dijkstra = new DijkstraUndirectedSP(graph, symbolWeightedGraph.indexOf(sourceVertex));

        if (!dijkstra.hasPathTo(symbolWeightedGraph.indexOf(destinationVertex)))
            throw new NoSuchElementException("No path between the given vertices.");

        GeneralizedQueueArray<Edge> queue = new GeneralizedQueueArray<Edge>();

        for (Edge e : dijkstra.pathTo(symbolWeightedGraph.indexOf(destinationVertex)))
            queue.insert(e);

        System.out.println("The weight of the shortest path between the vertices " + sourceVertex + " and "
                + destinationVertex + " is " + dijkstra.distTo(symbolWeightedGraph.indexOf(destinationVertex))
                + " and includes following edges:");
        for (Edge e : queue) {
            int v = e.either();
            int w = e.other(v);
            System.out.println(
                    symbolWeightedGraph.nameOf(v) + "-" + symbolWeightedGraph.nameOf(w) + " (" + e.weight() + ")");
        }
    }
}
