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
    if ((stop-start) <= k) {
      InsertionSort.sort(data, start, stop);
    } else if (start < stop) {
      int p = getPivot(data, start, stop);
      sort(data, start, p-1, k);
      sort(data, p+1, stop, k);
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

    return mid;
  }


  private static int getPivot(int[] data, int start, int stop) {
    //int p = median(data, start, stop);
    int p = (start + stop) / 2;
    int partition = data[p];
    int i = start;
    int j = stop;
    QuickSort.swap(data, p, i);

    int count = 0;

    while (i <= stop && count++ < 1000) {

      while (i < j && data[i] <= partition) {
        i++;
      }

      while (data[j] > partition) {
        j--;
      }

      if (i < j) {
        QuickSort.swap(data, i, j);
      }

    }

    QuickSort.swap(data, start, j);
    return j;

  }

}
