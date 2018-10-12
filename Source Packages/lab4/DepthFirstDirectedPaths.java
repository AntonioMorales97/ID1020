package lab4;

import java.util.NoSuchElementException;

import lab1.GeneralizedQueueArray;

/**
 * Finds a directed path from a source vertex to every other vertex (if such
 * path exists) in a directed graph.
 * 
 * @author Antonio
 *
 */
public class DepthFirstDirectedPaths {
    private boolean[] marked; // marked[v], true if v is reachable from s
    private int[] edgeTo; // edgeTo[v], last edge on path from s to v
    private final int s; // source vertex

    /**
     * Computes a directed path from s to every other vertex in the given digraph.
     * 
     * @param digraph the digraph.
     * @param s       the source vertex.
     * @throws IllegalArgumentException if the vertex s is out of bounds.
     */
    public DepthFirstDirectedPaths(Digraph digraph, int s) {
        marked = new boolean[digraph.V()];
        edgeTo = new int[digraph.V()];
        this.s = s;
        validateVertex(s);
        dfs(digraph, s);
    }

    // DFS from vertex v
    private void dfs(Digraph digraph, int v) {
        marked[v] = true;
        for (int w : digraph.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(digraph, w);
            }
        }
    }

    /**
     * Checks if there is a directed path from the source vertex to the given vertex
     * v.
     * 
     * @param v the vertex.
     * @return <code>true</code> if there is a path from the source vertex to the
     *         given vertex v; <code>false</code> otherwise.
     * @throws IllegalArgumentException if v is out of bounds.
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns a directed path from the source vertex to vertex v, or
     * <code>null</code> if there is no directed path.
     * 
     * @param v the vertex.
     * @return the sequence of vertices on a directed path from the source vertex to
     *         the vertex v as an iterable.
     * @throws IllegalArgumentException if v is out of bounds.
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
     * Simple unit test.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Digraph g = new Digraph(6);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(3, 0);

        int sourceVertex = 0;
        int destinationVertex = 3;

        DepthFirstDirectedPaths dfs = new DepthFirstDirectedPaths(g, sourceVertex);

        if (!dfs.hasPathTo(destinationVertex))
            throw new NoSuchElementException("No path between the given vertices");

        GeneralizedQueueArray<Integer> queue = new GeneralizedQueueArray<Integer>();
        for (int i : dfs.pathTo(destinationVertex))
            if (i == sourceVertex)
                queue.insert(i);
            else
                queue.insert(i);

        System.out.println("The path between the vertices " + sourceVertex + " and " + destinationVertex
                + " includes following vertices:");
        System.out.println(queue);
        System.out.println("Meaning that the path is: ");
        for (int i : queue)
            if (i == destinationVertex)
                System.out.print(i);
            else
                System.out.print(i + " - ");
        System.out.println();
    }
}
