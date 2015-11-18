import java.util.Hashtable;
import java.util.Enumeration;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Map;

public class HuffmanTranslator{
  private Hashtable<String, Integer> frequencies = new Hashtable<String, Integer>();
  private Tree<String> encoder = new Tree<String>();

  /**Default constructor
    *
    *<p> Frequencies are initialized to given lab inputs. </p>
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
      String newval = left.value + right.value;
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
    *<p>
    *</p>
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
        throw new BadEncoding("Non-zero found in encoded message");
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
    *<p>
    *</p>
    */
  public String encode(String input) {
    String message = "";
    Tree<String> tmpTree = encoder;
    for (int i=0; i<input.length(); i++) {
      String letter = input.substring(i, i+1).toLowerCase();
      char c = letter.charAt(0);
      if (Character.isLetter(c) == false) {
        continue;
      }

      while (tmpTree.isLeaf() == false) {
        if (tmpTree.left.data.toLowerCase().contains(letter)) {
          tmpTree = tmpTree.left;
          message = message + "0";
        } else if (tmpTree.right.data.toLowerCase().contains(letter)) {
          tmpTree = tmpTree.right;
          message = message + "1";
        } else {
          throw new EncodingError("No child contains letter: " + letter);
        }
      }

      //more Error handling
      if (tmpTree.data.toLowerCase().equals(letter)) {
        tmpTree = encoder;
      } else {
        throw new EncodingError("Found leaf, but does not contain: " + letter);
      }

    }
    return message;
  }

  public double compression(String plaintext, String codedtext) {
    return 100 * ((float) codedtext.length() - (8*plaintext.length())) / (8*plaintext.length());
  }

}
