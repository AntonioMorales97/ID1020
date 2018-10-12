package lab4;

import java.util.NoSuchElementException;

import lab1.DoublyLinkedQueue;
import lab1.GeneralizedQueueArray;

/**
 * This program will find the shortest path (if it exists one) between a source
 * vertex and every other vertices in a undirected graph without weights.
 * 
 * @author Antonio
 *
 */
public class BreadthFirstPaths {
    private boolean[] marked; // marked[v], true of there is a path between s and v
    private int[] edgeTo; // edgeTo[v], previous edge on the s to v path
    private int[] distTo; // distTo[v], number of edges on the shortest s to v path

    /**
     * Calculates the shortest path between the given source vertex and every other
     * vertices in the given graph.
     * 
     * @param graph the graph
     * @param s     the source vertex
     * @throws IllegalArgumentException if the source vertex is negative or out of
     *                                  bounds.
     */
    public BreadthFirstPaths(Graph graph, int s) {
        marked = new boolean[graph.V()];
        distTo = new int[graph.V()];
        edgeTo = new int[graph.V()];
        validateVertex(s);
        bfs(graph, s);
    }

    // BFS from source vertex. DoublyLinkedQueue is used here, but any other queue
    // (FIFO)
    // works fine.
    private void bfs(Graph graph, int s) {
        DoublyLinkedQueue<Integer> queue = new DoublyLinkedQueue<Integer>();
        distTo[s] = 0;
        marked[s] = true;
        queue.enqueue(s);

        while (!queue.isEmpty()) {
            int vertex = queue.dequeue();
            for (int i : graph.adj(vertex)) {
                if (!marked[i]) {
                    edgeTo[i] = vertex;
                    distTo[i] = distTo[vertex] + 1;
                    marked[i] = true;
                    queue.enqueue(i);
                }
            }
        }
    }

    /**
     * Checks if the marked-array is marked in position v, i.e if there is a path
     * between the source vertex and the given vertex.
     * 
     * @param v the vertex
     * @return <code>true</code> if there is a path, <code>false</code> otherwise.
     * @throws IllegalArgumentException if v is negative or out of bounds.
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
     * Returns the shortest path between the source vertex and the given vertex.
     * 
     * @param v the vertex.
     * @return the vertices on the shortest path as an iterable stack or
     *         <code>null</code> if there is no path.
     * @throws IllegalArgumentException if v is negative or out of bounds.
     */
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v))
            return null;
        Stack<Integer> path = new Stack<Integer>();
        int i;
        for (i = v; distTo[i] != 0; i = edgeTo[i])
            path.push(i);
        path.push(i);
        return path;
    }

    private void validateVertex(int v) {
        int markedLength = marked.length;
        if (v < 0 || v >= markedLength)
            throw new IllegalArgumentException("The vertex " + v + " is not in valid range!");
    }

    /**
     * Test client for finding the shortest path between vertices x (sourceVertex)
     * and y (destinationVertex). For simplicity the graph is hardcoded and so are
     * the vertices x and y.
     *
     * @param args Not used here.
     */
    public static void main(String[] args) {

        Graph g = new Graph(6);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(0, 3);

        int sourceVertex = 0;
        int destinationVertex = 3;

        BreadthFirstPaths bfs = new BreadthFirstPaths(g, sourceVertex);

        if (!bfs.hasPathTo(destinationVertex))
            throw new NoSuchElementException("No path between the given vertices");

        GeneralizedQueueArray<Integer> queue = new GeneralizedQueueArray<Integer>();
        for (int i : bfs.pathTo(destinationVertex))
            if (i == sourceVertex)
                queue.insert(i);
            else
                queue.insert(i);

        System.out.println("The shortest path between the vertices " + sourceVertex + " and " + destinationVertex
                + " is " + bfs.distTo(destinationVertex) + " edges and includes following vertices:");
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
