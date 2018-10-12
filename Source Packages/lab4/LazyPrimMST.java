package lab4;

import edu.princeton.cs.algs4.MinPQ;
import lab1.GeneralizedQueueArray;

/**
 * This class computes a lazy version of Prim's algorithm for finding the
 * minimum spanning tree of an edge-weighted graph that has the given vertex in
 * it. Since the vertices are symbols in this lab it uses the
 * {@link SymbolWeightedGraph} and also to find the largest component in such
 * graph (according to the lab task) it also uses {@link ConnectedComponents}.
 * 
 * @author Antonio
 *
 */
public class LazyPrimMST {
    private double weight; // total weight of the minimum spanning tree
    private GeneralizedQueueArray<Edge> mst; // edges in the MST
    private boolean[] marked; // marked[v], true if v is in the tree
    private MinPQ<Edge> pq; // edges with one endpoint in the tree

    /**
     * Compute a minimum spanning tree of the connected component which has the
     * given vertex s in the given graph.
     * 
     * @param s     the source vertex on which Prim's algorithm will start from.
     * @param graph the edge-weighted graph
     */
    public LazyPrimMST(EdgeWeightedGraph graph, int s) {
        mst = new GeneralizedQueueArray<Edge>();
        pq = new MinPQ<Edge>();
        marked = new boolean[graph.V()];
        prim(graph, s);
    }

    private void prim(EdgeWeightedGraph G, int s) {
        visit(G, s);
        while (!pq.isEmpty()) { // Stop when mst has V-1 edges
            Edge e = pq.delMin(); // smallest edge in the prio queue
            int v = e.either();
            int w = e.other(v);

            if (marked[v] && marked[w])
                continue; // lazy version, skip if ineligible

            mst.insert(e); // add edge to MST
            weight += e.weight();
            if (!marked[v])
                visit(G, v); // add v to the mst
            if (!marked[w])
                visit(G, w); // add w to the mst
        }
    }

    // add all edges adjacent to the vertex v into the prio queue if the other
    // endpoint has not yet been
    // visited
    private void visit(EdgeWeightedGraph graph, int v) {
        marked[v] = true;
        for (Edge e : graph.adj(v))
            if (!marked[e.other(v)])
                pq.insert(e);
    }

    /**
     * Returns the edges in a minimum spanning tree or a minimum spanning forest.
     * 
     * @return the edges in a minimum spanning tree or minimum spanning forest as an
     *         iterable of edges
     */
    public Iterable<Edge> edges() {
        return mst;
    }

    /**
     * Returns the total weight of all the edges in a minimum spanning tree or a
     * minimum spanning forest.
     * 
     * @return the total weight of the edges in a minimum spanning tree or minimum
     *         spanning forest.
     */
    public double weight() {
        return weight;
    }

    /**
     * Unit test to test the lazy version of Prim's algorithm; i.e finding a minimum
     * spanning tree.
     *
     * @param args args[0] the name of the graph file; args[1] the delimiter.
     */
    public static void main(String[] args) {
        String filename = args[0];
        String delimiter = args[1];
        SymbolWeightedGraph symbolWeightedGraph = new SymbolWeightedGraph(filename, delimiter);
        EdgeWeightedGraph graph = symbolWeightedGraph.graph();
        ConnectedComponents cc = new ConnectedComponents(graph);
        int vertexInMaxComp = cc.getVertexInMaxComp();

        LazyPrimMST mst = new LazyPrimMST(graph, vertexInMaxComp);
        for (Edge e : mst.edges())
            System.out.println(symbolWeightedGraph.nameOf(e.either()) + " - "
                    + symbolWeightedGraph.nameOf(e.other(e.either())) + " (" + e.weight() + ")");

        System.out.println("Total weight: " + mst.weight());
    }

}
