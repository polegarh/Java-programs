//Oleg Puchkov
import java.util.LinkedList;

public class Digraph {
	private int V;                         // number of vertices
	private int E;                         // number of edges
	private LinkedList<Integer>[] adjlist; // adjecency lists
	private boolean[] marked;
	private int[] edgeTo;

	// creates a graph with V vertices and 0 edges 
	@SuppressWarnings("unchecked")
	public Digraph(int V) {
		this.V = V;
		adjlist = (LinkedList<Integer>[])new LinkedList[V];
		edgeTo = new int[V];
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
	
	// returns the reverse of this graph which is:
	// the graph on the same vertices with the same edges except that each edge is reversed in direction  
	public Digraph reverse() {
		Digraph d = new Digraph(V);
        for (int i = 0; i<V; i++)
            reverseEdge(i, d);
        return d;
	}
	public void reverseEdge(int v, Digraph d){
		for (int i = 0; i < adjlist[v].size(); i++) {
            d.addEdge((Integer)adjlist[v].get(i),v);
        }
	}
	
	// returns the complement of this graph which is:
	// the graph on the same vertices containing all of the edges that are not in this graph   
	public Digraph complement() {
		Digraph d = new Digraph(V);
        for (int i = 0; i<V; i++)
            ComplementOneVertex(i, d);
        return d;
	}
	public void ComplementOneVertex(int v, Digraph d){
        for (int i = 0; i<V; i++) {
            if (i!=v) 
                if (! adjlist[v].contains((Integer) i))
                    d.addEdge(v, i);
        }
    }
	
	// determines whether the given iterable list of vertices is a topological sort or not
	public boolean isTopological(Iterable<Integer> sort) {
		boolean [] marked = new boolean [V];
        for(int i: sort){
            marked[i] = true;
            for (int j : adjlist[i]) {
                if(marked[j]) return false;
                }
            }
        return true;
	}
	
	// finds and returns a path from v to w, if there exists one, or returns null otherwise
	public Iterable<Integer> pathTo(int v, int w) {
		marked = new boolean[V];
		edgeTo = new int[V];
		dfs(v);
		if (!marked[w]) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = w; x != v; x = edgeTo[x])
            path.push(x);
        path.push(v);
        return path;
	}
	private void dfs(int v) {
        marked[v] = true;
        for (int w : adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(w);
            }
        }
	}
	
	// finds a directed cycle that contains the given two vertices v and w (if there exists one)
	// otherwise (if there are no cycles between v and w), this method returns null 

	public Iterable<Integer> cycleBetween(int v, int w) {
		if(pathTo(v,w)!=null && pathTo(w,v)!=null){
			Stack<Integer> list1 = (Stack<Integer>)pathTo(v,w);
			Stack<Integer> list2 = (Stack<Integer>)pathTo(w,v);
			Stack<Integer> list3 = new Stack<Integer>();
			list1.pop();
			while(list2.size() != 0){
				list3.push(list2.pop());
			}
			while(list3.size() != 0){
				list1.push(list3.pop());
			}
			list1.pop();
			return list1;
		}
        return null;
	}
	
	public Iterable<Integer> topologicalSort() {
		marked = new boolean[V];
		int k=0;
		Stack reversedList = new Stack <Integer>();
        for (int i=0; i<V; i++){
        	if(cycleBetween(k,i)==null){
        		if(!marked[i]){
        			reversedList = topologicalDfs(i,reversedList);
        			k=reversedList.size();
        		}
        	if(cycleBetween(k,i)!=null)return null;
        	}
        }
        return reversedList;
	}
	private Stack topologicalDfs(int v, Stack l){
        marked[v] = true;
        for (int w1 : adjlist[v]){
            if (!marked[w1]){
            	topologicalDfs(w1,l);
            	l.push(w1);
            }
        }
        return l;
    }
    
}
