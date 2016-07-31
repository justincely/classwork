/**Driver
  *
  */


import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Driver{

/**Main driver to open files
  *
  *@param args[]   Holds command line arguments: filename.
  */
 public static void main(String[] args) throws Exception {
   String inputFile = "";
   String outputFile = "";
   Boolean compress = false;
   Boolean extract = false;
   Boolean encrypt = false;
   Boolean decrypt = false;

   ArrayList<String> text = new ArrayList<String>();
   ArrayList<String> outText = new ArrayList<String>();

   // regex pattern match for encryption
   Pattern p = Pattern.compile("^(-{1,2}[a-z]+)[0-9]+$");

   for (int i=0; i<args.length; i++) {
     Matcher m = p.matcher(args[i]);
     String inputArg = "";
     if (m.find()) {
       inputArg = m.group(1);
     }

     switch (inputArg) {
       case "--help":
       case "-h":
        System.out.println("Usage: ");
        System.out.println("--help, -h: Print this helpfile and exit.");
        System.out.println("--input, -i: Filename to read input text from.");
        System.out.println("--output, -o: Filename to send output to.  If omitted, STDOUT will be used.");
        System.out.println("--compress, -c: Flag to run compression algorithm.");
        System.out.println("--extract, -x: Flag to decompress the text.");
        System.out.println("--encrypt, -e: Flag to encrypt the text.");
        System.out.println("--decrypt, -d: Flag to decrypt the text.");
        return;

       case "--input":
       case "-i":
        inputFile = args[i+1];
        break;

       case "--output":
       case "-o":
        outputFile = args[i+1];
        break;

       case "--compress":
       case "-c":
        compress = true;
        break;

       case "--extract":
       case "-x":
         extract = true;
         break;

       case "--encrypt":
       case "-e":
        Pattern e_pattern = Pattern.compile("^-{1,2}[a-z]+([0-9]+)$");
        Matcher e_match = e_pattern.matcher(args[i]);
        Integer shift_val = 0;
        //String shift_val = "0";
        if (e_match.find()) {
          shift_val = Integer.parseInt(e_match.group(1));
        }
        System.out.println("Found simple shift of value " + shift_val);

        encrypt = true;
        break;

       case "--decrypt":
       case "-d":
        decrypt = true;
        break;
     }
   }

   //Exit if invalid flags are supplied.
   if (compress & extract) {
     System.out.println("Incompatible arguments supplied: --compress, --extract");
     return;
   }

   if (encrypt & decrypt) {
     System.out.println("Incompatible arguments supplied: --encrypt, --decrypt");
   }

   // Read input file
   if (inputFile != "") {
     File buffer = new File(inputFile);
     if (buffer.exists() && !buffer.isDirectory()) {
       text = ReadFile(inputFile);
     } else {
       System.out.println("Input file not found on disk: " + inputFile);
     }
   }


   // begin compression
   if (compress | extract) {
     HuffmanTranslator translator = new HuffmanTranslator();
     translator.buildEncoderTree();

     for (int i=0; i<text.size(); i++) {
       if (compress) {
         String newString = translator.encode(text.get(i));
         outText.add(newString);
       } else if (extract) {
         String newString = translator.decode(text.get(i));
         outText.add(newString);
       }
     }

   }

   //Output information: either to stdout or a filename
   if (outputFile != "") {
     System.out.println("#-----------------------#");
     System.out.println("Writing file " + outputFile);
     System.out.println("#-----------------------#");
     WriteToFile(outText, outputFile);
   } else {
     System.out.println("No output file supplied, redirecting to STDOUT");
     for (int i=0; i<outText.size(); i++) {
       System.out.println(outText.get(i));
     }
   }

 }

 private static ArrayList ReadFile(String filename) throws java.io.FileNotFoundException {
   Scanner scanner = new Scanner(new File(filename));

   ArrayList<String> words = new ArrayList<String>();

   System.out.println("#-----------------------#");
   System.out.println("Reading file " + filename);
   System.out.println("#-----------------------#");

   while(scanner.hasNext()){
     words.add(scanner.next());
   }

   return words;
 }

 private static void WriteToFile(ArrayList text, String outName) {
   PrintWriter pw = null;

   try {
      File file = new File(outName);
      FileWriter fw = new FileWriter(file, true);
      pw = new PrintWriter(fw);

      for (int i=0; i<text.size(); i++) {
        pw.println(text.get(i));
      }

   } catch (IOException e) {
      e.printStackTrace();
   } finally {
      if (pw != null) {
         pw.close();
      }
   }
 }

}
