package lab4;

/**
 * This program computes the topological order of a directed graph using
 * {@link DirectedCycle} to find if the digraph has cycles and
 * {@link DepthFirstOrder} to find the topological order; which is the reversed
 * postorder. See {@link DepthFirstOrder} to see how the order is computed.
 * 
 * @author Antonio
 *
 */
public class Topological {
    private Iterable<Integer> topologicalOrder; // topological order

    /**
     * Builds the topological order of the given digraph if and only if it is
     * directed acyclic graph. The topological order of such DAG is its reversed
     * postorder.
     * 
     * @param digraph the digraph
     */
    public Topological(Digraph digraph) {
        DirectedCycle cycleFinder = new DirectedCycle(digraph);
        if (!cycleFinder.hasCycle()) {
            DepthFirstOrder dfOrder = new DepthFirstOrder(digraph);
            topologicalOrder = dfOrder.reversePost();
        }
    }

    /**
     * Returns the topological order of the given digraph as an iterable or
     * <code>null</code> if there is no such order.
     * 
     * @return the topological order as an iterable or <code>null</code> if there is
     *         none.
     */
    public Iterable<Integer> order() {
        return topologicalOrder;
    }

    /**
     * Checks if the digraph is a DAG (directed acyclic graph).
     * 
     * @return <code>true</code> if the given digraph is a DAG, <code>false</code>
     *         otherwise.
     */
    public boolean isDAG() {
        return topologicalOrder == null;
    }

    /**
     * Unit test that computes the topological order of the graph in the given file
     * if such order exists.
     * 
     * @param args args[0] the filename of the file holding the graph; args[1] the
     *             delimiter.
     */
    public static void main(String[] args) {
        String filename = args[0];
        String delimiter = args[1];
        SymbolDigraph symbolDigraph = new SymbolDigraph(filename, delimiter);
        Topological topological = new Topological(symbolDigraph.digraph());
        for (int v : topological.order())
            System.out.println(symbolDigraph.nameOf(v));
    }
}
