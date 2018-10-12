package lab4;

/**
 * This class builds a directed graph; i.e each edge in this graph has one
 * direction.
 * 
 * @author Antonio
 *
 */
public class Digraph {
    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    /**
     * Initializes an empty digraph of the size V.
     * 
     * @param V the number of vertices.
     */
    @SuppressWarnings("unchecked")
    public Digraph(int V) {
        if (V < 0)
            throw new IllegalArgumentException("V is negative.");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Integer>();
    }

    /**
     * Returns the number of vertices in the digraph.
     * 
     * @return the number of vertices in the digraph.
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in the digraph.
     * 
     * @return the number of edges in the digraph.
     */
    public int E() {
        return E;
    }

    /**
     * Adds an edge from v to w in the digraph.
     * 
     * @param v vertex v (startpoint).
     * @param w vertex w (endpoint).
     * @throws IllegalArgumentException if at least one of the given vertices is out
     *                                  of bounds.
     */
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        E++;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("The given vertex is not in valid range!");
    }

    /**
     * Returns the adjacent vertices to the vertex v as an iterable.
     * 
     * @param v the given vertex.
     * @return the adjacent vertices to the vertex v as an iterable.
     */
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * Returns a new {@link Digraph} but where all of the edges are in a reversed
     * direction.
     * 
     * @return a reversed {@link Digraph} of this digraph.
     */
    public Digraph reverse() {
        Digraph R = new Digraph(V);
        for (int v = 0; v < V; v++)
            for (int w : adj(v))
                R.addEdge(w, v);
        return R;
    }

    /**
     * Returns a string representation of this digraph.
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
     * Unit tests to test the digraph.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Digraph digraph = new Digraph(4);
        digraph.addEdge(0, 3);
        System.out.println(digraph);
    }
}
