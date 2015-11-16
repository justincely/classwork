/**Driver
  *
  */

import java.util.Scanner;
import java.io.File;


/**Parse matrices and calculate determinates.
  */
public class Driver{

/**Main driver to open files
  *
  *@param args[]   Holds command line arguments: filename.
  */
 public static void main(String[]args){

   

   /* Loop over input files */
   for (int i=0; i < args.length; i++) {
     try{
       Scanner scanner = new Scanner(new File(args[i]));
       System.out.println("Reading file " + args[i]);

       while(scanner.hasNext()){
       }

     } catch (java.io.FileNotFoundException e){
       System.out.println("Cannot find file: " + args[i]);
       return;
     }

   }
   System.out.println("--Finished--");
 }
}
