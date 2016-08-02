/**HuffmanTranslator
  *
  *<p> All the methods for building the huffman tree, encoding and deconding
  *    messages are contained here.
  *</p>
  */


import java.util.Hashtable;
import java.util.Enumeration;
//Used just for sorting strings of nodes - prettier output
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;

public class HuffmanTranslator{
  private Hashtable<String, Integer> frequencies = new Hashtable<String, Integer>();
  private Tree<String> encoder = new Tree<String>();

  /**Default constructor
    *
    *<p> Frequencies are initialized to given lab inputs.</p>
    */
  public HuffmanTranslator() {
    frequencies.put("A", 19);
    frequencies.put("B", 16);
    frequencies.put("C", 17);
    frequencies.put("D", 11);
    frequencies.put("E", 42);
    frequencies.put("F", 12);
    frequencies.put("G", 14);
    frequencies.put("H", 17);
    frequencies.put("I", 16);
    frequencies.put("J", 5);
    frequencies.put("K", 10);
    frequencies.put("L", 20);
    frequencies.put("M", 19);
    frequencies.put("N", 24);
    frequencies.put("O", 18);
    frequencies.put("P", 13);
    frequencies.put("Q", 1);
    frequencies.put("R", 25);
    frequencies.put("S", 35);
    frequencies.put("T", 25);
    frequencies.put("U", 15);
    frequencies.put("V", 5);
    frequencies.put("W", 21);
    frequencies.put("X", 2);
    frequencies.put("Y", 8);
    frequencies.put("Z", 3);
    frequencies.put("a", 19);
    frequencies.put("b", 16);
    frequencies.put("c", 17);
    frequencies.put("d", 11);
    frequencies.put("e", 42);
    frequencies.put("f", 12);
    frequencies.put("g", 14);
    frequencies.put("h", 17);
    frequencies.put("i", 16);
    frequencies.put("j", 5);
    frequencies.put("k", 10);
    frequencies.put("l", 20);
    frequencies.put("m", 19);
    frequencies.put("n", 24);
    frequencies.put("o", 18);
    frequencies.put("p", 13);
    frequencies.put("q", 1);
    frequencies.put("r", 25);
    frequencies.put("s", 35);
    frequencies.put("t", 25);
    frequencies.put("u", 15);
    frequencies.put("v", 5);
    frequencies.put("w", 21);
    frequencies.put("x", 2);
    frequencies.put("y", 8);
    frequencies.put("z", 3);
    frequencies.put(" ", 1);
  }

  /**Default constructor
    *
    *<p> Frequencies are initialized to given lab inputs.</p>
    */
  public HuffmanTranslator(ArrayList<String> filenames) throws java.io.FileNotFoundException {
    for (String fname: filenames) {
      Scanner scanner = new Scanner(new File(fname));

      ArrayList<String> words = new ArrayList<String>();

      System.out.println("#-----------------------#");
      System.out.println("Reading file " + fname);
      System.out.println("#-----------------------#");

      while(scanner.hasNextLine()) {
        String line = scanner.nextLine();
        for (int i=0; i<line.length(); i++) {
          String s = line.substring(i, i+1);
          if (frequencies.containsKey(s)) {
            frequencies.put(s, frequencies.get(s)+1);
          } else {
            frequencies.put(s, 1);
          }
        }
      }
    }

    //printFrequencies();

  }

  /**Print frequency content to STDOUT
    *
    *<p> Convenience function to display frequency information
    *    To the screen.  Each character will be on a new line, and the
    *    frequency for each will be : delimited.
    *</p>
    */
  public void printFrequencies() {
    Enumeration names;
    String str;

    names = frequencies.keys();
    while(names.hasMoreElements()) {
      str = (String) names.nextElement();
      System.out.println(str + ": " + frequencies.get(str));
    }
    System.out.println();
  }

  /**Print frequency content to STDOUT
    *
    *<p> Convenience function to display frequency information
    *    To the screen.  Each character will be on a new line, and the
    *    frequency for each will be : delimited.
    *</p>
    */
  public void printTree() {
    encoder.preOrderTraverse();
  }

  /**Print encoding to STDOUT
    *
    *<p> Convenience function to display letter encoding.
    *</p>
    */
  public void printCode() {
    String alpha = "abcdefghijklmnopqrstuvwxyz";

    for (int i=0; i<alpha.length(); i++) {
      System.out.println(alpha.substring(i, i+1) + " = " + encode(alpha.substring(i, i+1)));
    }
    System.out.println();

  }

  /**Assemble the huffman encoder tree from the frequency table
    *
    *<p> Using the priorities in the frequency table, build a binary huffman
    *    tree to use in later encoding and decoding messages.
    *</p>
    */
  public void buildEncoderTree() {
    Enumeration names;
    String str;
    int val;
    MinQueue queue = new MinQueue();

    //Populate MinQueue with Trees of given priority
    names = frequencies.keys();
    while(names.hasMoreElements()) {
      str = (String) names.nextElement();
      val = (int) frequencies.get(str);
      queue.insert(str, val, new Tree(str, val));
    }

    //Build up the tree
    Tree huffTree = new Tree();
    QueueNode left = new QueueNode();
    QueueNode right = new QueueNode();
    while ((queue.count > 1)) {
      //Pull out 2 trees at min frequency
      left = queue.pop();
      right = queue.pop();

      //Combine values and priority, create new parent tree
      String newval = right.value + left.value;

      //resort for pretty output
      char[] chars = newval.toCharArray();
      Arrays.sort(chars);
      newval = new String(chars);

      int newpriority = right.priority + left.priority;
      huffTree = new Tree(newval,
                          newpriority,
                          right.tree,
                          left.tree);

      //Push back onto queue at combined priority.
      queue.insert(newval, newpriority, huffTree);
    }
    encoder = huffTree;
  }


  /**Main decoder function.
    *
    *<p>Loop over the string, traversing the encoder tree until a leaf node
    *   is found.  Once a leaf is hit, append the letter at that leaf to the
    *   output message.  A 0 on input moves to the left child, a 1 moves to the
    *   right child.
    *</p>
    *@param String input
    *@returns String message
    */
  public String decode(String input){
    String message = "";
    Tree tmpTree = encoder;

    for (int i=0; i<input.length(); i++) {
      if (input.charAt(i) == '0') {
        tmpTree = tmpTree.left;
      } else if (input.charAt(i) == '1') {
        tmpTree = tmpTree.right;
      } else {
        throw new BadDecoding("Non-zero found in encoded message");
      }

      if (tmpTree.isLeaf()) {
        message = message + tmpTree.data;
        tmpTree = encoder;
      }

    }
    return message;
  }

  /**Main decoder function.
    *
    *<p>Loop over the string, traversing the encoder tree until a leaf node
    *   is found with only the letter as content.  After each step to a right
    *   child, append 1 to output.  After each stop to a left child, append a 0
    *   to output.
    *</p>
    *@param String input
    *@returns String message
    */
  public String encode(String input) {
    String message = "";
    Tree<String> tmpTree = encoder;
    for (int i=0; i<input.length(); i++) {
      String letter = input.substring(i, i+1);
      char c = letter.charAt(0);
      //if (Character.isLetter(c) == false) {
      //  continue;
      //}

      while (tmpTree.isLeaf() == false) {
        if (tmpTree.left.data.contains(letter)) {
          tmpTree = tmpTree.left;
          message = message + "0";
        } else if (tmpTree.right.data.contains(letter)) {
          tmpTree = tmpTree.right;
          message = message + "1";
        } else {
          System.out.println(letter + " " + tmpTree.left.data + " " +tmpTree.right.data);
          throw new EncodingError("No child contains letter: " + letter);
        }
      }

      if (tmpTree.data.equals(letter)) {
        tmpTree = encoder;
      } else {
        throw new EncodingError("Found leaf, but does not contain: " + letter);
      }

    }
    return message;
  }

  /**Calculate compression percent achieved
    *<p> Assuming 8-bit ASCII encoding, calculated what percentage of bits
    *    where saved due to the huffman encoding.
    *</p>
    *@param String plaintext
    *@param String codedtext
    */
  public double compression(String plaintext, String codedtext) {
    return 100 * ((float) (8*plaintext.length() - codedtext.length())) / (8*plaintext.length());
  }

}
