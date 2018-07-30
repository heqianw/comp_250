//package hashMap;
//He Qian Wang
//260688073

public class HashLinkedList<K,V>{
	/*
	 * Fields
	 */
	private HashNode<K,V> head;

	private Integer size;

	/*
	 * Constructor
	 */

	HashLinkedList(){
		head = null;
		size = 0;
	}


	/*
	 *Add (Hash)node at the front of the linked list
	 */

	public void add(K key, V value){
		// ADD CODE BELOW HERE
		// since this is a linkedList, save the previous node's address and then create new node
		// set the next of new node to the previous node
		
		HashNode<K,V> previousHead=head;
		
		head= new HashNode<K,V>(key, value);
		head.next=previousHead;
		
		//increment the size too
		size++;
		// ADD CODE ABOVE HERE
	}

	/*
	 * Get Hash(node) by key
	 * returns the node with key
	 */

	public HashNode<K,V> getListNode(K key){
		// ADD CODE BELOW HERE
		if (size==0){
			return null;
		}
		
		HashNode <K,V> currentNode=head;
		
		for(int i=0; i<size;i++){
			if (key.equals(currentNode.getKey())){
				return currentNode;
			}
			currentNode=currentNode.getNext();
		}
		
		return null;
		// ADD CODE ABOVE HERE
	}


	/*
	 * Remove the head node of the list
	 * Note: Used by remove method and next method of hash table Iterator
	 */

	public HashNode<K,V> removeFirst(){
		// ADD CODE BELOW HERE
		if(size==0){
			return null;
		}
		HashNode<K,V> previousHead=head;
		HashNode<K,V> nextHead=head.getNext();
		head=nextHead;
		size--;
		// ADD CODE ABOVE HERE
		return previousHead; 
	}

	/*
	 * Remove Node by key from linked list 
	 */

	public HashNode<K,V> remove(K key){
		// ADD CODE BELOW HERE
		if(size==0){
			return null;
		}
		//check first case
		if (key.equals(head.getKey())){
			return removeFirst();
		}
		
		HashNode<K,V> previousNode=head;
		HashNode<K,V> currentNode=head.getNext();
		//start from 1 since we already checked the first case, avoid nullpointer
		for(int i=1; i<size;i++){
			if(key.equals(currentNode.getKey())){
				previousNode.next=currentNode.getNext();
				size--;
				return currentNode;
			}
			previousNode=currentNode;
			currentNode=currentNode.getNext();
		}
		// ADD CODE ABOVE HERE
		return null; // removing failed
	}



	/*
	 * Delete the whole linked list
	 */
	public void clear(){
		head = null;
		size = 0;
	}
	/*
	 * Check if the list is empty
	 */

	boolean isEmpty(){
		return size == 0? true:false;
	}

	int size(){
		return this.size;
	}

	//ADD YOUR HELPER  METHODS BELOW THIS
	public HashNode<K,V> head(){
		return head;
	}

	//ADD YOUR HELPER METHODS ABOVE THIS


}
