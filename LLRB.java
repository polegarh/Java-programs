//Oleg Puchkov
import java.util.*;

public class LLRB {
    private static final boolean RED   = true;
    private static final boolean BLACK = false;
    
    public Node root;
	
	public class Node {
		public int key;
		public boolean color;
		public Node left, right;
		private int size;
		
		public Node(int key, boolean color) {
			this.key = key;
			this.color = color;
			this.size = size;
		}
	}
	public int size() {
        return size(root);
    }
	private int size(Node x){
		if (x == null) return 0;
        return x.size;
	}
	// Constructor for LLRB
	public LLRB() {
	}
	
	// Is parent link for node x red? false if x is null
	private boolean isRed(Node x) {
		if (x == null) return BLACK;
		return x.color == RED;
	}
	
	// Inserts a key without fixing the tree (this is a purposefully erroneous insertion method)
	public void bstInsert(int key) {
		root = bstInsert(root, key);
	}
	
	// Recursive helper method for bstInsert (erroneous)
	private Node bstInsert(Node x, int key) {
		if (x == null) return new Node(key, RED);
		if (key < x.key) x.left  = bstInsert(x.left, key);
		else if (key > x.key) x.right = bstInsert(x.right, key);
		return x;
	}
	
	// Inserts a key fixing the red-black tree property
	// Another way to do insert is described in the book: pg 453.
	public void insert(int key) {
		root = insert(root, key);
	}
  
	private Node insert(Node n, int key){
		if (n == null) return new Node(key, RED);
		if (key < n.key) n.left  = insert(n.left, key);
		else if (key > n.key) n.right = insert(n.right, key);
		if (isRed(n.right) && !isRed(n.left))    n = rotateLeft(n);
		if (isRed(n.left) && isRed(n.left.left)) n = rotateRight(n);
		if (isRed(n.left) && isRed(n.right))     flipColors(n);
		n.size = size(n.left) + size(n.right) + 1;
		return n;
	}
	
	// Checks whether the tree contains a red right edge
	public boolean containsRightRedEdge() {
		return containsRightRedEdge(root);
	}
  
	private boolean containsRightRedEdge(Node n){
		if (n == null) return false;
        if (n.right!=null && isRed(n.right)) return true;
        if (n.left !=null && n.right!=null) return  containsRightRedEdge(n.left) ||  containsRightRedEdge(n.right);
        if (n.left==null && n.right==null) return  false; 
        if (n.left==null && n.right!=null) return  containsRightRedEdge(n.right);
        else  return containsRightRedEdge(n.left); 
	}
	
	// Checks whether the tree contains two left red edges in a row
	public boolean containsConsecutiveLeftRedEdges() {
		return containsConsecutiveLeftRedEdges(root);
	}
  
	private boolean containsConsecutiveLeftRedEdges(Node n){
		if (n == null) return false;
		if (n.left != null && isRed(n) && isRed(n.left)) return true;
		return containsConsecutiveLeftRedEdges(n.left) || containsConsecutiveLeftRedEdges(n.right);
	}
	
	// Returns the maximum number of red edges (nodes) on any path from root to null
	public int maxRedEdgesDepth() {
		if (root==null) return 0;
		return maxRedEdgesDepth(root, 0);
	}
  
	private int maxRedEdgesDepth(Node n, int curMax){
		if (n != null && isRed(n)) curMax++;
		if (n == null) return curMax;
		return Math.max(maxRedEdgesDepth(n.left, curMax), maxRedEdgesDepth(n.right, curMax));
	}
  
	// Returns the minimum number of black edges (nodes) on any path from root to null
	public int minBlackEdgesDepth() {
		if (root==null) return 0;
		return minBlackEdgesDepth(root, 0);
	}
  
	private int minBlackEdgesDepth(Node n, int curMin) {
		if (n != null && !isRed(n)) curMin++;
		if (n == null) return curMin;
		return Math.min(minBlackEdgesDepth(n.left, curMin), minBlackEdgesDepth(n.right, curMin));
	}
  
	// Fixes a balanced red-black tree so that it becomes a left leaning red black tree.
	// The tree should already be balanced (wrt black nodes) before making a call to this method.
	public void fixLLRB() {
		fixLLRB(root);
		// TODO : complete this method
	}
  
	private void fixLLRB(Node n){
			if (n==null) return;
			if (isRed(n.right) && !isRed(n.left)) rotateLeft(n);
			if (isRed(n.left) && isRed(n.left.left)) rotateRight(n);
			if (isRed(n.left) && isRed(n.right)) flipColors(n);

			if (n.left != null) fixLLRB(n.left);
			if (n.right != null) fixLLRB(n.right);

		}
	
	private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // make a right-leaning link lean to the left
    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // flip the colors of a node and its two children
    private void flipColors(Node h) {
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }
}
