public class QuickSort {
  public static void sort(int[] data){
    sort(data, 0, data.length-1);
  }

  public static void sort(int[] data, int k) {
    sort(data, 0, data.length-1, k);
  }

  public static void sort(int[] data, int start, int stop) {
    if (start < stop) {
      int p = getPivot(data, start, stop);
      sort(data, start, p-1);
      sort(data, p+1, stop);
    }
  }

  public static void sort(int[] data, int start, int stop, int k){
    if (data.length <= k) {
      InsertionSort.sort(data);
    } else {
      sort(data, 0, data.length-1);
    }
  }


  private static int getPivot(int[] data, int start, int stop) {
    int pivot = data[stop];
    int i = start;

    for (int j=start; j<=stop-1; j++) {
      if (data[j] <= pivot) {
        swap(data, i, j);
        i++;
      }
    }
    swap(data, i, stop);

    return i;
  }

  private static void swap(int[] data, int i, int j){
    int tmp = data[i];
    data[i] = data[j];
    data[j] = tmp;
  }

}
