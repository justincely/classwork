import java.util.Stack;
import java.util.EmptyStackException;

public class MultiStack<T> {
  private Stack<T> a = new Stack();
  private Stack<T> b = new Stack();

  /** Push onto the A Stack
    */
  public void PushA(T item) {
    a.push(item);
  }

  /** Push onto the B Stack
    */
  public void PushB(T item) {
    b.push(item);
  }

  /** Peek at the top of the A Stack
    */
  public void PeekA() {
    System.out.println(a.peek());
  }

  /** Peek at the top of the B stack
    */
  public void PeekB() {
    System.out.println(b.peek());
  }

  /** Pop multiple from A
    */
  public void MultiPopA(int k) {
    MultiPop(a, k);
  }

  /** Pop multiple from B
    */
  public void MultiPopB(int k) {
    MultiPop(b, k);
  }

  /** perform multiple pops on given stack
    */
  private void MultiPop(Stack currentStack, int k) {
    int endIter = java.lang.Math.min(k, currentStack.size());
    System.out.println("Popping " + endIter + " values");

    for (int i=1; i<=endIter; i++) {
      System.out.println(currentStack.pop());
    }
  }

  /** Transfer items from A onto B
    */
  public void Transfer(int k) {
    for (int i=1; i<=k; i++) {
      if (a.empty() == false) {
        System.out.println("Moving " + a.peek() + " to b");
        b.push(a.pop());
      }
    }
  }

}
