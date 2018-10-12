package lab4;

/**
 * This class searches for a directed cycle in a given directed graph using the
 * depth-first search algorithm and a boolean array that represents if a vertex
 * is in a nonexisting stack. This is for finding directed cycles.
 * 
 * @author Antonio
 *
 */
public class DirectedCycle {
    private boolean[] marked; // marked[v], true if vertex v has been marked
    private int[] edgeTo; // edgeTo[v], previous vertex on path to v
    private boolean[] onStack; // onStack[v], true if vertex v is on stack
    private Stack<Integer> cycle; // the directed cycle or null if no cycle

    /**
     * Searches for a directed cycle in the given digraph using the dfs algorithm
     * and builds it if found.
     * 
     * @param digraph the digraph.
     */
    public DirectedCycle(Digraph digraph) {
        marked = new boolean[digraph.V()];
        onStack = new boolean[digraph.V()];
        edgeTo = new int[digraph.V()];
        for (int v = 0; v < digraph.V(); v++)
            if (!marked[v] && cycle == null)
                dfs(digraph, v);
    }

    private void dfs(Digraph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (this.hasCycle()) // if cycle built/found, we are done
                return;
            else if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }

            // If cycle found, build stack
            else if (onStack[w]) {
                cycle = new Stack<Integer>();
                for (int x = v; x != w; x = edgeTo[x])
                    cycle.push(x);

                cycle.push(w);
                cycle.push(v);
            }
        }
        onStack[v] = false;
    }

    /**
     * Checks if the digraph has a directed cycle.
     * 
     * @return <code>true</code> if the digraph has a directed cycle,
     *         <code>false</code> otherwise.
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * Returns a directed cycle if one was found on the digraph; returns
     * <code>null</code> otherwise.
     * 
     * @return a directed cycle as an iterable if one was found on the digraph; or
     *         <code>null</code> otherwise.
     */
    public Iterable<Integer> cycle() {
        return cycle;
    }

    /**
     * Unit tests for finding a directed cycle. This test client is hardcoded in
     * some parts for this laboration task. Since the "lab4input.txt" has no cycles,
     * a trivial edge that creates one will be added.
     *
     * @param args args[0] the name of the file holding the graph; args[1] the
     *             delimiter.
     */
    public static void main(String[] args) {
        String filename = args[0];
        String delimiter = args[1];
        SymbolDigraph symbolDigraph = new SymbolDigraph(filename, delimiter);
        Digraph digraph = symbolDigraph.digraph();

        DirectedCycle cycleFinder = new DirectedCycle(digraph);

        if (cycleFinder.hasCycle()) {
            System.out.println("A directed cycle was found!");
            System.out.print("Directed cycle: ");
            for (int i : cycleFinder.cycle()) {
                System.out.print(symbolDigraph.nameOf(i) + " ");
            }
            System.out.println();
        } else
            System.out.println("No directed cycle was found.");

        // Hard coded for lab4input.txt
        System.out.println("Putting a hardcoded edge TN-FL for the graph in lab4input.txt");
        int v = symbolDigraph.indexOf("TN");
        int w = symbolDigraph.indexOf("FL");
        digraph.addEdge(v, w);
        cycleFinder = new DirectedCycle(digraph);
        if (cycleFinder.hasCycle()) {
            System.out.println("A directed cycle was found!");
            System.out.print("Directed cycle: ");
            for (int i : cycleFinder.cycle()) {
                System.out.print(symbolDigraph.nameOf(i) + " ");
            }
            System.out.println();
        } else
            System.out.println("No directed cycle was found.");
    }
}
