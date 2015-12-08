/**Heap
  *
  * Array-based heap structure with sorting methods to facilitate heap-sort.
  */

public class Heap {
  public int size = 50;
  public int[] heapdata = new int[size];

  /** Default constructor
    */
  public Heap(){
  }

  /** Heap constructor from data
    *@param inData - array of input data to add to the heap
    */
  public Heap(int[] inData) {
      size = inData.length;
      heapdata = inData;
  }

  /**Perform iterative heapsort on heap data
    */
  public void sort() {
    int end = size-1;

    while (end > 0) {
      swap(end, 0);
      end--;
      siftDown(0, end);
    }
  }

  /**Perform recursive heapsort on heap data
    */
  public void sortRecursive() {
    int end = size-1;

    while (end > 0) {
      swap(end, 0);
      end--;
      siftDownRecursive(0, end);
    }
  }

  /**Transform the data into a heap
    */
  public void heapify() {
    int start = (size-2)/2;

    while (start >= 0) {
      siftDown(start, size-1);
      start--;
    }
  }

  /**Transform the data into a heap recursively
    */
  public void heapifyRecursive() {
    int start = (size)/2;

    while (start >= 0) {
      siftDownRecursive(start, size-1);
      start--;
    }
  }

  /**Enforce heap structure down the heap
    *@param start - starting index
    *@param end - ending index
    */
  public void siftDown(int start, int end) {
    int root = start;
    while (2*root + 1 <= end) {

      int child = 2*root + 1;
      int swap = root;
      if (heapdata[swap] < heapdata[child]) {
        swap = child;
      }

      if (child+1 <= end && heapdata[swap] < heapdata[child+1]) {
        swap = child+1;
      }

      if (swap == root) {
        return;
      } else {
        swap(root, swap);
        root = swap;
      }
    }
  }

  /**Enforce heap structure recursively down the heap
    *@param start - starting index
    *@param end - ending index
    */
  public void siftDownRecursive(int start, int end) {
    int left = 2*start;
    int right = left + 1;

    if (left > end) {
      return;
    }

    int mc = (right > end) ? left : (heapdata[left] > heapdata[right]) ? left : right;

    if (heapdata[start] >= heapdata[mc]) {
      return;
    }

    swap(start, mc);
    siftDownRecursive(mc, end);

  }

  /** Swap two elements in the heapdata array
    *@param i - first index
    *@param j - second index
    */
  private void swap(int i, int j){
    int tmp = heapdata[i];
    heapdata[i] = heapdata[j];
    heapdata[j] = tmp;
  }


}
