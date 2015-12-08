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
    if ((stop-start) <= k) {
      InsertionSort.sort(data, start, stop);
    } else if (start < stop) {
      int p = getPivot(data, start, stop);
      sort(data, start, p-1, k);
      sort(data, p+1, stop, k);
    }
  }


  private static int getPivot(int[] data, int start, int stop) {
    int p = start;
    int partition = data[p];
    int i = start;
    int j = stop;

    swap(data, p, i);

    while (i < stop) {
      while (i < j && data[i] <= partition) {
        i++;
      }

      while (data[j] > partition) {
        j--;
      }

      if (i < j) {
        swap(data, i, j);
      } else {
        break;
      }

    }

    swap(data, start, j);
    return j;

  }

  public static void swap(int[] data, int i, int j){
    int tmp = data[i];
    data[i] = data[j];
    data[j] = tmp;
  }

}
