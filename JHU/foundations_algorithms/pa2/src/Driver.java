/**Driver
  *
  *Main program to sort input files.  Input must be
  *from named files supplied via STDIN, and output will be written to
  *output file name specified using the selected sorting method.
  */

import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;


/**Parse matrices and calculate determinates.
  */
public class Driver{

/**Main driver to open files, parse data, sort data, and write to output.
  *
  *@param args[]   Holds command line arguments: input file, output file, sorting method
  */
 public static void main(String[]args){

   System.out.println("#---- Running problem 1a ----#");
   String[] data = {"COW", "DOG", "SEA", "RUG", "ROW", "MOB", "BOX", "TAB", "BAR", "EAR", "TAR", "DIG", "BIG", "TEA", "NOW", "FOX"};
   try {
     RadixSort.sort(data);
   } catch (Exception error) {
     System.out.println("Exception hit");
     System.out.println(error);
   }
   
 }

}
