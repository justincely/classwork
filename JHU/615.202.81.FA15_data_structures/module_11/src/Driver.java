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
         String translated;
         if (phrase.length() == 0) {
           continue;
         }

         if ((phrase.substring(0, 1).equals("0")) || (phrase.substring(0, 1).equals("1"))){
           System.out.println("Encoded message found: " + phrase);
           translated = translator.decode(phrase);
           System.out.println("Decoded version is   : " + translated);
         } else {
           System.out.println("Plain-text message found: " + phrase);
           translated = translator.encode(phrase);
           System.out.println("Encoded version is      : " + translated);
         }
         double compRatio = translator.compression(phrase, translated);
         System.out.println("Ecoding was " + compRatio + "% smaller than plaintext.");
         System.out.println();

       }

     } catch (java.io.FileNotFoundException e){
       System.out.println("Cannot find file: " + args[i]);
       return;
     }

   }
   System.out.println("--Finished all translations--");
 }
}
