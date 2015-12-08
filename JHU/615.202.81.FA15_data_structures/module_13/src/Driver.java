/**Driver
  *
  *Main program to drive matrix determinate calculations.  Input must be
  *from named files supplied via STDIN.
  */

import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;


/**Parse matrices and calculate determinates.
  */
public class Driver{

/**Main driver to open files, parse data, calculated derminates and
  *handle errors.
  *
  *@param args[]   Holds command line arguments: filename.
  */
 public static void main(String[]args){
   int matrixSize = 0;
   int[] inData = new int[1];

   String inFile = args[0];
   String outFile = args[1];
   String method = args[2];
   int k = 0;
   if (args.length > 4) {
     k = Integer.parseInt(args[3]);
   }

   System.out.println("#--------------------------#");
   System.out.println("#-- Starting file sorter --#");
   System.out.println("#--------------------------#");
   try{
     Scanner scanner = new Scanner(new File(inFile));
     System.out.println("  Reading file " + inFile);
     int size = 200;
     int[] data = new int[size];

     /* Loop over tokens in the input file.  Any non-int (such as float, char)
      * found in the data will be ignored.
      */
     int count = 0;
     while (scanner.hasNextInt()){
       //resize if needed
       if (count >= size) {
         int[] tmp = data;
         size = size * 2;
         data = new int[size];
         backFill(data, tmp);
         //System.out.println(data.length);
       }

       data[count] = scanner.nextInt();
       count++;
     }

     int[] tmp = data;
     data = new int[count];
     backFill(data, tmp);

     long startTime = 0;
     long estimatedTime = 0;

     System.out.println("  Found " + data.length + " items to be sorted.");
     if (method.equalsIgnoreCase("insertion")) {
       startTime = System.nanoTime();
       InsertionSort.sort(data);
       estimatedTime = System.nanoTime() - startTime;
     } else if (method.equalsIgnoreCase("heap")) {
       startTime = System.nanoTime();
       Heap testHeap = new Heap(data);
       estimatedTime = System.nanoTime() - startTime;
     } else if (method.equalsIgnoreCase("quick")) {
       if (k > 0) {
         startTime = System.nanoTime();
         QuickSort.sort(data, k);
         estimatedTime = System.nanoTime() - startTime;
       } else {
         startTime = System.nanoTime();
         QuickSort.sort(data);
         estimatedTime = System.nanoTime() - startTime;
       }
     } else if (method.equalsIgnoreCase("quickmed")){
       startTime = System.nanoTime();
       QuickSortMed.sort(data);
       estimatedTime = System.nanoTime() - startTime;
     } else {
       System.out.println("ERROR: method not understood, please try again.");
       return;
     }

     System.out.println("  " + method + " on " + data.length + " items took " + estimatedTime + "ns to calculate");

     System.out.println("  Writing output to file: " + outFile);
     writeData(data, outFile);

   } catch (java.io.FileNotFoundException e){
     System.out.println("ERROR: Cannot find file: " + inFile);
     return;
   }
 }

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

 public static void printData(int[] data){
   for (int i=0; i<data.length; i++) {
     System.out.println(data[i]);
   }
 }

public static void backFill(int[] newdata, int[] olddata) {
  int minsize = (olddata.length < newdata.length) ? olddata.length : newdata.length;

  for (int i=0; i<minsize; i++) {
    newdata[i] = olddata[i];
  }
}


}
