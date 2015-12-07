/**TestQuickSort
  *
  *@author Justin Ely
  */


import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertArrayEquals;

/**Test the QuickSort algorithm
  *
  * <p>Tests are performed using the JUnit testing framework.
  *    A final output to STDOUT of true, indicates that all tests passed.
  *    Final output of false indicates a failing test.
  * </p>
  */
public class TestQuickSort {
    int[] refData = {1, 2, 3, 4, 5};

    @Test
    public void testSortOrder() {
      int[] inData = {1, 2, 3, 4, 5};
      QuickSort.sort(inData);
      assertArrayEquals(inData, refData);
    }

    @Test
    public void testReverseOrder() {
      int[] inData = {5, 4, 3, 2, 1};
      QuickSort.sort(inData);
      assertArrayEquals(inData, refData);
    }

    @Test
    public void testNoOrder() {
      int[] inData = {2, 5, 4, 3, 1};
      QuickSort.sort(inData);
      assertArrayEquals(inData, refData);
    }

    @Test
    public void testUniq() {
      int[] refData = {1, 1, 2, 2, 3};
      int[] inData = {1, 2, 1, 3, 2};
      QuickSort.sort(inData);
      assertArrayEquals(inData, refData);
    }

}
