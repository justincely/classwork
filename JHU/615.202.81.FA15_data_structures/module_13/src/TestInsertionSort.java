/**TestTree
  *
  *@author Justin Ely
  */


import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertArrayEquals;
import java.lang.IndexOutOfBoundsException;

/**Test the Tree object
  *
  * <p>Tests are performed using the JUnit testing framework.
  *    A final output to STDOUT of true, indicates that all tests passed.
  *    Final output of false indicates a failing test.
  * </p>
  */
public class TestInsertionSort {
    int[] refData = {1, 2, 3, 4, 5};

    @Test
    public void testSortOrder() {
      int[] inData = {1, 2, 3, 4, 5};
      InsertionSort.sort(inData);
      assertArrayEquals(inData, refData);
    }

    @Test
    public void testReverseOrder() {
      int[] inData = {5, 4, 3, 2, 1};
      InsertionSort.sort(inData);
      assertArrayEquals(inData, refData);
    }

    @Test
    public void testNoOrder() {
      int[] inData = {3, 2, 5, 1, 4};
      InsertionSort.sort(inData);
      assertArrayEquals(inData, refData);
    }

    @Test
    public void testUniq() {
      int[] inData = {1, 3, 1, 2, 2};
      int[] refData = {1, 1, 2, 2, 3};
      InsertionSort.sort(inData);
      assertArrayEquals(inData, refData);
    }

}
