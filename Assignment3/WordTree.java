//package assignments2017.a3posted;

//COMP 250 - Introduction to Computer Science - Fall 2017
//Assignment #3 - Question 1
//Wang He Qian 260688073
import java.util.*;

/*
 *  WordTree class.  Each node is associated with a prefix of some word 
 *  stored in the tree.   (Any string is a prefix of itself.)
 */

public class WordTree
{
	WordTreeNode root;

	// Empty tree has just a root node.  All the children are null.

	public WordTree() 
	{
		root = new WordTreeNode();
	}


	/*
	 * Insert word into the tree.  First, find the longest 
	 * prefix of word that is already in the tree (use getPrefixNode() below). 
	 * Then, add TreeNode(s) such that the word is inserted 
	 * according to the specification in PDF.
	 */
	public void insert(String word)
	{
		//  ADD YOUR CODE BELOW HERE
		
		//get that prefix of the word, then we add child to that node
		
		WordTreeNode lastStoredNode=this.getPrefixNode(word);
		String prefixStored=this.getPrefix(word);
		WordTreeNode toBeStoredNode;
		
		for(int i=prefixStored.length(); i<word.length();i++){
			toBeStoredNode=lastStoredNode.createChild(word.charAt(i));
			lastStoredNode=toBeStoredNode;
		}
		//we need to set end of word
		lastStoredNode.setEndOfWord(true);
		
		//  ADD YOUR ABOVE HERE
		
	}

	// insert each word in a given list 

	public void loadWords(ArrayList<String> words)
	{
		for (int i = 0; i < words.size(); i++)
		{
			insert(words.get(i));
		}
		return;
	}

	/*
	 * Given an input word, return the TreeNode corresponding the longest prefix that is found. 
	 * If no prefix is found, return the root. 
	 * In the example in the PDF, running getPrefixNode("any") should return the
	 * dashed node under "n", since "an" is the longest prefix of "any" in the tree. 
	 */
	WordTreeNode getPrefixNode(String word)
	{
		//   ADD YOUR CODE BELOW HERE
		WordTreeNode currentNode=root;
		//loop the nodes and returns a wordtree node until we get to the last node
		for(int i=0; i<word.length();i++){
			//check if it has a child that matches the current character, if yes, then our new node is that one
			if(currentNode.getChild(word.charAt(i))!=null){
				currentNode=currentNode.getChild(word.charAt(i));
			}
			//if there is no more child that has the same character, simply return the last stored node
			else{
				return currentNode;
			}
		}
		//this is case if the whole word is contained in the nodes
		return currentNode;
		
		//   ADD YOUR CODE ABOVE HERE

	}

	/*
	 * Similar to getPrefixNode() but now return the prefix as a String, rather than as a TreeNode.
	 */

	public String getPrefix(String word)
	{
		return getPrefixNode(word).toString();
	}


	/*
	 *  Return true if word is contained in the tree (i.e. it was added by insert), false otherwise.
	 *  Hint:  any string is a prefix of itself, so you can use getPrefixNode().
	 */
	public boolean contains(String word)
	{  
		//   ADD YOUR CODE BELOW HERE
		//have to check the length, since can't go string.equal and if end of word
		boolean comparePrefixWord=((getPrefixNode(word).depth==word.length())&& getPrefixNode(word).isEndOfWord());
		return comparePrefixWord;   
		
		//   ADD YOUR CODE ABOVE HERE
	}

	/*
	 *  Return a list of all words in the tree that have the given prefix. 
	 *  For example,  getListPrefixMatches("") should return all words in the tree.
	 */
	public ArrayList<String> getListPrefixMatches( String prefix )
	{
		//  ADD YOUR CODE BELOW 
		//initialize and arrayList
		ArrayList<String> wordsContained=new ArrayList<String>();
		
		WordTreeNode nodeEndPrefix=getPrefixNode(prefix);
		if (nodeEndPrefix.isEndOfWord()){
			wordsContained.add(nodeEndPrefix.toString());
		}
		//if the length of prefix and the length of length of node are same, we go through all the connecting nodes
		if(prefix.length()==nodeEndPrefix.toString().length()){
			parseTreeNodes(nodeEndPrefix, wordsContained);
		}
		return wordsContained;   //  REMOVE THIS STUB
		
		//  ADD YOUR CODE ABOVE HERE
	}
	//We have to make a recursive method to do tree traversals, we can't do it using stacks since we can't import stack functions
	
	public void parseTreeNodes(WordTreeNode nodePrefix, ArrayList <String> containList){
		
		//save our next node in this variable
		WordTreeNode nodeToCheck;
		//loop 256 times to check every possible ASCII numbers
		for(int i=0; i<256; i++){
			//cast the int to its ASCII char
			char child=(char)i;
			//get the child
			nodeToCheck=nodePrefix.getChild(child);
			//if we actually have a match
			if(nodeToCheck!=null){
				//and its a word, add it
				if(nodeToCheck.isEndOfWord()){
					containList.add(nodeToCheck.toString());
				}
				//if not, then next node to check recursively
				parseTreeNodes(nodeToCheck, containList);
			}
		}
	}

	/*
	 *  Below is the inner class defining a node in a Tree (prefix) tree.  
	 *  A node contains an array of children: one for each possible character.  
	 *  The children themselves are nodes.
	 *  The i-th slot in the array contains a reference to a child node which corresponds 
	 *  to character  (char) i, namely the character with  ASCII (and UNICODE) code value i. 
	 *  Similarly the index of character c is obtained by "casting":   (int) c.
	 *  So children[97] = children[ (int) 'a']  would reference a child node corresponding to 'a' 
	 *  since (char)97 == 'a'   and  (int)'a' == 97.
	 * 
	 *  To learn more:
	 * -For all unicode charactors, see http://unicode.org/charts
	 *  in particular, the ascii characters are listed at http://unicode.org/charts/PDF/U0000.pdf
	 * -For ascii table, see http://www.asciitable.com/
	 * -For basic idea of converting (casting) from one type to another, see 
	 *  any intro to Java book (index "primitive type conversions"), or google
	 *  that phrase.   We will cover casting of reference types when get to the
	 *  Object Oriented Design part of this course.
	 */

	public class WordTreeNode
	{
		/*  
		 *   Highest allowable character index is NUMCHILDREN-1
		 *   (assuming one-byte ASCII i.e. "extended ASCII")
		 *   
		 *   NUMCHILDREN is constant (static and final)
		 *   To access it, write "TreeNode.NUMCHILDREN"
		 *   
		 *   For simplicity,  we have given each WordTree node 256 children. 
		 *   Note that if our words only consisted of characters from {a,...,z,A,...,Z} then
		 *   we would only need 52 children.   The WordTree can represent more general words
		 *   e.g.  it could also represent many special characters often used in passwords.
		 */

		public static final int NUMCHILDREN = 256;

		WordTreeNode     parent;
		WordTreeNode[]   children;
		int              depth;            // 0 for root, 1 for root's children, 2 for their children, etc..
		
		char             charInParent;    // Character associated with the tree edge from this node's parent 
		                                  // to this node.
		// See comment above for relationship between an index in 0 to 255 and a char value.
		
		boolean endOfWord;   // Set to true if prefix associated with this node is also a word.

		
		// Constructor for new, empty node with NUMCHILDREN children.  
		//  All the children are automatically initialized to null. 

		public WordTreeNode()
		{
			children = new WordTreeNode[NUMCHILDREN];
			
			//   These assignments below are unnecessary since they are just the default values.
			
			endOfWord = false;
			depth = 0; 
			charInParent = (char)0; 
		}


		/*
		 *  Add a child to current node.  The child is associated with the character specified by
		 *  the method parameter.  Make sure you set as many fields in the child node as you can.
		 *  
		 *  To implement this method, see the comment above the inner class TreeNode declaration.  
		 *  
		 */
		
		public WordTreeNode createChild(char  c) 
		{	   
			WordTreeNode child       = new WordTreeNode();

			// ADD YOUR CODE BELOW HERE
			
			this.children[c]=child;
			child.parent=this;
			child.depth=this.depth+1;
			child.charInParent=c;
			
			// ADD YOUR CODE ABOVE HERE

			return child;
		}

		// Get the child node associated with a given character, i.e. that character is "on" 
		// the edge from this node to the child.  The child could be null.  

		public WordTreeNode getChild(char c) 
		{
			return children[ c ];
		}

		// Test whether the path from the root to this node is a word in the tree.  
		// Return true if it is, false if it is prefix but not a word.

		public boolean isEndOfWord() 
		{
			return endOfWord;
		}

		// Set to true for the node associated with the last character of an input word

		public void setEndOfWord(boolean endOfWord)
		{
			this.endOfWord = endOfWord;
		}

		/*  
		 *  Return the prefix (as a String) associated with this node.  This prefix
		 *  is defined by descending from the root to this node.  However, you will
		 *  find it is easier to implement by ascending from the node to the root,
		 *  composing the prefix string from its last character to its first.  
		 *
		 *  This overrides the default toString() method.
		 */

		public String toString()
		{
			//using a recursive code, if the node has no more parent, stop. 
			//the character is added at the end to get the proper ordering
			// ADD YOUR CODE BELOW HERE
			if (this.parent==null){
				return "";
			}
			return ""+ this.parent.toString()+this.charInParent;
			// ADD YOUR CODE ABOVE HERE
		}
	}

}