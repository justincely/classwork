/**InsertionSort
  *
  * .
  */

public class InsertionSort {

  public static Integer[] sort(String[] data) {
    return sort(data, 0, data.length-1);
  }

  /** sort input data
    *@param data - input array to be sorted in-place
    */
  public static Integer[] sort(String[] data, int start, int end){
    Integer[] outData = new Integer[data.length];
    for (int i=0; i<data.length; i++) {
      outData[i] = i;
    }

    for (int index=start; index<=end; index++) {
      String val = data[index];
      Integer indexVal = outData[index];
      int cursor = index;

      while(cursor > start && data[cursor-1].compareTo(val) > 0) {
        System.out.println(data[cursor-1] + " " + val);
        data[cursor] = data[cursor-1];
        outData[cursor] = outData[cursor-1];
        cursor--;
      }

      data[cursor] = val;
      outData[cursor] = indexVal;
    }

    System.out.println("data looks like");
    for (int i=0; i<data.length; i++) {
      System.out.println(data[i]);
    }
    return outData;
  }

}
