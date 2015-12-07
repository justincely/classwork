/**TestInsertionSort
  *
  *@author Justin Ely
  */


import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertArrayEquals;

/**Test the InsertionSort algorithm
  *
  * <p>Tests are performed using the JUnit testing framework.
  *    A final output to STDOUT of true, indicates that all tests passed.
  *    Final output of false indicates a failing test.
  * </p>
  */
public class TestHeap {
    int[] refData = {1, 2, 3, 4, 5};

    @Test
    public void testSortOrder() {
      int[] inData = {1, 2, 3, 4, 5};
      Heap testHeap = new Heap(inData);
      assertArrayEquals(inData, refData);
    }

    @Test
    public void testReverseOrder() {
      int[] inData = {5, 4, 3, 2, 1};
      Heap testHeap = new Heap(inData);
      assertArrayEquals(inData, refData);
    }

    @Test
    public void testNoOrder() {
      int[] inData = {2, 5, 4, 3, 1};
      Heap testHeap = new Heap(inData);
      assertArrayEquals(inData, refData);
    }

    @Test
    public void testUniq() {
      int[] refData = {1, 1, 2, 2, 3};
      int[] inData = {1, 2, 1, 3, 2};
      Heap testHeap = new Heap(inData);
      assertArrayEquals(inData, refData);
    }

}
