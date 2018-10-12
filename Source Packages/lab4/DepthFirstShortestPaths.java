package lab4;

/**
 * Searches for the shortest paths (if such paths exists) from a source vertex
 * to every other vertex in a graph using a modified depth-first search
 * algorithm for finding the shortest path.
 * 
 * @author Antonio
 *
 */
public class DepthFirstShortestPaths {
    private boolean[] marked; // marked[v], true if there is a s to v path
    private int[] edgeTo; // edgeTo[v], last edge on s to v path
    private int[] distTo; // distTo[v], number of edges on the shortest s to v path
    private final int s; // source vertex

    /**
     * Computes the paths between the given source vertex s and every other vertex
     * in the given graph.
     * 
     * @param graph the graph
     * @param s     the source vertex
     * @throws IllegalArgumentException if the source vertex is out of bounds.
     */
    public DepthFirstShortestPaths(Graph graph, int s) {
        this.s = s;
        distTo = new int[graph.V()];
        edgeTo = new int[graph.V()];
        marked = new boolean[graph.V()];
        validateVertex(s);
        dfs(graph, s, 0);
    }

    // depth first search from v, but also visit the marked ones if the count is
    // less than the last distance to that vertex.
    // In this way we will keep the distTo updated with the shortest and get the
    // shortest path.
    private void dfs(Graph G, int v, int count) {
        marked[v] = true;
        distTo[v] = count;
        for (int w : G.adj(v)) {
            if (marked[w]) {
                if (count < distTo[w]) {
                    edgeTo[w] = v;
                    dfs(G, w, count + 1);
                }
            }

            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w, count + 1);
            }
        }
    }

    /**
     * Checks if there is a path from the source vertex to the given vertex v.
     * 
     * @param v the vertex
     * @return <code>true</code> if there is such path, <code>false</code>
     *         otherwise.
     * @throws IllegalArgumentException if the vertex v is out of bounds.
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns the number of edges in a shortest path between the source vertex and
     * the given vertex.
     * 
     * @param v the vertex.
     * @return the number of edges in a shortest path from the source vertex.
     * @throws IllegalArgumentException if the v is negative or out of bounds.
     */
    public int distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    /**
     * Returns a path between the source vertex and the given vertex v; or
     * <code>null</code> if there is no such path.
     * 
     * @param v the vertex
     * @return a sequence of vertices on a path between the source vertex and vertex
     *         v as an iterable.
     * @throws IllegalArgumentException if the vertex v is out of bounds.
     */
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v))
            return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("The vertex " + v + " is not in valid range!");
    }

    /**
     * Simple unit test that finds the shortest path in a created graph using the
     * DFS algorithm.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int sourceVertex = 0;
        Graph g = new Graph(5);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(0, 3);
        DepthFirstShortestPaths dfp = new DepthFirstShortestPaths(g, sourceVertex);
        for (int v = 0; v < g.V(); v++) {
            if (dfp.hasPathTo(v)) {
                System.out.print(sourceVertex + "  to " + v + ": ");
                for (int x : dfp.pathTo(v)) {
                    if (x == sourceVertex)
                        System.out.print(x);
                    else
                        System.out.print("-" + x);
                }
                System.out.println();
            } else
                System.out.println("Not connected to " + v);
        }
    }

}
