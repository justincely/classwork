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

/**Main driver to display output from .
  *
  *@param args[]   Holds command line arguments
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

   System.out.println("#---- Running samples from problem 2 ----#");
   MultiStack stacks = new MultiStack();
   System.out.println("Adding {1, 2, 3, 4, 5} to A");
   stacks.PushA(1);
   stacks.PushA(2);
   stacks.PushA(3);
   stacks.PushA(4);
   stacks.PushA(5);

   System.out.println("Adding {6, 7, 8, 9, 10} to B");
   stacks.PushB(6);
   stacks.PushB(7);
   stacks.PushB(8);
   stacks.PushB(9);
   stacks.PushB(10);

   System.out.println("MultiPopping 3 elements from A");
   stacks.MultiPopA(3);

   System.out.println("MultiPopping 3 elements from B");
   stacks.MultiPopB(3);

   System.out.println("Transferring all remaining items from A to B");
   stacks.Transfer(5);

   System.out.println("B now contains:");
   stacks.MultiPopB(100);
 }

}
