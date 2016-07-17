import java.util.Stack;
import java.util.EmptyStackException;
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
public class MultiStack<T> {
  private Stack<T> a = new Stack();
  private Stack<T> b = new Stack();

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
  public void PushA(T item) {
    a.push(item);
  }

  public void PushB(T item) {
    b.push(item);
  }

  public void PeekA() {
    System.out.println(a.peek());
  }

  public void PeekB() {
    System.out.println(b.peek());
  }

  public void MultiPopA(int k) {
    MultiPop(a, k);
  }

  public void MultiPopB(int k) {
    MultiPop(b, k);
  }

  private void MultiPop(Stack currentStack, int k) {
    int endIter = java.lang.Math.min(k, currentStack.size());
    System.out.println("Popping " + endIter + " values");

    for (int i=1; i<=endIter; i++) {
      System.out.println(currentStack.pop());
    }
  }

  public void Transfer(int k) {
    for (int i=1; i<=k; i++) {
      if (a.empty() == false) {
        System.out.println("Moving " + a.peek() + " to b");
        b.push(a.pop());
      }
    }
  }

}
