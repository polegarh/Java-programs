package hw5;
//Oleg Puchkov
import java.util.LinkedList;

public class Graph {
	private int V;                         // number of vertices
	private int E;                         // number of edges
	private boolean[] marked;
	private boolean hasCycle;
	private boolean[] color;
	private boolean isTwoColorable;
	private LinkedList<Integer>[] adjlist; // adjecency lists

	// creates a graph with V vertices and 0 edges 
	@SuppressWarnings("unchecked")
	public Graph(int V) {
		this.V = V;
		adjlist = (LinkedList<Integer>[])new LinkedList[V];
		for (int v = 0; v < V; v++) {
			adjlist[v] = new LinkedList<Integer>();
		}
	}
	
	// returns the number of vertices in this graph
	public int V() {
		return V;
	}
	
	// returns the number of edges in this graph
	public int E() {
		return E;
	}
	
	// adds an edge to this graph
	public void addEdge(int v, int w) {
		adjlist[v].add(w);
		adjlist[w].add(v);
		E++;
	}
	
	// returns the list of vertices adjacency to v
	public Iterable<Integer> adj(int v) {
		return adjlist[v];
	}
	
	// does this graph contain the edge (v,w)?
	public boolean containsEdge(int v, int w) {
		return adjlist[v].contains(w);
	}
	
	// adds a new vertex into the graph without any edges
	public void addVertex() {
		LinkedList<Integer>[] temp = new LinkedList[V+1];
		for (int i=0; i<V; i++){
			temp[i]=adjlist[i];
		}
		temp[V] = new LinkedList<Integer>();
		adjlist=temp;
		V++;
	}
	
	// returns the adjacency matrix for this graph 
	public boolean[][] adjMatrix() {
		boolean[][] result = new boolean [V][V];
        for(int i=0; i<V; i++) {
            for (int j=0; j<V; j++) result[i][j] = false;
            for (int j = 0; j < adjlist[i].size(); j++)
                result[i][(Integer) adjlist[i].get(j)] = true;
        }
        return result;
	}
	
	// returns true if and only if the graph contains a three cycle (triangle)
	public boolean containsTriangle() {
		boolean[][] adj = adjMatrix();
        for (int i = 1; i<V; i++){
            for (int j= i+1; j<V; j++){
                if (adj[i][j]==true) {
                    for (int k=0; k<V; k++){
                        if (adj[k][i] && adj[k][j]) return true;
                    }
                }
            }
        }
        
		return false;
	}
	
	// returns true if and only if the graph contains a cycle (of arbitrary length)
	public boolean containsCycle() {
		if (V==0) return false;
        marked = new boolean[V];
        for (int i=0; i<V; i++) marked[i] = false;
        hasCycle = false;
        for (int s = 0; s < V(); s++){
        	if (!marked[s])
                dfs(s, s);
        }
        return hasCycle;
	}
	
	private void dfs(int i, int h){
		marked[i] = true;
		for (int w : adjlist[i]){
		    if (!marked[w]){
		          dfs(w, i);
			} else if (w != h) hasCycle = true;
		}	
	}
	
	// returns true if and only if the graph is bipartite
	// equivalently, returns true if and only if the graph contains an odd-length cycle
	public boolean isBipartite() {
		isTwoColorable = true;
		marked = new boolean[V()];
        color = new boolean[V()];
        for (int s = 0; s < V(); s++)
           if (!marked[s])
               dfs1(s);
        return isTwoColorable;
	}
	private void dfs1(int v){
    marked[v] = true;
    for (int w : adjlist[v])
        if (!marked[w]){
        	color[w] = !color[v];
        	dfs1(w); 
        }else if (color[w] == color[v]) isTwoColorable = false;
	}
}
