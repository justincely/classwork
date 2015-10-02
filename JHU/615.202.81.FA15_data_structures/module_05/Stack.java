import java.util.EmptyStackException;

public class Stack implements StackADT{

  private final int MAX_SIZE = 50;
  private int top = -1;
  private char[] data = new char[MAX_SIZE];

  public boolean isEmpty(){
    if (top == -1){
      return true;
    } else {
      return false;
    }
  }

  public void push(char value){
    //if (top == MAX_SIZE)
    //  throw new Exception();

    top++;
    data[top] = value;
  }

  public char pop(){
    //if (top == -1)
    //  throw new EmptyStackException();

    top--;
    return data[top+1];
  }

}
