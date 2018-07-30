//package hashMap;
//He Qian Wang
//260688073
import java.util.ArrayList;
import java.util.Iterator;


class MyHashTable<K,V> {
	/*
	 *   Number of entries in the HashTable. 
	 */
	private int entryCount = 0;

	/*
	 * Number of buckets. The constructor sets this variable to its initial value,
	 * which eventually can get changed by invoking the rehash() method.
	 */
	private int numBuckets;

	/*
	 * Threshold load factor for rehashing.
	 */
	private final double MAX_LOAD_FACTOR=0.75;

	/*
	 *  Buckets to store lists of key-value pairs.
	 *  Traditionally an array is used for the buckets and
	 *  a linked list is used for the entries within each bucket.   
	 *  We use an Arraylist rather than an array, since the former is simpler to use in Java.   
	 */

	ArrayList< HashLinkedList<K,V> >  buckets;

	/* 
	 * Constructor.
	 * 
	 * numBuckets is the initial number of buckets used by this hash table
	 */

	MyHashTable(int numBuckets) {

		//  ADD YOUR CODE BELOW HERE
		
		//set the buckets to numBuckets
		this.numBuckets=numBuckets;
		
		//we have to create an arraylist to hold the buckets
		this.buckets=new ArrayList<HashLinkedList<K,V>>(numBuckets);
		
		//we have populate the arraylist and declare the HashLinkedList inside the arraylist
		for(int i=0; i<numBuckets; i++){
			this.buckets.add(new HashLinkedList<K,V>());
		}
		//  ADD YOUR CODE ABOVE HERE

	}

	/**
	 * Given a key, return the bucket position for the key. 
	 */
	private int hashFunction(K key) {

		return  Math.abs( key.hashCode() ) % numBuckets ;
	}

	/**
	 * Checking if the hash table is empty.  
	 */

	public boolean isEmpty()
	{
		if (entryCount == 0)
			return true;
		else
			return(false);
	}

	/**
	 *   return the number of entries in the hash table.
	 */

	public int size()
	{
		return(entryCount);
	}

	/**
	 * Adds a key-value pair to the hash table. If the load factor goes above the 
	 * MAX_LOAD_FACTOR, then call the rehash() method after inserting. 
	 * 
	 *  If there was a previous value for the given key in this hashtable,
	 *  then overwrite it with new value and return the old value.
	 *  Otherwise return null.   
	 */

	public  V  put(K key, V value) {

		//  ADD YOUR CODE BELOW HERE
		int valueToSet=hashFunction(key);
		
		//if the value exists already at the given key
		
		if(containsKey(key)){
			//save the past value
			
			V previousValue=this.buckets.get(valueToSet).getListNode(key).getValue();
			//set the new value
			
			this.buckets.get(valueToSet).getListNode(key).newValue(value);
			return previousValue;
		}
		
		//create the linkedList
		if(this.buckets.get(valueToSet)==null){
			buckets.set(valueToSet, new HashLinkedList<K,V>());
		}
		
		//increment the number of entries after
		this.entryCount++;
		
		//add the node
		buckets.get(valueToSet).add(key, value);
		double numerator=entryCount;
		double denominator=numBuckets;
		if((numerator/denominator) > MAX_LOAD_FACTOR){
			rehash();
		}
		
		//  ADD YOUR CODE ABOVE HERE
		return null;
	}

	/**
	 * Retrieves a value associated with some given key in the hash table.
     Returns null if the key could not be found in the hash table)
	 */
	public V get(K key) {

		//  ADD YOUR CODE BELOW HERE
		//check if we have the key in our hashtable
		if(containsKey(key)){
			return this.buckets.get(hashFunction(key)).getListNode(key).getValue();
		}

		//  ADD YOUR CODE ABOVE HERE

		return null;
	}

	/**
	 * Removes a key-value pair from the hash table.
	 * Return value associated with the provided key.   If the key is not found, return null.
	 */
	public V remove(K key) {

		//  ADD YOUR CODE BELOW HERE
		if(containsKey(key)){
			//decrement the entries
			entryCount--;
			//we dont need to reshash, since it will not increase load factor
			return this.buckets.get(hashFunction(key)).remove(key).getValue();
		}

		//  ADD  YOUR CODE ABOVE HERE

		return null;
	}

	/*
	 *  This method is used for testing rehash().  Normally one would not provide such a method. 
	 */

	public int getNumBuckets(){
		return numBuckets;
	}

	/*
	 * Returns an iterator for the hash table. 
	 */

	public MyHashTable<K, V>.HashIterator  iterator(){
		return new HashIterator();
	}

	/*
	 * Removes all the entries from the hash table, 
	 * but keeps the number of buckets intact.
	 */
	public void clear()
	{
		for (int ct = 0; ct < buckets.size(); ct++){
			buckets.get(ct).clear();
		}
		entryCount=0;		
	}

	/**
	 *   Create a new hash table that has twice the number of buckets.
	 */


	public void rehash()
	{
		//   ADD YOUR CODE BELOW HERE
		//initialize a new arrayList with twice the number of slots
		
		/*int newNumBuckets=2*this.numBuckets;
		this.numBuckets=newNumBuckets;*/
		
		 ArrayList<HashLinkedList<K,V>> rehashBuckets = new ArrayList<HashLinkedList<K,V>> (numBuckets*2);
		 
		 //populate the new buckets with linkedlists
		 for (int i=0;i<numBuckets*2;i++){
			 rehashBuckets.add(new HashLinkedList<K,V>());
		 }
		 
		 //use iterator for all entries
		 HashIterator previousNodes=iterator();
		 //reset properties
		
		 buckets=rehashBuckets;
		 this.entryCount=0;
		 numBuckets=numBuckets*2;

		 //until there is no more nodes after, add them
		 while(previousNodes.hasNext()){
			 HashNode<K,V> currentNode = previousNodes.next();
	         this.put(currentNode.getKey(), currentNode.getValue());
		 }
		 
		//   ADD YOUR CODE ABOVE HERE

	}


	/*
	 * Checks if the hash table contains the given key.
	 * Return true if the hash table has the specified key, and false otherwise.
	 */

	public boolean containsKey(K key)
	{
		int hashValue = hashFunction(key);
		if(buckets.get(hashValue).getListNode(key) == null){
			return false;
		}
		return true;
	}

	/*
	 * return an ArrayList of the keys in the hashtable
	 */

	public ArrayList<K>  keys()
	{

		ArrayList<K>  listKeys = new ArrayList<K>();

		//   ADD YOUR CODE BELOW HERE
		//iterator to properly parse the keys
		HashIterator keyIterate= iterator();
		//parse until no more
		while(keyIterate.hasNext()){
			//add the keys to the arrayList
			listKeys.add(keyIterate.next().getKey());
		}
		return listKeys;
		
		//   ADD YOUR CODE ABOVE HERE

	}

	/*
	 * return an ArrayList of the values in the hashtable
	 */
	public ArrayList <V> values()
	{
		ArrayList<V>  listValues = new ArrayList<V>();

		//   ADD YOUR CODE BELOW HERE
		//do it the same way as keys()
		HashIterator valueIterate= iterator();
		//parse until no more
		while(valueIterate.hasNext()){
			//add the values to the arrayList
			listValues.add(valueIterate.next().getValue());
		}
		return listValues;
		//   ADD YOUR CODE ABOVE HERE
	}

	@Override
	public String toString() {
		/*
		 * Implemented method. You do not need to modify.
		 */
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buckets.size(); i++) {
			sb.append("Bucket ");
			sb.append(i);
			sb.append(" has ");
			sb.append(buckets.get(i).size());
			sb.append(" entries.\n");
		}
		sb.append("There are ");
		sb.append(entryCount);
		sb.append(" entries in the hash table altogether.");
		return sb.toString();
	}

	/*
	 *    Inner class:   Iterator for the Hash Table.
	 */
	public class HashIterator implements  Iterator<HashNode<K,V> > {
		HashLinkedList<K,V>  allEntries;

		/**
		 * Constructor:   make a linkedlist (HashLinkedList) 'allEntries' of all the entries in the hash table
		 */
		public  HashIterator()
		{

			//  ADD YOUR CODE BELOW HERE
			allEntries=new HashLinkedList<K,V>();
			
			for (int i=0; i<numBuckets; i++){
				HashNode<K,V> followingNode=buckets.get(i).head();
				while(followingNode!=null){
					allEntries.add(followingNode.getKey(), followingNode.getValue());
					followingNode=followingNode.getNext();
				}
			}
			//  ADD YOUR CODE ABOVE HERE

		}

		//  Override
		@Override
		public boolean hasNext()
		{
			return !allEntries.isEmpty();
		}

		//  Override
		@Override
		public HashNode<K,V> next()
		{
			return allEntries.removeFirst();
		}

		@Override
		public void remove() {
			// not implemented,  but must be declared because it is in the Iterator interface

		}		
	}

}
