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
public class TestQuickSortMed {
    int[] refData = {1, 2, 3, 4, 5};

    @Test
    public void testSortOrder() {
      int[] inData = {1, 2, 3, 4, 5};
      QuickSortMed.sort(inData);
      assertArrayEquals(inData, refData);
    }

    @Test
    public void testReverseOrder() {
      int[] inData = {5, 4, 3, 2, 1};
      QuickSortMed.sort(inData);
      assertArrayEquals(inData, refData);
    }

    @Test
    public void testNoOrder() {
      int[] inData = {2, 5, 4, 3, 1};
      QuickSortMed.sort(inData);
      assertArrayEquals(inData, refData);
    }

    @Test
    public void testUniq() {
      int[] refData = {1, 1, 2, 2, 3};
      int[] inData = {1, 2, 1, 3, 2};
      QuickSortMed.sort(inData);
      assertArrayEquals(inData, refData);
    }

    @Test
    public void testWithK() {
      int[] inData = {10, 9, 1, 2, 4, 3, 7, 6, 8, 5};
      int[] refData = {1, 2, 3, 4, 5, 6, 7 , 8, 9, 10};
      QuickSortMed.sort(inData, 3);
      assertArrayEquals(inData, refData);
    }

}
