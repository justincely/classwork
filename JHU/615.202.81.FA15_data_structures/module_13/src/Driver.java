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

   try{
     Scanner scanner = new Scanner(new File(inFile));
     System.out.println("Reading file " + inFile);
     int size = 200;
     int[] data = new int[size];

     /* Loop over tokens in the input file.  The first integer is
      * interpreted as the order (n) of the subsequent matrix data.
      * The next n*n integers are parsed and used as input data
      * to the matrix.  Any non-int (such as float, char) found in
      * the data will cause the entire matrix to not be evaluated.
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

     //System.out.println(data.length);
     if (method.equalsIgnoreCase("insertion")) {
       InsertionSort.sort(data);
     } else if (method.equalsIgnoreCase("heap")) {
       Heap testHeap = new Heap(data);
     }

     writeData(data, outFile);

   } catch (java.io.FileNotFoundException e){
     System.out.println("Cannot find file: " + inFile);
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
