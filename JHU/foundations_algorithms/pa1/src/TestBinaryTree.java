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
public class TestBinaryTree {
  //Test operations on single-path tree
  Integer[] numbers = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
  BinaryTree<Integer> intTree = new BinaryTree<Integer>(numbers);

  //Test operations on single-node tree
  Integer[] rootval = {1};
  BinaryTree<Integer> rootTree = new BinaryTree<Integer>(rootval);

  //Test operations on single-node tree
  Integer[] twobody = {2, 1};
  BinaryTree<Integer> twoTree = new BinaryTree<Integer>(twobody);

   @Test
   public void testHeight() {
     assertEquals(intTree.height(), 9);
     assertEquals(rootTree.height(), 0);
     assertEquals(twoTree.height(), 1);
   }

   @Test
   public void testFindLeaves() {
     assertEquals(intTree.leaves(), 1);
     assertEquals(rootTree.leaves(), 1);
     assertEquals(twoTree.height(), 1);
   }

   @Test
   public void testFindNonleaves() {
     assertEquals(intTree.nonleaves(), 9);
     assertEquals(rootTree.nonleaves(), 0);
     assertEquals(twoTree.nonleaves(), 1);
   }

   @Test
   public void testPostorder() {
      System.out.println("Calling postorder;");
      intTree.postorder();
      System.out.println();

      System.out.println("Calling postorder;");
      rootTree.postorder();
      System.out.println();

      System.out.println("Calling postorder;");
      twoTree.postorder();
      System.out.println();
   }

}
