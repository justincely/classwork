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

   System.out.println("Huffman Tree");
   translator.printTree();

   System.out.println("Encoding Code");

   translator.printCode();
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

         //Skip blank lines
         if (phrase.length() == 0) {
           continue;
         }

         //If O's or 1's, assume encoded message and attempt to decode
         if ((phrase.substring(0, 1).equals("0")) || (phrase.substring(0, 1).equals("1"))){
           try{
             System.out.println("Encoded message found: " + phrase);
             translated = translator.decode(phrase);
             System.out.println("Decoded version is   : " + translated);
             double compRatio = translator.compression(translated, phrase);
             System.out.println("Ecoding was " + compRatio + "% smaller than plaintext.");
             System.out.println();
           } catch (BadEncoding e) {
             System.out.println("Decoding Failed: " + e);
           }

         //If not O's or 1's, assume text and attempt to encode
         } else {
           System.out.println("Plain-text message found: " + phrase);
           try {
             translated = translator.encode(phrase);
             System.out.println("Encoded version is      : " + translated);
             double compRatio = translator.compression(phrase, translated);
             System.out.println("Ecoding was " + compRatio + "% smaller than plaintext.");
             System.out.println();
           } catch (EncodingError e) {
             System.out.println("Encoding Failed: " + e);
           }
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
