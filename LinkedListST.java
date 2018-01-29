//The LinkedListST class represents an (unordered) symbol table of
//generic key-value pairs.  It supports put, get, and delete methods.
import java.util.*;
	
public class LinkedListST<Key extends Comparable<Key>, Value extends Comparable<Value>> {
    private Node first;      // the linked list of key-value pairs
    private int N;

    // a helper linked list data type
    private class Node {
        private Key key;
        private Value val;
        private Node next;

        public Node(Key key, Value val, Node next)  {
            this.key  = key;
            this.val  = val;
            this.next = next;
        }
    }
    
    //Initializes an empty symbol table.
    public LinkedListST() {
    	
    }

    //Returns the value associated with the given key in this symbol table.
    public Value get(Key key) {
        if (key == null) throw new NullPointerException("argument to get() is null"); 
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key))
                return x.val;
        }
        return null;
    }

    /*
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is null.
     */
    public void put(Key key, Value val) {
        if (key == null) throw new NullPointerException("first argument to put() is null"); 
        if (val == null) {
            delete(key);
            return;
        }

        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.val = val;
                return;
            }
        }
        first = new Node(key, val, first);
        N++;
    }

    //Removes the specified key and its associated value from this symbol table     
    //(if the key is in this symbol table).    
    public void delete(Key key) {
        if (key == null) throw new NullPointerException("argument to delete() is null"); 
        first = delete(first, key);
        N--;
    }

    // delete key in linked list beginning at Node x
    // warning: function call stack too large if table is large
    private Node delete(Node x, Key key) {
        if (x == null) return null;
        if (key.equals(x.key)) {
            return x.next;
        }
        x.next = delete(x.next, key);
        return x;
    }
    
    //size returns the number of key-value pairs in the symbol table.
    //it returns 0 if the symbol table is empty.
    public int size () {
    	return N;
    }

    //secondMaxKey returns the second maximum key in the symbol table.
    //it returns null if the symbol table is empty or if it has only one key.
    public Key secondMaxKey () {
        if (first == null) return null;
        Key firstMax = first.key;
        Key secondMax = null;
        for (Node n = first.next; n != null; n = n.next){
            if (n.key.compareTo(firstMax) > 0)
                {  secondMax = firstMax;
                         firstMax = n.key;
                       }
            else if (secondMax==null )
                secondMax = n.key;
                    else if (  secondMax == n.key )secondMax = n.key;
        
        return secondMax;
        }
        return null;
    }

    //rank returns the number of keys in this symbol table that is less than the given key.
    //your implementation should be recursive.
    public int rank (Key key) {
        int count = 0;
        return rank(first, key, count);
	  }
	
	  public int rank (Node n, Key key, int count) {
	      if (n==null) return count; 
	      if (n.key.compareTo(key) <= 0) return rank(n.next, key, count++);
	      else return rank(n.next, key, count);
	  }

    
    //floor returns the largest key in the symbol table that is less than or equal to the given key.
    //it returns null if there is no such key.
    public Key floor (Key key) {
        Key newMax = null;
    	  for (Node n= first; n != null; n= n.next){
    		  if (n.key.compareTo(key) <= 0){
    			  if (newMax == null || n.key.compareTo(newMax) > 0){
    				  newMax = n.key;
    			  }
    		  }
    	  }
    	  return newMax;
    }

    //inverse returns the inverse of this symbol table.
    //if the symbol table contains duplicate values, you can use any of the keys for the inverse
    public LinkedListST<Value, Key> inverse () {
    	LinkedListST<Value, Key> reversed = new LinkedListST<Value, Key>();    	
    	return reversed;
    }
    
    public Key minKey (){
    	if(first == null) return null;
    	return minKey (first, first.key);
    }
    private Key minKey (Node n, Key currentMin){
    	if (n == null) return currentMin;
    	if (n.key.compareTo(currentMin)<0) return minKey(n.next, n.key);
    	else return minKey(n.next, currentMin);
    }
}
