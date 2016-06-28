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

   /* Verify that the correct number of arguments was supplied,
      otherwise display usage banner and exit */
   if (args.length < 1) {
     System.err.println("Incorrect number of arguments supplied.");
     return;
   }

   /* input file, output file, must be supplied in order */
   String inFile = args[0];

   System.out.println("#--------------------------#");
   System.out.println("#--Starting tree creation--#");
   System.out.println("#--------------------------#");
   try{
     Scanner scanner = new Scanner(new File(inFile));
     System.out.println("  Reading file " + inFile);

     if (scanner.hasNextInt()) {
       ArrayList<Integer> data = new ArrayList<Integer>();
       while (scanner.hasNextInt()) {
         data.add(scanner.nextInt());
       }
       Integer[] treedata = data.toArray(new Integer[0]);
       BinaryTree<Integer> intTree = new BinaryTree<Integer>(treedata);

       System.out.println("This tree's height is: " + intTree.height());
       System.out.println("The number of leaves is: " + intTree.getNumberOfLeaves());
       System.out.println("The number of non-leaves is: " + intTree.getNumberOfNonLeaves());
       System.out.println("And the post-order traversal is: ");
       intTree.postorder();
       System.out.println();

     } else if (scanner.hasNext()) {
       ArrayList<String> data = new ArrayList<String>();
       while (scanner.hasNext()) {
         data.add(scanner.next());
       }
       String[] treedata = data.toArray(new String[0]);
       BinaryTree<String> intTree = new BinaryTree<String>(treedata);

       System.out.println("This tree's height is: " + intTree.height());
       System.out.println("The number of leaves is: " + intTree.getNumberOfLeaves());
       System.out.println("The number of non-leaves is: " + intTree.getNumberOfNonLeaves());
       System.out.println("And the post-order traversal is: ");
       intTree.postorder();
       System.out.println();

     } else {
       System.out.println("No data found.");
     }

   } catch (java.io.FileNotFoundException e){
     System.out.println("ERROR: Cannot find file: " + inFile);
     return;
   }
 }

}
