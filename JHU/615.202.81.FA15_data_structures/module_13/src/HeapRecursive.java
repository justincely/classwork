public class HeapRecursive {
  public int size = 50;
  public int[] heapdata = new int[size];

  public HeapRecursive(){
  }

  public HeapRecursive(int[] inData) {
      size = inData.length;
      heapdata = inData;
      heapify();
      sort();
  }

  public void sort() {
    int end = size-1;

    while (end > 0) {
      swap(end, 0);
      end--;
      siftDown(0, end);
    }
  }

  public void heapify() {
    int start = (size-2)/2;

    while (start >= 0) {
      siftDown(start, size-1);
      start--;
    }
  }

  public void siftDown(int start, int end) {
    int root = start;
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
    siftDown(mc, end);

  }

  private void swap(int i, int j){
    int tmp = heapdata[i];
    heapdata[i] = heapdata[j];
    heapdata[j] = tmp;
  }


}
