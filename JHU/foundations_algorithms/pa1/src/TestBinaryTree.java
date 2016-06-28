/** TestBinaryTree
 *
 *@author Justin Ely
 */

 import org.junit.Test;
 import static org.junit.Assert.assertEquals;

/**Test the BinaryTree object
 *
 * <p>Tests are performed using the JUnit testing framework.
 *    A final output to STDOUT of true, indicates that all tests passed.
 *    Final output of false indicates a failing test.
 * </p>
 */
public class TestBinaryTree {
  Integer[] numbers = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
  BinaryTree<Integer> intTree = new BinaryTree<Integer>(numbers);

   @Test
   public void testHeight() {
     assertEquals(intTree.height(), 9);
   }

   @Test
   public void testFindLeaves() {
     assertEquals(intTree.leaves(), 1);
   }

   @Test
   public void testFindNonleaves() {
     assertEquals(intTree.nonleaves(), 9);
   }

   @Test
   public void testPostorder() {
      intTree.postorder();
   }

}
