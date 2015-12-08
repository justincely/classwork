/**InsertionSort
  *
  * .
  */

public class InsertionSort {
  /** sort input data
    *@param data - input array to be sorted in-place
    */
  public static void sort(int[] data){
    for (int index=1; index < data.length; index++) {
      int val = data[index];
      int cursor = index;

      while(cursor > 0 && data[cursor-1] > val) {
        data[cursor] = data[cursor-1];
        cursor--;
      }
      data[cursor] = val;
    }
  }

}
