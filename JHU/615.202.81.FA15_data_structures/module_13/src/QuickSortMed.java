/**quickSortMed
  *
  * Quick sort with the pivot as the median of 3
  */

public class QuickSortMed {
  public static void sort(int[] data){
    sort(data, 0, data.length-1);
  }

  /**Constructor specifying minimum partition size
    *@param data - data array
    *@param k - minimum partition size
    */
  public static void sort(int[] data, int k) {
    sort(data, 0, data.length-1, k);
  }

  /**Constructor specifying start and stop indexes of current partition
    *@param data - data array
    *@param start - starting index
    *@param end - ending index
    */
  public static void sort(int[] data, int start, int stop) {
    if (start < stop) {
      int p = getPivot(data, start, stop);
      sort(data, start, p-1);
      sort(data, p+1, stop);
    }
  }

  /**Constructor with all arguements
    *@param data - data array
    *@param start - starting index
    *@param stop - ending index
    */
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

  /**Perform the quicksort and partition algorithm
    *@param data - data array
    *@param start - starting index
    *@param stop - stopping index
    */
  private static int getPivot(int[] data, int start, int stop) {
    int p = median(data, start, stop);
    //int p = (start + stop) / 2;
    int partition = data[p];
    int i = start;
    int j = stop;
    QuickSort.swap(data, p, i);

    while (i < stop) {
      while (i < j && data[i] <= partition) {
        i++;
      }

      while (data[j] > partition) {
        j--;
      }

      if (i < j) {
        QuickSort.swap(data, i, j);
      } else {
        break;
      }

    }

    QuickSort.swap(data, start, j);
    return j;

  }

}
