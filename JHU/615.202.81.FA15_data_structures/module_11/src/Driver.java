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

   HuffmanTranslator translator = new HuffmanTranslator();
   translator.buildEncoderTree();

   //translator.decode("1111110100");
   translator.decode("01011001010110011111011011");
   translator.decode("10110000101010011011101101100010110010101100010111000110111");
   translator.decode("11111110001000111111101011111011001111111000100011111000001010000001110010111");
   translator.decode("1101101000010001111100011111101000000101100");

   translator.encode("EIEIOH");

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
