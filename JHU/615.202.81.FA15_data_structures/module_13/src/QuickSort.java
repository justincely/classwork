/**QuickSort
  *
  * .
  */


public class QuickSort {

  /**Default constructor with just data array
    *@param data - data array
    */
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



  /**Perform the quicksort and partition algorithm
    *@param data - data array
    *@param start - starting index
    *@param stop - stopping index
    */
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


  /** Swap two elements in the  array
    *@param data - data array
    *@param i - first index
    *@param j - second index
    */
  public static void swap(int[] data, int i, int j){
    int tmp = data[i];
    data[i] = data[j];
    data[j] = tmp;
  }

}
