public class RadixSort {

  public static void sort(String[] data) throws java.lang.Exception {
    int wordSize = data[0].length();

    // Verify all words are the same length
    for (int i=1; i<data.length; i++) {
      if (data[i].length() != wordSize) {
        throw new Exception("Not all input has the same size");
      }
    }

    // Start sorting along the character columns
    for (int c=wordSize; c>0; c--) {
      // split out all the characters at column index
      String[] subArray = new String[data.length];
      for (int i=0; i<data.length; i++) {
        subArray[i] = data[i].substring(c-1, c);
      }

      Integer[] index = InsertionSort.sort(subArray);

      //re-order the raw input data by the sorted index of the characters
      String[] tmpArray = new String[data.length];
      for (int i=0; i<index.length; i++) {
        tmpArray[i] = data[index[i]];
      }

      //Display current state of the data
      System.out.println("Iterating on character: " + c);
      for (String s : tmpArray) {
        System.out.println(s);
      }

      //Copy over re-sorted data to be processed on the next iteration.
      data = tmpArray;

    }
  }

}
