public class Heap {
  public int size = 50;
  public int[] heapdata = new int[size];

  public Heap(){
  }

  public Heap(int[] inData) {
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

  private void swap(int i, int j){
    int tmp = heapdata[i];
    heapdata[i] = heapdata[j];
    heapdata[j] = tmp;
  }


}
