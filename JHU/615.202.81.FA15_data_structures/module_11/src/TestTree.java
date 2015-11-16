/**TestTree
  *
  *@author Justin Ely
  */


import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import java.lang.IndexOutOfBoundsException;

/**Test the Tree object
  *
  * <p>Tests are performed using the JUnit testing framework.
  *    A final output to STDOUT of true, indicates that all tests passed.
  *    Final output of false indicates a failing test.
  * </p>
  */
public class TestTree {

    @Test
    public void testTreeEmptyCreation() {
      Tree myTree = new Tree();
    }

    @Test
    public void testTreeValueCreation() {
      Tree myTree = new Tree("S", 1);
    }

    @Test
    public void testTreeChildCreation() {
      Tree myTree = new Tree("A", 1, new Tree("B", 2), new Tree("C", 3));
    }

    @Test
    public void testInOrderTraversal() {
      Tree myTree = new Tree("A", 1, new Tree("B", 2), new Tree("C", 3));
      myTree.inOrderTraverse();
    }

    @Test
    public void testPreeOrderTraversal() {
      Tree myTree = new Tree("A", 1, new Tree("B", 2), new Tree("C", 3));
      myTree.preOrderTraverse();
    }

}
