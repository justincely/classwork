

import java.util.EmptyStackException;
import java.nio.BufferOverflowException;

/** A stack of <code>String</code> objects.
  *
  * <p>The stack is implemented as an array
  *     with a maximum size of 50 elements.
  *     This size limitation comes from an
  *     conservative overestimate based on
  *     the given in put situations and the
  *     likely size of postfix expressions:
  *     50 variables in an expression would
  *     be a rather large expression.
  * </p>
  *
  * @author Justin Ely
  * @version 0.0.1
  * @license BSD 3-clause
  */
public class Stack implements StackADT{
  private final int MAX_SIZE = 50;
  private String[] data = new String[MAX_SIZE];
  //top of -1 indicates an empty stack
  private int top = -1;

  /**Check if the stack is empty
    *
    * <p>The contents of the stack are
    *    unchanged by this call. Empty is
    *    determined by checking the position
    *    of the stack pointer with the bottom
    *    of the array.
    * </p>
    *
    * @return <code>true</code> if stack is empty, <code>false</code> otherwise
    */
  public boolean isEmpty(){
    return (top == -1) ? true : false;
  }


  /**peek at the top of the stack
    *
    * <p><code>peek</code> is implimented as a
    *    <code>pop</code> followed by a
    *    <code>push</code>, leaving the final
    *    stack unchanged.
    * </p>
    *
    *@return the item at the top of stack
    *@throws EmptyStackException - if stack is empty
    */
  public String peek() throws EmptyStackException {
    String tmp;

    if (isEmpty()) {
      throw new EmptyStackException();
    }

    tmp = pop();
    push(tmp);

    return tmp;
  }


  /**Pop from the stack
    *
    *@return String - value at the top of stack
    *@throws EmptyStackException - if stack is empty
    */
  public String pop() throws EmptyStackException {
    if (isEmpty()) {
      throw new EmptyStackException();
    }

    top--;
    return data[top+1];
  }


  /**Push a new value on to the stack
    *
    *@param value - item to add
    *@throws BufferOverflowException - if maximum stack size is exceeded
    */
  public void push(String value) throws BufferOverflowException {
    if (top == MAX_SIZE - 1){
      throw new BufferOverflowException();
    }

    top++;
    data[top] = value;
  }

  /**View the number of elements placed onto the stack
    *
    *@return the current size of the stack
    */
  public int size() {
    return top + 1;
  }

}
