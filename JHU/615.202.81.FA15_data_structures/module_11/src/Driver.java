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

   /* Loop over input files */
   for (int i=0; i < args.length; i++) {
     try{
       Scanner scanner = new Scanner(new File(args[i]));

       System.out.println("#-----------------------#");
       System.out.println("Reading file " + args[i]);
       System.out.println("#-----------------------#");
       while(scanner.hasNext()){
         String phrase = scanner.nextLine();
         if (phrase.length() == 0) {
           continue;
         }

         if ((phrase.substring(0, 1).equals("0")) || (phrase.substring(0, 1).equals("1"))){
           System.out.println("Encoded message found: " + phrase);
           System.out.println("Decoded version is   : " + translator.decode(phrase));
         } else {
           System.out.println("Plain-text message found: " + phrase);
           System.out.println("Encoded version is      : " + translator.encode(phrase));
         }
       }

     } catch (java.io.FileNotFoundException e){
       System.out.println("Cannot find file: " + args[i]);
       return;
     }

   }
   System.out.println("--Finished all translations--");
 }
}
