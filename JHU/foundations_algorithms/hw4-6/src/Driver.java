/**Driver
  *
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

   ArrayList<String> text = new ArrayList<String>();
   ArrayList<String> outText = new ArrayList<String>();

   // regex pattern match for encryption
   Pattern p = Pattern.compile("(^-{1,2}[a-zA-Z]+)[0-9]*$");

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
        System.out.println("--readbytes, -rb: Read input as bytes.");
        System.out.println("--output, -o: Filename to send output to.  If omitted, STDOUT will be used.");
        System.out.println("--writebytes, -wb: Write output as bytes.");
        System.out.println("--compress, -c: Flag to run compression algorithm.");
        System.out.println("--huffmanfiles: Files from which to build huffman tree.");
        System.out.println("--extract, -x: Flag to decompress the text.");
        System.out.println("--encrypt, -e: Flag to encrypt the text.");
        System.out.println("--decrypt, -d: Flag to decrypt the text.");
        return;

       case "--input":
       case "-i":
        inputFile = args[i+1];
        break;

       case "--readbytes":
       case "-rb":
        readAsBytes = true;
        break;

       case "--output":
       case "-o":
        outputFile = args[i+1];
        break;

       case "--writebytes":
       case "-wb":
        writeAsBytes = true;
        break;

       case "--compress":
       case "-c":
        compressAlg = args[i+1];
        compress = true;
        break;

       case "--huffmanfiles":
        for (int k=i+1; k<args.length; k++) {
          if (args[k].startsWith("-")) {
            break;
          } else {
            huffmanfiles.add(args[k]);
          }
        }
        break;

       case "--extract":
       case "-x":
         compressAlg = args[i+1];
         extract = true;
         break;

       case "--encrypt":
       case "-e":
        encryptArg = args[i+1];
        encrypt = true;
        break;

       case "--decrypt":
       case "-d":
        encryptArg = args[i+1];
        decrypt = true;
        break;

      case "--keyfile":
      case "-k":
        keyFile = args[i+1];
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
       if (readAsBytes) {
         text = readFileInputStream(inputFile);
       } else {
         text = ReadFile(inputFile);
       }
     } else {
       System.out.println("Input file not found on disk: " + inputFile);
     }
   }

   //encrypt or not
   if (encrypt) {
     if (encryptArg.equals("RSA")) {
       Encryption.RSAEncrypt(text);
     } else if (encryptArg.equals("ElGamal")) {
       System.out.println("NOT IMPLEMENTED!!!!!!!!!!!!!!!!!!");
     } else {
       Encryption.ceasarShift(text, Integer.parseInt(encryptArg));
     }
   }

   // begin compression
   if ((compress | extract) & (compressAlg.equals("huffman"))) {
     HuffmanTranslator translator = null;
     if (huffmanfiles.size() == 0) {
       translator = new HuffmanTranslator();
     } else{
       translator = new HuffmanTranslator(huffmanfiles);
     }

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

   } else if ((compress | extract) & (compressAlg.equals("lzw"))) {
     if (compress) {
       String allText = "";
       for (String s : text) {
         allText = allText + s;
       }

       ArrayList<Integer> LZWVals = LZW.compress(allText);

       for (Integer val : LZWVals) {
          outText.add(String.valueOf(val));
       }

     } else if (extract) {
       System.out.println("Extracting LZW");
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

   int rawsize = 0;
   for (String s : text) {
     rawsize = rawsize + s.length();
   }

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
     } else if (encryptArg.equals("ElGamal")) {
       System.out.println("NOT IMPLEMENTED!!!!!!!!!!!!!!!!!!");
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
