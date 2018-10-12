package lab4;

import java.util.NoSuchElementException;

import lab1.GeneralizedQueueArray;

/**
 * This program will find all of the connected components in an edge-weighted
 * graph using the depth-first search algorithm and storing the id of every
 * component and the vertices belonging to them. Can be used to find the largest
 * component in a graph.
 * 
 * @author Antonio
 *
 */
public class ConnectedComponents {
    private boolean[] marked; // marked[v], true if the vertex v has been visited
    private int[] id; // id[v], id of the connected component containing vertex v
    private int[] size; // size[id], the number of vertices in the component with the given id
    private int count; // number of connected components in the graph
    private int graphSize;

    /**
     * Computes the connected components of the edge-weighted graph.
     *
     * @param graph the edge-weighted graph.
     */
    public ConnectedComponents(EdgeWeightedGraph graph) {
        graphSize = graph.V();
        marked = new boolean[graphSize];
        id = new int[graphSize];
        size = new int[graphSize];

        for (int v = 0; v < graph.V(); v++) {
            // new component
            if (!marked[v]) {
                dfs(graph, v);
                count++;
            }
        }
    }

    // DFS the edge-weighted graph from vertex v
    private void dfs(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        id[v] = count;
        size[count]++;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (!marked[w])
                dfs(G, w);
        }
    }

    /**
     * Returns the component id of the connected component containing the given
     * vertex v.
     *
     * @param v the vertex
     * @return the component id of the connected component containing vertex v.
     * @throws IllegalArgumentException if v is out of bounds.
     */
    public int id(int v) {
        validateVertex(v);
        return id[v];
    }

    /**
     * Returns the number of vertices in the connected component containing the
     * given vertex v.
     *
     * @param v the vertex
     * @return the number of vertices in the connected component containing vertex
     *         v.
     * @throws IllegalArgumentException if v is out of bounds.
     */
    public int size(int v) {
        validateVertex(v);
        return size[id[v]];
    }

    /**
     * Returns the number of vertices in the component with the given id.
     * 
     * @param id the id of the component.
     * @return the number of vertices in that component.
     */
    public int sizeOfComp(int id) {
        return size[id];
    }

    /**
     * Returns the number of connected components in the graph.
     *
     * @return the number of connected components in the graph.
     */
    public int count() {
        return count;
    }

    /**
     * Returns true if the given vertices are in the same component.
     * 
     * @param v one vertex
     * @param w the other vertex
     * @return <code>true</code> if the vertices are in the same component,
     *         <code>false</code> otherwise.
     * @throws IllegalArgumentException if one of the vertices is out of bounds.
     */
    public boolean connected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id(v) == id(w);
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("The vertex " + v + " is not in valid range!");
    }

    private int maxComponentId() {
        if (count == 0)
            throw new NoSuchElementException("No components.");
        int maxCompId = 0;
        for (int i = 1; i < count; i++) {
            if (this.sizeOfComp(i - 1) < this.sizeOfComp(i))
                maxCompId = i;
        }
        return maxCompId;
    }

    /**
     * Searches for one vertex in the largest component by iterating through the
     * vertices until finding one vertex belonging to the largest component.
     * 
     * @return the first vertex found.
     * @throws NoSuchElementException if no vertex was found.
     */
    public int getVertexInMaxComp() {
        int maxCompId = maxComponentId();
        for (int v = 0; v < graphSize; v++)
            if (this.id(v) == maxCompId)
                return v;
        throw new NoSuchElementException("No vertex in the graph could be find belonging to the largest component.");
    }

    /**
     * Simple unit test that finds the largest component in a graph with all of its
     * edges.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String filename = args[0];
        String delimiter = args[1];
        SymbolWeightedGraph symbolWeightedGraph = new SymbolWeightedGraph(filename, delimiter);
        EdgeWeightedGraph graph = symbolWeightedGraph.graph();
        ConnectedComponents cc = new ConnectedComponents(graph);

        int compCount = cc.count();
        System.out.println("The graph has " + compCount + " components:");
        // Set all vertices in each connected components to lists.
        @SuppressWarnings("unchecked")
        GeneralizedQueueArray<Integer>[] components = (GeneralizedQueueArray<Integer>[]) new GeneralizedQueueArray[compCount];
        for (int i = 0; i < compCount; i++)
            components[i] = new GeneralizedQueueArray<Integer>();

        for (int v = 0; v < graph.V(); v++)
            components[cc.id(v)].insert(v);

        System.out.println("Components are: ");
        for (int i = 0; i < compCount; i++) {
            for (int v : components[i]) {
                System.out.print(symbolWeightedGraph.nameOf(v) + " ");
            }
            System.out.println();
        }
    }
}
