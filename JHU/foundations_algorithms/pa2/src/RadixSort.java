public class RadixSort {

  public static void sort(String[] data) throws java.lang.Exception {
    int wordSize = data[0].length();
    for (int i=1; i<data.length; i++) {
      if (data[i].length() != wordSize) {
        throw new Exception("Not all input has the same size");
      }
    }

    for (int c=wordSize; c>0; c--) {
      String[] subArray = new String[data.length];
      for (int i=0; i<data.length; i++) {
        subArray[i] = data[i].substring(c-1, c);
      }

      Integer[] index = InsertionSort.sort(subArray);
      for (int i=0; i<index.length; i++) {
        System.out.println("Index: " + index[i]);
      }

      String[] tmpArray = new String[data.length];
      for (int i=0; i<index.length; i++) {
        tmpArray[i] = data[index[i]];
      }

      data = tmpArray;

    }

    for (int i=0; i<data.length; i++) {
      System.out.println(data[i]);
    }
  }

}
