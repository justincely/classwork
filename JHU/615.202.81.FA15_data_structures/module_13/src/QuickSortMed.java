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
    } else {
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

    System.out.print("median: " + data[start] + " " + data[mid] + " " + data[stop] + "\n");
    //QuickSort.swap(data, mid, stop);
    return mid;
  }


  private static int getPivot(int[] data, int start, int stop) {
    int p = median(data, start, stop);
    int i = start;
    int j = stop;
    System.out.println(start + " --> " + stop);
    int count = 0;
    int size = stop-start;

    while (count++ < 40) {

      for (int k=0; k<data.length; k++) {
        System.out.print(data[k] + " ");
      }
      //System.out.println(data[i] + " " + pivot + " " + data[j]);

      //if (data[i] == pivot && data[j] == pivot) {
      //  return j;
      //}

      while (data[j] > data[p]) {
        j--;
      }

      while (data[i] < data[p]) {
        i++;
      }

      System.out.print("start: " + data[i] + " stop: " + data[j] + " pivot: " + data[p] + " size: " + size + "\n");
      System.out.println(i + " " + j);
      if (i < j) {
        QuickSort.swap(data, i, j);
      } else if (data[j] < data[p]) {
        QuickSort.swap(data, j, p);
      }

    }
    QuickSort.swap(data, i, stop-1);
    return i;

  }

}
