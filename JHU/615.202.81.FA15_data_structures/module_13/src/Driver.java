/**Driver
  *
  *Main program to sort input files.  Input must be
  *from named files supplied via STDIN, and output will be written to
  *output file name specified using the selected sorting method.
  */

import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;


/**Parse matrices and calculate determinates.
  */
public class Driver{

/**Main driver to open files, parse data, sort data, and write to output.
  *
  *@param args[]   Holds command line arguments: input file, output file, sorting method
  */
 public static void main(String[]args){

   /* Verify that the correct number of arguments was supplied,
      otherwise display usage banner and exit */
   if (args.length < 3) {
     System.err.println("Incorrect number of arguments supplied.");
     helpBanner();
     return;
   }

   /* input file, output file, sorting method must be supplied in order */
   String inFile = args[0];
   String outFile = args[1];
   String method = args[2];
   /* Use default minimum quick partition size is 0, unless specified */
   int k = 0;
   if (args.length == 4) {
     k = Integer.parseInt(args[3]);
   }

   System.out.println("#--------------------------#");
   System.out.println("#-- Starting file sorter --#");
   System.out.println("#--------------------------#");
   try{
     Scanner scanner = new Scanner(new File(inFile));
     System.out.println("  Reading file " + inFile);

     /* Data array to hold values will be set initially to 200,
        but will dynamically increase if the number if input elements
        exceeds the size.  After all values are parsed, the array will be
        sized back down to the actual number of elements.

        This is to avoid the use of ArrayLists, which was frowned upon in the
        assignment specifications.
    */
     int size = 200;
     int[] data = new int[size];

     /* Loop over tokens in the input file.  Any non-int (such as float, char)
      * found in the data will simply be ignored.
      */
     int count = 0;
     while (scanner.hasNextInt()){
       // resize when needed
       if (count >= size) {
         int[] tmp = data;
         size = size * 2;
         data = new int[size];
         backFill(data, tmp);
       }

       data[count] = scanner.nextInt();
       count++;
     }

     /* Shrink oversiezed array back down to input size after
        all values have been parsed.
      */
     int[] tmp = data;
     data = new int[count];
     backFill(data, tmp);


     long startTime = 0;
     long estimatedTime = 0;

     System.out.println("  Found " + data.length + " items to be sorted.");

     /* insertion sort */
     if (method.equalsIgnoreCase("insertion")) {
       startTime = System.nanoTime();
       InsertionSort.sort(data);
       estimatedTime = System.nanoTime() - startTime;
     /* heap sort */
     } else if (method.equalsIgnoreCase("heap")) {
       startTime = System.nanoTime();
       Heap testHeap = new Heap(data);
       testHeap.heapify();
       testHeap.sort();
       estimatedTime = System.nanoTime() - startTime;
     /* recursive heap sort */
     } else if (method.equalsIgnoreCase("heaprecursive")){
       startTime = System.nanoTime();
       Heap testHeap = new Heap(data);
       testHeap.heapifyRecursive();
       testHeap.sortRecursive();
       estimatedTime = System.nanoTime() - startTime;
     /* quick sort */
     } else if (method.equalsIgnoreCase("quick")) {
       /* If minimum partition size is supplied, use it */
       if (k > 0) {
         startTime = System.nanoTime();
         QuickSort.sort(data, k);
         estimatedTime = System.nanoTime() - startTime;
       } else {
         startTime = System.nanoTime();
         QuickSort.sort(data);
         estimatedTime = System.nanoTime() - startTime;
       }
     /* quick sort with median-of-3 */
     } else if (method.equalsIgnoreCase("quickmed")){
       startTime = System.nanoTime();
       QuickSortMed.sort(data);
       estimatedTime = System.nanoTime() - startTime;
     } else {
       System.out.println("ERROR: method not understood, please try again.");
       return;
     }

     /* Display message on elapsed time for given file and size.
        This format must remain unchanged to work with separate analysis
        scripts.
      */
     System.out.println("  " + method + "sort on " + inFile + " with "+ data.length + " items took " + estimatedTime + "ns to calculate");
     System.out.println("  Writing output to file: " + outFile);
     writeData(data, outFile);

   } catch (java.io.FileNotFoundException e){
     System.out.println("ERROR: Cannot find file: " + inFile);
     return;
   }
 }

 /**Write data to supplied filename
   *@param data - data array to be written
   *@param filename - full filepath to write data to
   */
 public static void writeData(int[] data, String filename) {
   try{
     PrintWriter writer = new PrintWriter(filename);
     for (int i=0; i<data.length; i++) {
       writer.println(data[i]);
     }
     writer.close();
   } catch (java.io.FileNotFoundException e) {
     System.err.println(e);
   }
 }


 /** Convenience method to print an array to STDOUT
   *
   *@param data - 1D array data to be printed
   */
 public static void printData(int[] data){
   for (int i=0; i<data.length; i++) {
     System.out.println(data[i]);
   }
 }

/** Fill new data array with elements from another array
  *
  *@param newdata - new blank array to be filled
  *@param olddata - filled/partially filled array to take values from
  */
public static void backFill(int[] newdata, int[] olddata) {
  int minsize = (olddata.length < newdata.length) ? olddata.length : newdata.length;

  for (int i=0; i<minsize; i++) {
    newdata[i] = olddata[i];
  }
}

/** Convenience function to display usage of Driver program
  */
public static void helpBanner(){
  System.out.println("usage:  java Driver <infile> <outfile> <sorting algorithm>");
  System.out.println("\n      algorithm may be any of: insertion, heap, heaprecursive, quick, quickmed.");
  System.out.println("      For any of the quicksorts, an optional minimum partition size may be suppied, ex: \n");
  System.out.println("usage:  java Driver <infile> <outfile> quick 50");
}

}
