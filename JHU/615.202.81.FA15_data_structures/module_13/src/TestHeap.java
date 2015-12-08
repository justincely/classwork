/**TestHeap
  *
  *@author Justin Ely
  */


import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertArrayEquals;

/**Test the HeapSort algorithm
  *
  * <p>Tests are performed using the JUnit testing framework.
  *    A final output to STDOUT of true, indicates that all tests passed.
  *    Final output of false indicates a failing test.
  * </p>
  */
public class TestHeap {
    int[] refData = {1, 2, 3, 4, 5};

    /* Iterative method tests */

    @Test
    public void testSortOrder() {
      int[] inData = {1, 2, 3, 4, 5};
      Heap testHeap = new Heap(inData);
      testHeap.heapify();
      testHeap.sort();
      assertArrayEquals(inData, refData);
    }

    @Test
    public void testReverseOrder() {
      int[] inData = {5, 4, 3, 2, 1};
      Heap testHeap = new Heap(inData);
      testHeap.heapify();
      testHeap.sort();
      assertArrayEquals(inData, refData);
    }

    @Test
    public void testNoOrder() {
      int[] inData = {2, 5, 4, 3, 1};
      Heap testHeap = new Heap(inData);
      testHeap.heapify();
      testHeap.sort();
      assertArrayEquals(inData, refData);
    }

    @Test
    public void testUniq() {
      int[] uniqData = {1, 1, 2, 2, 3};
      int[] inData = {1, 2, 1, 3, 2};
      Heap testHeap = new Heap(inData);
      testHeap.heapify();
      testHeap.sort();
      assertArrayEquals(inData, uniqData);
    }


    /* Recursive method tests */

    @Test
    public void testSortOrderR() {
      int[] inData = {1, 2, 3, 4, 5};
      Heap testHeap = new Heap(inData);
      testHeap.heapifyRecursive();
      testHeap.sortRecursive();
      for (int i=0; i<inData.length; i++) {
        System.out.println(inData[i]);
      }
      assertArrayEquals(inData, refData);
    }

    @Test
    public void testReverseOrderR() {
      int[] inData = {5, 4, 3, 2, 1};
      Heap testHeap = new Heap(inData);
      testHeap.heapifyRecursive();
      testHeap.sortRecursive();
      assertArrayEquals(inData, refData);
    }

    @Test
    public void testNoOrderR() {
      int[] inData = {2, 5, 4, 3, 1};
      Heap testHeap = new Heap(inData);
      testHeap.heapifyRecursive();
      testHeap.sortRecursive();
      assertArrayEquals(inData, refData);
    }

    @Test
    public void testUniqR() {
      int[] uniqData = {1, 1, 2, 2, 3};
      int[] inData = {1, 2, 1, 3, 2};
      Heap testHeap = new Heap(inData);
      testHeap.heapifyRecursive();
      testHeap.sortRecursive();
      assertArrayEquals(inData, uniqData);
    }

}
