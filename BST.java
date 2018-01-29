//Oleg Puchkov
import java.util*;

public class BST<Key extends Comparable<Key>, Value> {
	private Node root;             // root of BST

	private class Node {
		private Key key;           // sorted by key
		private Value val;         // associated data
		private Node left, right;  // left and right subtrees
		private int size;
		
		public Node(Key key, Value val) {
			this.key = key;
			this.val = val;
			this.size = size;
		}
	
		//Appends the preorder string representation of the sub-tree to the given StringBuilder.
		public void buildString(StringBuilder s) {
			s.append(left == null ? '[' : '(');
			s.append(key + "," + val);
			s.append(right == null ? ']' : ')');
			if (left != null) left.buildString(s);
			if (right != null) right.buildString(s);
		}
	}
	
	//Initializes an empty symbol table.
	public BST() {
	}
	
	
	//Returns the value associated with the given key.
	public Value get(Key key) {
		return get(root, key);
	}
	
	private Value get(Node x, Key key) {
		if (x == null) return null;
		int cmp = key.compareTo(x.key);
		if      (cmp < 0) return get(x.left, key);
		else if (cmp > 0) return get(x.right, key);
		else              return x.val;
	}
	
	//Inserts the specified key-value pair into the symbol table, overwriting the old 
	//value with the new value if the symbol table already contains the specified key.
  	public void put(Key key, Value val) {
		if (key == null) throw new NullPointerException("first argument to put() is null");
		root = put(root, key, val);
	}
	
	private Node put(Node x, Key key, Value val) {
		if (x == null) return new Node(key, val);
		int cmp = key.compareTo(x.key);
		if      (cmp < 0) x.left  = put(x.left,  key, val);
		else if (cmp > 0) x.right = put(x.right, key, val);
		else              x.val   = val;
		x.size = 1 + size(x.left) + size(x.right);
		return x;
	}
	
	//Returns the preorder string representation of the BST
	public String toString() {
		StringBuilder s = new StringBuilder();
		root.buildString(s);
		return s.toString();
	}
  
	public int size() {
        	return size(root);
    	}
    
	private int size(Node x) {
        	if (x == null) return 0;
        	else return x.size;
    	}
    
	//Returns the number of null nodes in this symbol table.
	public int numNull() {
		return numNull(root);
	}
  
	private int numNull(Node n) {
		if (n==null) return 1;
		return numNull(n.left) + numNull(n.right);
	}
	
	//Returns the length of the shortest path from root to a null node.
	public int lenShortestPathToNull() {
		if (root==null) return 0;
		int c = 0;
		return lenShortestPathToNull(root,c);
	}
  
	private int lenShortestPathToNull(Node n, int count){
		if (n != null) count++;
		if (n == null) return count;
		return Math.min(lenShortestPathToNull(n.left, count), lenShortestPathToNull(n.right, count));
	}
	
	//median returns the median of all keys in the symbol table.
	public Key median() {
		if (root == null) return null;
		int count = 0;
		if (root.left != null && root.right != null){
			count = root.left.size + root.right.size + 1;
		} else {
			if (root.left == null && root.right!=null) count = root.right.size+1;
			else if (root.right == null && root.left!=null) count = root.left.size+1;
			else count = 1;
		}
		count = Math.floorDiv(count, 2);
		Node x = median(root, count);
        	return x.key;
	}
  
	private Node median(Node x, int k) {
        	if (x == null) return null; 
        	int t = size(x.left); 
        	if      (t > k) return median(x.left,  k); 
        	else if (t < k) return median(x.right, k-t-1); 
        	else            return x; 
    	} 
	
  	/*
	 * closest returns a key in the symbol table that is equal to
	 * or otherwise that is the closest on either side of the given key.
	 * it returns null if there is no such key (empty symbol table).
	 */
	public Key closest(Key key) {
		if (ceiling(key) == null){
			return floor(key);
		}
		if (floor(key) == null) {
			return ceiling(key); 
		}
		return (ceiling(key));
	}
  
	/*
	 * Merges the given symbol table into this symbol table.
	 * If the given symbol table contains keys that is already in this symbol table
	 * merge overwrites those keys' values with the values in the given symbol table.
	 */
	public void merge(BST<Key, Value> bst) {
		if (bst == null) return;
		merge(bst.root);
	}
  
	private void merge(Node k) {
		if (k == null) return;
		if (k.key != null) put(k.key, k.val);
		if (k.left != null)  merge(k.left);
		if (k.right != null) merge(k.right);
	}
  
	public Key secondMax(){
		return floor(max());
	}

	public Key ceiling(Key key) {
    		if (key == null) throw new NullPointerException("argument to ceiling() is null");
    		if (root == null) return null;
    		Node x = ceiling(root, key);
    		if (x == null) return null;
    		else return x.key;
  	}

  	private Node ceiling(Node x, Key key) {
    		if (x == null) return null;
    		int cmp = key.compareTo(x.key);
    		if (cmp == 0) return x;
    		if (cmp < 0) { 
      			Node t = ceiling(x.left, key); 
      			if (t != null) return t;
      			else return x; 
    		} 
    		return ceiling(x.right, key); 
  		} 
  
 	public Key floor(Key key) {
    		if (key == null) throw new NullPointerException("argument to floor() is null");
    		if (root == null) return null;
    		Node x = floor(root, key);
    		if (x == null) return null;
    		else return x.key;
  	} 

	private Node floor(Node x, Key key) {
		if (x == null) return null;
	    	int cmp = key.compareTo(x.key);
		if (cmp == 0) return x;
		if (cmp <  0) return floor(x.left, key);
		Node t = floor(x.right, key); 
		if (t != null) return t;
		else return x; 
	} 
  
  	public Key max() {
    		if (root == null) return null;
    		return max(root).key;
  	} 

  	private Node max(Node x) {
    		if (x.right == null) return x; 
    		else                 return max(x.right); 
  	} 
}
