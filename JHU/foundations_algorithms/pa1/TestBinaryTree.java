/** Original code was provided by "Introduction to Java Programming"
 * 8th Edition by Y. Daniel Liang, pages 858 - 869
 *
 *  This code is used for educational purposes only
 *  in 605.421 Foundations of Algorithms.
 *
 */

public class TestBinaryTree {
  public static void main(String[] args) {
    // Create a BinaryTree
    BinaryTree<String> tree = new BinaryTree<String>();
    tree.insert("George");
    tree.insert("Michael");
    tree.insert("Tom");
    tree.insert("Adam");
    tree.insert("Jones");
    tree.insert("Peter");
    tree.insert("Daniel");

    // Traverse tree
    System.out.print("Inorder (sorted): ");
    tree.inorder();
    System.out.print("\nPostorder: ");
    tree.postorder();
    System.out.print("\nPreorder: ");
    tree.preorder();
    System.out.print("\nThe number of nodes is " + tree.getSize());

    // Search for an element
    System.out.print("\nIs Peter in the tree? " +
      tree.search("Peter"));

    // Get a path from the root to Peter
    System.out.print("\nA path from the root to Peter is: ");
    java.util.ArrayList<BinaryTree.TreeNode<String>>  path
      = tree.path("Peter");
    for (int i = 0; path != null && i < path.size(); i++)
      System.out.print(path.get(i).element + " ");

    //Integer[] numbers = {10, 5, 15, 1, 7, 13, 17};
    Integer[] numbers = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    BinaryTree<Integer> intTree = new BinaryTree<Integer>(numbers);
    System.out.print("\nInorder (sorted): ");
    intTree.inorder();
    System.out.print("\nPostorder: ");
    intTree.postorder();
    System.out.print("\nSize of this tree: ");
    System.out.print(intTree.getSize());
    System.out.print("\nHeight of this tree: ");
    System.out.print(intTree.height());
    System.out.print("\nLeaves in this tree; ");
    System.out.print(intTree.nonleaves());
    System.out.print("\nNonleaves in this tree; ");
    System.out.print(intTree.leaves());


    System.out.println();
  }
}
