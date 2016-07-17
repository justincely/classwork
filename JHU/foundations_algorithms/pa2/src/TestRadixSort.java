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
public class TestRadixSort {

  @Test
  public void testSortSimple() {
    String[] data = {"COW", "DOG", "SEA", "RUG", "ROW", "MOB", "BOX", "TAB", "BAR", "EAR", "TAR", "DIG", "BIG", "TEA", "NOW", "FOX"};
    try {
      RadixSort.sort(data);
    } catch (Exception error) {
      System.out.println("Exception hit");
      System.out.println(error);
    }
  }

}
