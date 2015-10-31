/**Driver
  *
  *Main program to drive matrix determinate calculations.  Input must be
  *from named files supplied via STDIN.
  */

import java.util.Scanner;
import java.io.File;


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

   /* Loop over input files */
   for (int i=0; i < args.length; i++) {
     try{
       Scanner scanner = new Scanner(new File(args[i]));
       System.out.println("Reading file " + args[i]);
       int j = 0;
       boolean validMatrix = true;

       /* Loop over tokens in the input file.  The first integer is
        * interpreted as the order (n) of the subsequent matrix data.
        * The next n*n integers are parsed and used as input data
        * to the matrix.  Any non-int (such as float, char) found in
        * the data will cause the entire matrix to not be evaluated.
        */
       while(scanner.hasNext()){
         /* if first element and is an int, treat as order (n) */
         if ((j==0) && (scanner.hasNextInt())){
           matrixSize = scanner.nextInt();
           System.out.println("Found matrix of size " + matrixSize);
           inData = new int[matrixSize*matrixSize];
           j++;
         } else if (scanner.hasNextInt()){
           inData[j-1] = scanner.nextInt();
           j++;
         } else {
           String wrong = scanner.next();
           System.out.println("Non-int found, execution will halt: " + wrong);
           validMatrix = false;
           j++;
         }

         /* After nxn data has been parsed, initialize a matrix and
          * calculate the determinate.
          */
         if (j-1 == matrixSize*matrixSize){

           /* print to user if the matrix is invalid*/
           if (validMatrix == false) {
             System.out.println("\n#----Matrix----#");
             System.out.println("No valid matrix found.");
             System.out.println("#--------------#\n");

             //Reset counter for next matrix
             j = 0;
             validMatrix = true;
           } else{
             try {
               Matrix myMatrix = new Matrix(matrixSize, inData);
               long startTime = System.nanoTime();
               long det = Matrix.Determinate(myMatrix);
               long estimatedTime = System.nanoTime() - startTime;

               System.out.println("\n#----Matrix----#");
               myMatrix.Print();
               System.out.println("#----------------#");
               System.out.println("#------Stats-----#");
               System.out.println("Order " + matrixSize + " took " + estimatedTime + "ns to calculate");
               System.out.println("a determinate of " + det + ".");
               System.out.println("#----------------#\n");

             } catch (BadMatrix e) {
               System.err.println(e);
               validMatrix = false;
             }

             //Reset counter for next matrix
             j = 0;
             validMatrix = true;
           }
         }
       }

       if (j != 0){
         System.err.println("WARNING: Not enough data found to fill final matrix, exiting.");
         return;
       }
       
     } catch (java.io.FileNotFoundException e){
       System.out.println("Cannot find file: " + args[i]);
       return;
     }

   }
   System.out.println("--Finished calculating all determinates--");
 }
}
