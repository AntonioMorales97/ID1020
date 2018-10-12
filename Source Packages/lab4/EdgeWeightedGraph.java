package lab4;

/**
 * This class represents an edge-weighted graph and uses the {@link Edge} class
 * to creates such edges.
 * 
 * @author Antonio
 *
 */
public class EdgeWeightedGraph {
    private final int V;
    private int E;
    private Bag<Edge>[] adj;

    /**
     * Initializes an empty edge-weighted graph with V vertices and 0 edges.
     *
     * @param V the number of vertices in this graph.
     * @throws IllegalArgumentException if V is negative.
     */
    @SuppressWarnings("unchecked")
    public EdgeWeightedGraph(int V) {
        if (V < 0)
            throw new IllegalArgumentException("The number of vertices cannot be negative!");
        this.V = V;
        this.E = 0;
        adj = (Bag<Edge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Edge>();
        }
    }

    /**
     * Returns the number of vertices in this edge-weighted graph.
     *
     * @return the number of vertices in this graph.
     */
    public int V() {
        return V;
    }

    /**
     * Returns the number of edges in this edge-weighted graph.
     *
     * @return the number of edges in this graph.
     */
    public int E() {
        return E;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("Vertex " + v + " is not in valid range!");
    }

    /**
     * Adds the undirected {@link Edge} to this edge-weighted graph.
     *
     * @param edge the edge
     * @throws IllegalArgumentException if at least one of the endpoints in the
     *                                  given edge is out of bounds.
     */
    public void addEdge(Edge edge) {
        int v = edge.either();
        int w = edge.other(v);
        validateVertex(v);
        validateVertex(w);
        adj[v].add(edge);
        adj[w].add(edge);
        E++;
    }

    /**
     * Returns the edges from the given vertex v.
     *
     * @param v the vertex.
     * @return the edges from vertex v as an iterable.
     * @throws IllegalArgumentException if the vertex v is out of bounds.
     */
    public Iterable<Edge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    /**
     * Returns the degree of the given vertex.
     *
     * @param v the vertex.
     * @return the degree of vertex.
     * @throws IllegalArgumentException if the vertex v is out of bounds.
     */
    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    /**
     * Returns all of the edges in this edge-weighted graph.
     *
     * @return all edges in this edge-weighted graph as an iterable.
     */
    public Iterable<Edge> edges() {
        Bag<Edge> bag = new Bag<Edge>();
        for (int v = 0; v < V; v++) {
            for (Edge e : adj(v)) {
                if (e.other(v) > v) { // ignore self-loops
                    bag.add(e);
                }
            }
        }
        return bag;
    }

    /**
     * Returns a string representation of this edge-weighted graph.
     *
     * @return the number of vertices and edges in this graph and followed by the
     *         adjacency lists of edges as a <code>String</code>.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + "\n");
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (Edge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    /**
     * Unit test to test the EdgeWeightedGraph.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Edge testEdge = new Edge(0, 1, 4.5);
        EdgeWeightedGraph graph = new EdgeWeightedGraph(3);
        graph.addEdge(testEdge);
        System.out.println(graph);
    }
}
