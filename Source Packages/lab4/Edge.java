package lab4;

/**
 * This class represents an edge with a weight between a vertex to another
 * vertex. Used for edge-weighted graphs. See {@link EdgeWeightedGraph}.
 * 
 * @author Antonio
 *
 */
public class Edge implements Comparable<Edge> {

    private final int v; // one endpoint vertex
    private final int w; // other endpoint vertex
    private final double weight;

    /**
     * Initializes an edge between vertex v and vertex w with the given weight.
     *
     * @param v      one vertex.
     * @param w      the other vertex.
     * @param weight the weight of the initialized edge.
     * @throws IllegalArgumentException if either v or w is negative.
     * @throws IllegalArgumentException if the given weight is not a number.
     */
    public Edge(int v, int w, double weight) {
        if (v < 0)
            throw new IllegalArgumentException("First vertex must be a positive integer!");
        if (w < 0)
            throw new IllegalArgumentException("Second vertex must be a positive integer!");
        if (Double.isNaN(weight))
            throw new IllegalArgumentException("The given weight is not a number!");
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    /**
     * Returns the weight of this edge.
     *
     * @return the weight of this edge
     */
    public double weight() {
        return weight;
    }

    /**
     * Returns either of this edge's vertices.
     *
     * @return the other vertex, an endpoint.
     */
    public int either() {
        return v;
    }

    /**
     * Returns the other endpoint of this edge that is not the given vertex.
     *
     * @param vertex one endpoint of this edge.
     * @return the other endpoint of this edge.
     * @throws IllegalArgumentException if the vertex is not an endpoint of this
     *                                  edge.
     */
    public int other(int vertex) {
        if (vertex == v)
            return w;
        else if (vertex == w)
            return v;
        else
            throw new IllegalArgumentException("Not an endpoint of this edge!");
    }

    /**
     * Compares two edges by their weights.
     * 
     * @param otherEdge the other edge to be compared to.
     * @return a negative integer, zero, or positive integer if the weight of this
     *         is less than, equal to, or greater than the given edge.
     */
    @Override
    public int compareTo(Edge otherEdge) {
        return Double.compare(this.weight, otherEdge.weight);
    }

    /**
     * Returns a string representation of this edge.
     *
     * @return a string representation of this edge
     */
    public String toString() {
        return String.format("%d-%d %.5f", v, w, weight);
    }

    /**
     * Simple unit test.
     *
     * @param args Not used here.
     */
    public static void main(String[] args) {
        Edge edge = new Edge(0, 2, 13.37);
        System.out.println(edge);
    }

}
