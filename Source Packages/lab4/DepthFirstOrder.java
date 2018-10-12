package lab4;

import lab1.GeneralizedQueueArray;

/**
 * Finds paths using the depth-first search algorithm in a directed path and
 * also stores the orders in a preorder, postorder and reversed postorder.
 * 
 * @author Antonio
 *
 */
public class DepthFirstOrder {
    private boolean[] marked; // marked[v], true if vertex v has been visited
    private GeneralizedQueueArray<Integer> pre; // vertices in preorder (before recursive calls)
    private GeneralizedQueueArray<Integer> post; // vertices in postorder (after recursive calls)
    private Stack<Integer> reversePost; // vertices in reverse postorder

    /**
     * Determines the depth first order of the given digraph.
     * 
     * @param digraph the digraph.
     */
    public DepthFirstOrder(Digraph digraph) {
        pre = new GeneralizedQueueArray<Integer>();
        post = new GeneralizedQueueArray<Integer>();
        reversePost = new Stack<Integer>();
        marked = new boolean[digraph.V()];
        for (int v = 0; v < digraph.V(); v++)
            if (!marked[v])
                dfs(digraph, v);
    }

    // DFS but also save the different types of orders
    private void dfs(Digraph G, int v) {
        pre.insert(v);
        marked[v] = true;
        for (int w : G.adj(v))
            if (!marked[w])
                dfs(G, w);
        post.insert(v);
        reversePost.push(v);
    }

    /**
     * Returns the vertices in preorder.
     * 
     * @return the vertices in preorder as an iterable.
     */
    public Iterable<Integer> pre() {
        return pre;
    }

    /**
     * Returns the vertices in postorder.
     * 
     * @return the vertices in postorder as an iterable.
     */
    public Iterable<Integer> post() {
        return post;
    }

    /**
     * Returns the vertices in a reversed postorder.
     * 
     * @return the vertices in reversed postorder as an iterable.
     */
    public Iterable<Integer> reversePost() {
        return reversePost;
    }

    /**
     * Simple unit test.
     * 
     * @param args Not used here.
     */
    public static void main(String[] args) {
        Digraph digraph = new Digraph(5);
        digraph.addEdge(0, 1);
        digraph.addEdge(1, 2);
        digraph.addEdge(2, 3);
        digraph.addEdge(3, 4);
        DepthFirstOrder dfo = new DepthFirstOrder(digraph);
        for (int i : dfo.pre())
            System.out.print(i + " ");
        System.out.println();
        for (int i : dfo.post())
            System.out.print(i + " ");
        System.out.println();
        for (int i : dfo.reversePost())
            System.out.print(i + " ");
        System.out.println();
    }
}
