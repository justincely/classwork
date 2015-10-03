import java.util.EmptyStackException;
import java.nio.BufferOverflowException;

/**
  * Stack
  *
  * @author justincely
  *
  * @version 0.0.1
  *
  * @license BSD 3-clause
  */
public class Stack implements StackADT{

  private final int MAX_SIZE = 50;
  private int top = -1;
  private char[] data = new char[MAX_SIZE];

  /**Check if the stack is empty
    */
  public boolean isEmpty(){
    return (top == -1) ? true : false;
  }

  /**Push a new value on to the stack
    *@param char value - item to add
    */
  public void push(char value) throws BufferOverflowException {
    if (top == MAX_SIZE){
      throw new BufferOverflowException();
    }

    top++;
    data[top] = value;
  }

  /**Pop from the stack
    */
  public char pop() throws EmptyStackException {
    if (this.isEmpty()) {
      throw new EmptyStackException();
    }

    top--;
    return data[top+1];
  }

}
