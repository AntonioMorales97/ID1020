package lab4;

import edu.princeton.cs.algs4.IndexMinPQ;

/**
 * This program searches for the shortest paths (i.e the paths with lowest
 * weights) from a source vertex to every other vertex in an edge-weighted
 * graph.
 * 
 * @author Antonio
 *
 */
public class DijkstraUndirectedSP {
    private double[] distTo; // distTo[v], holds the distance of shortest s to v path
    private Edge[] edgeTo; // edgeTo[v], holds the last edge on shortest s to v path
    private IndexMinPQ<Double> pq; // The index priority queue of vertices

    /**
     * Computes a shortest-paths tree from the source vertex s to every other vertex
     * in the edge-weighted graph.
     *
     * @param graph the edge-weighted graph.
     * @param s     the source vertex.
     * @throws IllegalArgumentException if the weight of an edge is negative.
     * @throws IllegalArgumentException if s is out of bounds.
     */
    public DijkstraUndirectedSP(EdgeWeightedGraph graph, int s) {
        for (Edge e : graph.edges()) {
            if (e.weight() < 0)
                throw new IllegalArgumentException("The weight of the edge " + e + " is negative.");
        }

        distTo = new double[graph.V()];
        edgeTo = new Edge[graph.V()];

        validateVertex(s);

        for (int v = 0; v < graph.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY; // Init
        distTo[s] = 0.0;

        // Relax vertices in order of distance from source vertex
        pq = new IndexMinPQ<Double>(graph.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (Edge e : graph.adj(v))
                relax(e, v);
        }

    }

    // Relax edge e and update tree if needed
    private void relax(Edge e, int v) {
        int w = e.other(v);
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w))
                pq.decreaseKey(w, distTo[w]);
            else
                pq.insert(w, distTo[w]);
        }
    }

    /**
     * Returns the length of a shortest path between the source vertex and vertex v.
     *
     * @param v the destination vertex.
     * @return the length of a shortest path between the source vertex and the
     *         vertex v. Returns <code>Double.POSITIVE_INFINITY</code> if there is
     *         no path.
     * @throws IllegalArgumentException if v is out of bounds.
     */
    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    /**
     * Checks if there is a path from the source vertex to the given vertex v.
     *
     * @param v the destination vertex.
     * @return <code>true</code> if there is such path, <code>false</code>
     *         otherwise.
     * @throws IllegalArgumentException if v is out of bounds.
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns a shortest path between the source vertex and vertex v as an
     * iterable.
     *
     * @param v the destination vertex
     * @return a shortest iterable path between the source vertex and vertex v.
     *         Returns <code>null</code> if there is no path.
     * @throws IllegalArgumentException if v is out of bounds.
     */
    public Iterable<Edge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v))
            return null;
        Stack<Edge> path = new Stack<Edge>();
        int edge = v;
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[edge]) {
            path.push(e);
            edge = e.other(edge);
        }
        return path;
    }

    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("The given vertex is not in valid range!");
    }

    /**
     * Simple unit test that searches for the shortest weighted path in a graph
     * using Dijsktra's algorithm for finding shortest undirected weighted paths.
     *
     * @param args Not used here.
     */
    public static void main(String[] args) {
        EdgeWeightedGraph graph = new EdgeWeightedGraph(5);
        Edge i = new Edge(0, 1, 1.0);
        Edge j = new Edge(1, 2, 1.0);
        Edge k = new Edge(0, 2, 0.5);
        graph.addEdge(i);
        graph.addEdge(j);
        graph.addEdge(k);
        DijkstraUndirectedSP sp = new DijkstraUndirectedSP(graph, 0);
        System.out.println(sp.distTo(2));

    }
}
