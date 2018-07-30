//package assignment2_comp_250;

/*  Wang, He Qian
 * 260688073

 */

import java.util.Stack;
import java.util.ArrayList;

public class Expression  {
	private ArrayList<String> tokenList;

	//  Constructor    
	/**
	 * The constructor takes in an expression as a string
	 * and tokenizes it (breaks it up into meaningful units)
	 * These tokens are then stored in an array list 'tokenList'.
	 */

	Expression(String expressionString) throws IllegalArgumentException{
		tokenList = new ArrayList<String>();
		StringBuilder token = new StringBuilder();
		
		//ADD YOUR CODE BELOW HERE
		
		//parse the String, breaks it into tokens, and add them to tokenList
/*		if(expressionString==""){
			this.tokenList.add("");
		}
		else{
			char position=expressionString.charAt(0);
			if(tokenList.size()==0){
				tokenList.add(Character.toString(position));
				//we have one less string now
				expressionString=expressionString.substring(1);				
				Expression following=new Expression(expressionString);
			}
			else
		}
		*/
		for(int i=0; i<expressionString.length();i++){
			char position=expressionString.charAt(i);
			String positionString=Character.toString(position);
			int size=tokenList.size();
			boolean isNumber=Character.isDigit(positionString.charAt(0));
			//if first one, simply add it to list
			if(i==0){
				tokenList.add(positionString);
			}
			else{
				//check if digit
				if(isNumber){
					//is a digit, now check if previous element of tokenList was digit
					if(Character.isDigit(tokenList.get(size-1).charAt(tokenList.get(size-1).length()-1))){
						//is a digit, so we fetch the last element of arrayList and we add our digit to it
						String update=tokenList.get(size-1);
						update=update+positionString;
						tokenList.set(size-1, update);
					}
					//is a digit, but last element was not a digit, then simply add to the list as new element
					else{
						tokenList.add(positionString);
						}
				}
				//checks if we have an increment in order
				else if(position=='+'||position=='-'){
					if(tokenList.get(size-1).charAt(0)=='+'||tokenList.get(size-1).charAt(0)=='-'){
						String updateInc=tokenList.get(size-1);
						updateInc=updateInc+positionString;
						tokenList.set(size-1, updateInc);
					}
					else{
						tokenList.add(positionString);
					}
				}
				//checks for blank space
				else if(position==' '){
					//nothing happens
				}
				//if anything else, add the elment
				else{
					tokenList.add(positionString);
				}
								
			}
		}
		
		//ADD YOUR CODE ABOVE HERE
	}


	/**
	 * This method evaluates the expression and returns the value of the expression
	 * Evaluation is done using 2 stack ADTs, operatorStack to store operators
	 * and valueStack to store values and intermediate results.
	 * - You must fill in code to evaluate an expression using 2 stacks
	 */
	public Integer eval(){
		Stack<String> operatorStack = new Stack<String>();
		Stack<Integer> valueStack = new Stack<Integer>();
		
		//ADD YOUR CODE BELOW HERE
		//parse every value in the tokenList
		int size=tokenList.size();
		
		for (int i=0; i<size;i++){
			
			String positionString=tokenList.get(i);
			
			boolean isNumber=Character.isDigit(positionString.charAt(0));
			//if we get a "(", ignore them
			
			if(tokenList.get(i).charAt(0)=='('){
				
				//nothing happens, we skip to next one
			}
			//if its an operator of +,-,*,/, push on stack
			else if(((positionString.charAt(0)=='+' ||positionString.charAt(0)=='-' ||positionString.charAt(0)=='*' ||positionString.charAt(0)=='/'))&&(positionString.length()==1)){
				operatorStack.push(tokenList.get(i));
				
			}
			//if it is a number
			else if(isNumber){
				//convert string to number in this case and push it on the stack
				int convertedNumber=Integer.parseInt(positionString);
				valueStack.push(convertedNumber);
			}
			//if we get increments
			else if((positionString.length()==2&&positionString.charAt(0)=='+')||(positionString.length()==2&&positionString.charAt(0)=='-')){
				//if since already checked for single operators and numbers, we can assume that we can get away with checking only the
				//the length, if length is 2, we can directly add it. just in case, check if we have + or -
				operatorStack.push(positionString);
			}
			
			//if we get absolute value, we push it on the stack
			else if(positionString.charAt(0)==']'){
				operatorStack.push(positionString);
				
			}
			
			//check if we get a closing parenthesis
			else if(positionString.charAt(0)==')'){
				//we have to peek to see what is the next operator, if it is inc or dec, we only have to pop one value and one operator
				//if next operator is +,-,*,/ then we pop two values and one operator with second pop value being placed first
				//push resulting value on stack7
		
				//if we have increment, we pop 1 value and we pop 1 operator, we push one value after
				if(operatorStack.peek().length()==2 && operatorStack.peek().charAt(0)=='+'){
					int value=valueStack.pop();
					operatorStack.pop();
					++value;
					valueStack.push(value);
				}
				//if we have decrement, we pop 1 value and we pop 1 operator, we push one value after 
				else if(operatorStack.peek().length()==2 && operatorStack.peek().charAt(0)=='-'){
					int value=valueStack.pop();
					operatorStack.pop();
					--value;
					valueStack.push(value);
				}
				//if we have a absolute value, we pop value from stack and we return absolute value of that value
				else if(operatorStack.peek().charAt(0)==']'){
					int value=valueStack.pop();
					//if positive or 0, push it back
					if(value>=0){
						valueStack.push(value);
					}
					//if negative, *-1 and push it back
					else{
						valueStack.push(value*-1);
					}
				}
				
				
				//only remaining operator are +, -, *, / we pop two values, pop 1 operator, push one value after
				//using switch case to check which operator is to use
				//swap value 1/2 order
				else{
					int value1=valueStack.pop();
					int value2=valueStack.pop();
					String operator=operatorStack.pop();
					
					
					/*System.out.println(operator);
					System.out.println(value1);
					System.out.println(value2);*/
					
					if(operator.charAt(0)=='+'){
						valueStack.push(value2+value1);
					}
					else if(operator.charAt(0)=='-'){
						valueStack.push(value2-value1);
					}
					else if(operator.charAt(0)=='*'){
						valueStack.push(value2*value1);
					}
					else if(operator.charAt(0)=='/'){
						valueStack.push(value2/value1);
					}
					/*switch(operator.charAt(0)){
						case '+': valueStack.push(value2+value1);
						System.out.println("ActivePlus");
						break;
						case '-': valueStack.push(value2-value1);
						System.out.println("ActiveMinus");
						break;
						case '*': valueStack.push(value2*value1);
						System.out.println("ActiveMulti");
						break;
						case '/': valueStack.push(value2/value1);
						System.out.println("ActiveDivide");
						break;
					}*/
					
				}
				
			}
			
		}
		//loop is over, pop() values and return it
		
		int valueReturn=valueStack.pop();
		
		
		
		
		
		//ADD YOUR CODE ABOVE HERE

		return valueReturn;   // DELETE THIS LINE
	}

	//Helper methods
	/**
	 * Helper method to test if a string is an integer
	 * Returns true for strings of integers like "456"
	 * and false for string of non-integers like "+"
	 * - DO NOT EDIT THIS METHOD
	 */
	private boolean isInteger(String element){
		try{
			Integer.valueOf(element);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}

	/**
	 * Method to help print out the expression stored as a list in tokenList.
	 * - DO NOT EDIT THIS METHOD    
	 */

	@Override
	public String toString(){	
		String s = new String(); 
		for (String t : tokenList )
			s = s + "~"+  t;
		return s;		
	}

}

