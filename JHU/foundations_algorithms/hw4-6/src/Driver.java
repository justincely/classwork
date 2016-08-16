/**Driver that allows running of algorithms on text input
  *
  * Justin Ely
  */


import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
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
   // Variables to hold all command-line inputs and switches.
   String inputFile = "";
   String outputFile = "";
   Boolean compress = false;
   String compressAlg = "";
   Boolean extract = false;
   Boolean encrypt = false;
   Boolean decrypt = false;
   String encryptArg = "";
   String keyFile = "";
   Boolean readAsBytes = false;
   Boolean writeAsBytes = false;
   ArrayList<String> huffmanfiles = new ArrayList<String>();

   // input and output text Lists
   ArrayList<String> text = new ArrayList<String>();
   ArrayList<String> outText = new ArrayList<String>();

   // regex pattern match for command-line arguments
   Pattern p = Pattern.compile("(^-{1,2}[a-zA-Z]+)[0-9]*$");

   // Loop through command-line arguments
   for (int i=0; i<args.length; i++) {
     Matcher m = p.matcher(args[i]);

     String inputArg = "";
     if (m.find()) {
       inputArg = m.group(1);
     }

     // Handle each found Argument.  Only arguments starting with -- or -
     // will be parsed as arguments. Everything else will only be parsed
     // if there is a rule to take in the value with a given option.
     switch (inputArg) {
       case "--help":
       case "-h":
        System.out.println("Usage: ");
        System.out.println("--help, -h: Print this helpfile and exit.");
        System.out.println("--input, -i: Filename to read input text from.");
        System.out.println("--readbytes, -rb: Read input as binary.");
        System.out.println("--output, -o: Filename to send output to.  If omitted, STDOUT will be used.");
        System.out.println("--writebytes, -wb: Write output as binary.");
        System.out.println("--compress, -c: Flag to run compression.");
        System.out.println("  -c huffman: to run huffman compression");
        System.out.println("  -c lzw: to run LZW compression.");
        System.out.println("--huffmanfiles: Files from which to build huffman tree.");
        System.out.println("  only used if huffman compression is run.");
        System.out.println("--extract, -x: Flag to decompress the text.");
        System.out.println("  -x huffman: decompress with Huffman");
        System.out.println("  -x lzw: decompress with LZW.");
        System.out.println("--encrypt, -e: Flag to encrypt the text.");
        System.out.println("  -e INT: encrypt with simple shift cypher of INT.");
        System.out.println("  -e RSA: run RSA encryption");
        System.out.println("  -e AES: run AES encruption");
        System.out.println("--decrypt, -d: Flag to decrypt the text.");
        System.out.println("  -d INT: decrypt with simple shift cypher of INT.");
        System.out.println("  -d RSA: decrypt with RSA.");
        System.out.println("  -d AES: decrypt with AES.");
        return;

        // File to take input text from
       case "--input":
       case "-i":
        inputFile = args[i+1];
        break;

        // Flag to read input as binary instead of ascii
       case "--readbytes":
       case "-rb":
        readAsBytes = true;
        break;

        // File to output changed text to
       case "--output":
       case "-o":
        outputFile = args[i+1];
        break;

        // Flag to write output as binary instead of ascii
       case "--writebytes":
       case "-wb":
        writeAsBytes = true;
        break;

        // Flag to compress input
        // Will parse the following argument to specify compression algorithm
       case "--compress":
       case "-c":
        compressAlg = args[i+1];
        compress = true;
        break;

        // Specify input text files to build huffman tree.
        // all strings following argument until next optional parameter is found
        // specified by string starting with '-'.
       case "--huffmanfiles":
        for (int k=i+1; k<args.length; k++) {
          if (args[k].startsWith("-")) {
            break;
          } else {
            huffmanfiles.add(args[k]);
          }
        }
        break;

        // Extract (decompress) input
        // Immediately following argument will specify algorithm to use
       case "--extract":
       case "-x":
         compressAlg = args[i+1];
         extract = true;
         break;

        // Flag to encrypt output before writing.
        // Following argument will specify algorithm to use
       case "--encrypt":
       case "-e":
        encryptArg = args[i+1];
        encrypt = true;
        break;

        // Flag to decrypt output after reading.
        // Following argument will specify algorithm to use
       case "--decrypt":
       case "-d":
        encryptArg = args[i+1];
        decrypt = true;
        break;

        // File to specify keys for encryption algorithms
      case "--keyfile":
      case "-k":
        keyFile = args[i+1];
        break;
     }
   }

   // Exit if invalid flags are supplied.  Compression and extraction cannot be
   // used simultaneously.
   if (compress & extract) {
     System.out.println("Incompatible arguments supplied: --compress, --extract");
     return;
   }

   // Exit if invalid flags are supplied.  Encryption and decryption cannot be
   // used simultaneously.
   if (encrypt & decrypt) {
     System.out.println("Incompatible arguments supplied: --encrypt, --decrypt");
   }

   // Read text from input file and store in ArrayList
   if (inputFile != "") {
     File buffer = new File(inputFile);
     if (buffer.exists() && !buffer.isDirectory()) {
       if (readAsBytes) {
         text = readFileInputStream(inputFile);
       } else {
         text = ReadFile(inputFile);
       }
     } else {
       System.out.println("Input file not found on disk: " + inputFile);
     }
   }

   // Run encryption algorithm if given by input arguments.
   if (encrypt) {
     if (encryptArg.equals("RSA")) {
       Encryption.RSAEncrypt(text);
     } else if (encryptArg.equals("AES")) {
       Encryption.AESEncrypt(text);
     } else {
       Encryption.ceasarShift(text, Integer.parseInt(encryptArg));
     }
   }

   // Run compression or extraction.
   if ((compress | extract) & (compressAlg.equals("huffman"))) {

     // Initialize huffman tree
     HuffmanTranslator translator = null;
     if (huffmanfiles.size() == 0) {
       translator = new HuffmanTranslator();
     } else{
       translator = new HuffmanTranslator(huffmanfiles);
     }

     // Build the encoder tree
     translator.buildEncoderTree();

     // Compress or extract each line of text.
     for (int i=0; i<text.size(); i++) {
       if (compress) {
         String newString = translator.encode(text.get(i));
         outText.add(newString);
       } else if (extract) {
         String newString = translator.decode(text.get(i));
         outText.add(newString);
       }
     }

     // Run LZW compression algorithm
   } else if ((compress | extract) & (compressAlg.equals("lzw"))) {
     if (compress) {
       System.out.println("Compressing with LZW algorithm.");
       // concatenate all text into a single string to work with LZW compression.
       String allText = "";
       for (String s : text) {
         allText = allText + s;
       }

       ArrayList<Integer> LZWVals = LZW.compress(allText);
       for (Integer val : LZWVals) {
          outText.add(String.valueOf(val));
       }

     } else if (extract) {
       System.out.println("Extracting with LZW algorithm.");

       ArrayList<Integer> LZWVals = new ArrayList<Integer>();
       for (String s: text) {
         LZWVals.add(Integer.parseInt(s));
       }
       outText.add(LZW.decompress(LZWVals));
     }
   } else {
     for (String s : text) {
       outText.add(s);
     }
   }

   // Compute raw size of text input
   int rawsize = 0;
   for (String s : text) {
     rawsize = rawsize + s.length();
   }

   // Compute size of text output
   int outsize = 0;
   for (String s : outText) {
     outsize = outsize + s.length();
   }

   System.out.println("Raw character length: " + 8*rawsize);
   System.out.println("Compressed character length: " + outsize);
   System.out.println("Compression ratio of " + (8*rawsize - outsize)/(float) (8.0 * rawsize));

   // Decrypt or not
   if (decrypt) {
     if (encryptArg.equals("RSA")) {
       Encryption.RSADecrypt(outText, keyFile);
     } else if (encryptArg.equals("AES")) {
       Encryption.AESDecrypt(outText, keyFile);
     } else {
       Encryption.ceasarShift(outText, -1*Integer.parseInt(encryptArg));
     }
   }

   //Output information: either to stdout or a filename
   if (outputFile != "") {
     System.out.println("#-----------------------#");
     System.out.println("Writing file " + outputFile);
     System.out.println("#-----------------------#");
     if (writeAsBytes) {
       WriteToFile(outText, outputFile, writeAsBytes);
     } else {
       WriteToFile(outText, outputFile);
     }
   } else {
     System.out.println("No output file supplied, redirecting to STDOUT");
     for (int i=0; i<outText.size(); i++) {
       if (writeAsBytes) {
         System.out.println(outText.get(i).getBytes());
       } else {
         System.out.println(outText.get(i));
       }
     }
   }

 }


 /** Read from input File
  */
 private static ArrayList ReadFile(String filename) throws java.io.FileNotFoundException {
   Scanner scanner = new Scanner(new File(filename));

   ArrayList<String> words = new ArrayList<String>();

   System.out.println("#-----------------------#");
   System.out.println("Reading file " + filename);
   System.out.println("#-----------------------#");

   while(scanner.hasNextLine()){
     words.add(scanner.nextLine());
   }

   return words;
 }

 /** Read from input file as an
  */
 private static ArrayList readFileInputStream(String filename) throws IOException {
  String sContent=null;
  byte[] buffer =null;
  File a_file = new File(filename);

  try {
  FileInputStream fis = new FileInputStream(filename);
  int length = (int)a_file.length();
  buffer = new byte [length];
  fis.read(buffer);
  fis.close();
    } catch(IOException e) {
      e.printStackTrace();
    }

  sContent = new String(buffer);

  ArrayList<String> text = new ArrayList<String>();
  text.add(sContent);

    return text;
  }

  /** Write to file as ascii
    */
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

/** Write to file as binary
  */
 private static void WriteToFile(ArrayList<String> text, String outName, Boolean bytes) {
   OutputStream output = null;

   try {
     File file = new File(outName);
     output = new FileOutputStream(outName);

     for (int i=0; i<text.size(); i++) {
       byte[] outBytes = text.get(i).getBytes();
       output.write(outBytes);
     }

   } catch (IOException e) {
      e.printStackTrace();
   }  finally {
       try{
         if (output != null) {
            output.close();
         }
       } catch (IOException e) {
         e.printStackTrace();
     }
   }
 }
}
