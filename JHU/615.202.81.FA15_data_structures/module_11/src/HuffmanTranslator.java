import java.util.Hashtable;
import java.util.Enumeration;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Map;

public class HuffmanTranslator{
  private Hashtable<String, Integer> frequencies = new Hashtable<String, Integer>();
  private Tree encoder = new Tree();

  public HuffmanTranslator() {
    /*
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
    */
    frequencies.put("X", 3);
    frequencies.put("Y", 1);
    frequencies.put("Z", 2);
  }

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

  public void buildEncoderTree() {
    Enumeration names;
    String str;
    int val;
    MinQueue queue = new MinQueue();

    names = frequencies.keys();
    while(names.hasMoreElements()) {
      str = (String) names.nextElement();
      val = (int) (int) frequencies.get(str);
      queue.insert(str, val, new Tree(str, val));
    }

    System.out.println("The sorted queue: ");
    queue.print();

    System.out.println("Building the tree: ");
    Tree huffTree = new Tree();
    QueueNode left = new QueueNode();
    QueueNode right = new QueueNode();
    while ((queue.count > 1)) {
      left = queue.pop();
      right = queue.pop();
      System.out.println(left.value + ": " + left.priority + " and " + right.value + ": " + right.priority);
      String newval = left.value + right.value;
      int newpriority = right.priority + left.priority;
      huffTree = new Tree(newval,
                          newpriority,
                          right.tree,
                          left.tree);
      queue.insert(newval, newpriority, huffTree);
      System.out.println("Tree at this point: ");
      queue.print();
    }
    System.out.println("Going through the tree: ");
    huffTree.preOrderTraverse();
  }
}
