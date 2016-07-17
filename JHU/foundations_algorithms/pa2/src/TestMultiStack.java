/** TestBinaryTree
 *
 *@author Justin Ely
 */

 import org.junit.Test;
 import static org.junit.Assert.assertEquals;
 import static org.junit.Assert.assertArrayEquals;

/**Test the BinaryTree object
 *
 * <p>Tests are performed using the JUnit testing framework.
 *    A final output to STDOUT of true, indicates that all tests passed.
 *    Final output of false indicates a failing test.
 * </p>
 */
public class TestMultiStack {

  @Test
  public void testPushA() {
    MultiStack stacks = new MultiStack();
    stacks.PushA(5);
    stacks.PeekA();
  }

  @Test
  public void TestMultiPopA() {
    MultiStack stacks = new MultiStack();
    stacks.PushA(5);
    stacks.PushA(7);
    stacks.PushA(9);
    stacks.MultiPopA(5);
  }

  @Test
  public void TestTransfer() {
    System.out.println("#-- Testing Transfer function --#");

    MultiStack stacks = new MultiStack();
    for (int i=0; i<10; i++) {
      stacks.PushA(i);
    }

    stacks.Transfer(5);
    System.out.print("Last value pushed to B: ");
    stacks.PeekB();
  }

}
