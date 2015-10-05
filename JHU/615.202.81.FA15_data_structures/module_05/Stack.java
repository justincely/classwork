import java.util.EmptyStackException;
import java.nio.BufferOverflowException;

/** Stack
  * The stack has a maximum number of elements of 50, all of String Type.
  *
  * @author Justin Ely
  * @version 0.0.1
  * @license BSD 3-clause
  */
public class Stack implements StackADT{
  private final int MAX_SIZE = 50;
  private int top = -1;
  private String[] data = new String[MAX_SIZE];


  /**Check if the stack is empty
    *
    * @return Boolean - try if stack is empty, false otherwise
    */
  public boolean isEmpty(){
    return (top == -1) ? true : false;
  }


  /**peek at the top of the stack
    *
    *@return String - value at the top of stack
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
    *@param char value - item to add
    *@throws BufferOverflowException - if maximum stack size is exceeded
    */
  public void push(String value) throws BufferOverflowException {
    if (top == MAX_SIZE - 1){
      throw new BufferOverflowException();
    }

    top++;
    data[top] = value;
  }

  /**View the current size of the stack
    *
    *@return int - the current size of the stack
    */
  public int size() {
    return top + 1;
  }

}
