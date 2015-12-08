public class QuickSortMed {
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


  private static int median(int[] data, int start, int stop)
  {
    int mid = ((stop + start) / 2);

    if (data[stop] < data[start]) {
      QuickSort.swap(data, stop, start);
    }

    if (data[mid] < data[start]) {
      QuickSort.swap(data, mid, start);
    }

    if (data[stop] < data[mid]) {
      QuickSort.swap(data, stop, mid);
    }

    return data[mid];
  }


  private static int getPivot(int[] data, int start, int stop) {
    int pivot = median(data, start, stop);
    int i = start;
    int j = stop;

    while (i < j) {
      if (data[j] > pivot) {
        j--;
      }
      if (data[i] < pivot) {
        i++;
      }

      QuickSort.swap(data, i, j);
    }

    return j;
  }

}
