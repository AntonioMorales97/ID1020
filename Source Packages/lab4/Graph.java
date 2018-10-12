package lab4;

/**
 * This class represents a graph without edges with weights and also without
 * directions.
 * 
 * @author Antonio
 *
 */
public class Graph {
    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    /**
     * Initializes an empty graph with V vertices and 0 edges.
     *
     * @param V the number of vertices.
     * @throws IllegalArgumentException if V is negative.
     */
    @SuppressWarnings("unchecked")
    public Graph(int V) {
        if (V < 0)
            throw new IllegalArgumentException("V is negative.");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this graph.
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph.
     */
    public int E() {
        return E;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("The given vertex is not in valid range!");
    }

    /**
     * Adds the undirected edge v to w (w to v) to this graph.
     *
     * @param v one vertex in the edge
     * @param w the other vertex in the edge
     * @throws IllegalArgumentException if at least one of the given vertices is out
     *                                  of bounds.
     */
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    /**
     * Returns the vertices adjacent to the vertex v.
     *
     * @param v the vertex
     * @return the vertices adjacent to vertex v as an iterable bag.
     * @throws IllegalArgumentException if the vertex v is out of bounds.
     */
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * Returns the degree of vertex v.
     *
     * @param v the vertex.
     * @return the degree of vertex v.
     * @throws IllegalArgumentException if the vertex v is out of bounds.
     */
    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    /**
     * Returns a string representation of this graph.
     *
     * @return the number of vertices and the number of edges followed by the
     *         adjacency lists as a <code>String</code>.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + "\n");
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    /**
     * Simple unit test.
     *
     * @param args Not used here.
     */
    public static void main(String[] args) {
        Graph graph = new Graph(4);
        graph.addEdge(0, 3);
        System.out.println(graph);
    }

}
