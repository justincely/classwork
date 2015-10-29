import java.util.Scanner;
import java.io.File;

public class Driver{

/**Main driver to parse and evaluate expressions
  *
  *@param args[]   Holds command line arguments: filenames or
  *                postfix strings.
  */
 public static void main(String[]args){
   boolean isFileInput = false;
   int matrixSize = 0;
   int[] inData = new int[1];
   /* For each input argument, check if the supplied
    * input string corresponds to an accesible file.
    * If it doesn't, assume the input was an expression
    * to be evaluated and pass directly to the translator.
    */
   for (int i=0; i < args.length; i++) {
     //isFileInput = new File(args[i]).exists();
     try{
       Scanner scanner = new Scanner(new File(args[i]));
       System.out.println("Reading file " + args[i]);
       int j = 0;
       //int x = 0;
       //int y = 0;

       while(scanner.hasNextInt()){
         if (j==0){
           matrixSize = scanner.nextInt();
           System.out.println("Found matrix of size " + matrixSize);
           inData = new int[matrixSize*matrixSize];
           j++;
         } else{
           //x = (j-1)%matrixSize;
           //y = (j-1)/matrixSize;
           //System.out.println(j + " (X,Y)" + x + "," + y);
           inData[j-1] = scanner.nextInt();
           j++;
         }

         if (j-1 == matrixSize*matrixSize){
           Matrix myMatrix = new Matrix(matrixSize, inData);
           int det = Matrix.Determinate(myMatrix);
           System.out.println("\n#----Matrix----#");
           myMatrix.Print();
           System.out.println("Has determinate: " + det);
           System.out.println("#--------------#\n");
           j = 0;
           //x = 0;
           //y = 0;
         }
       }


       if (j!=0){
         //Raise error or something, only happens if matrix doesn't fully eval
         System.err.println("Error happened");
       }
     } catch (java.io.FileNotFoundException e){
       System.out.println("Cannot find file: " + args[i]);
       return;
     }

   }
   System.out.println("--Finished calculating all determinates--");
 }
}
