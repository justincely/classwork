/** Original code was provided by "Introduction to Java Programming"
 * 8th Edition by Y. Daniel Liang, pages 858 - 869
 *
 *  This code is used for educational purposes only
 *  in 605.421 Foundations of Algorithms.
 *
 *  This code has been modified by Justin Ely for Programming
 *  assignment 1.
 *
 *  The solutions to the homework problems begin on line 303,
 *  and continue to the end of the file.  Note that the solution to
 *  problem 2 was supplied with the given source, and remains in it's
 *  initial implementation location; names as postorder().
 *
 *  print statements to show algorithm process are included.  They can
 *  be commented out from the above mentioned lines if not desired.
 */

public class BinaryTree<E extends Comparable<E>>
    extends AbstractTree<E> implements Cloneable {
  protected TreeNode<E> root;
  protected int size = 0;

  /** Create a default binary tree */
  public BinaryTree() {
  }

  /** Create a binary tree from an array of objects */
  public BinaryTree(E[] objects) {
    for (int i = 0; i < objects.length; i++)
      insert(objects[i]);
  }

  /** Returns true if the element is in the tree */
  public boolean search(E e) {
    TreeNode<E> current = root; // Start from the root

    while (current != null) {
      if (e.compareTo(current.element) < 0) {
        current = current.left;
      }
      else if (e.compareTo(current.element) > 0) {
        current = current.right;
      }
      else // element matches current.element
        return true; // Element is found
    }

    return false;
  }

  /** Insert element o into the binary tree
   * Return true if the element is inserted successfully */
  public boolean insert(E e) {
    if (root == null)
      root = createNewNode(e); // Create a new root
    else {
      // Locate the parent node
      TreeNode<E> parent = null;
      TreeNode<E> current = root;
      while (current != null)
        if (e.compareTo(current.element) < 0) {
          parent = current;
          current = current.left;
        }
        else if (e.compareTo(current.element) > 0) {
          parent = current;
          current = current.right;
        }
        else
          return false; // Duplicate node not inserted

      // Create the new node and attach it to the parent node
      if (e.compareTo(parent.element) < 0)
        parent.left = createNewNode(e);
      else
        parent.right = createNewNode(e);
    }

    size++;
    return true; // Element inserted
  }

  protected TreeNode<E> createNewNode(E e) {
    return new TreeNode<E>(e);
  }

  /** Inorder traversal from the root*/
  public void inorder() {
    inorder(root);
  }

  /** Inorder traversal from a subtree */
  protected void inorder(TreeNode<E> root) {
    if (root == null) return;
    inorder(root.left);
    System.out.print(root.element + " ");
    inorder(root.right);
  }

  /** Postorder traversal from the root */
  public void postorder() {
    postorder(root);
  }

  /** Postorder traversal from a subtree */
  protected void postorder(TreeNode<E> root) {
    if (root == null) return;
    postorder(root.left);
    postorder(root.right);
    System.out.print(root.element + " ");
  }

  /** Preorder traversal from the root */
  public void preorder() {
    preorder(root);
  }

  /** Preorder traversal from a subtree */
  protected void preorder(TreeNode<E> root) {
    if (root == null) return;
    System.out.print(root.element + " ");
    preorder(root.left);
    preorder(root.right);
  }

  /** Inner class tree node */
  public static class TreeNode<E extends Comparable<E>> {
    E element;
    TreeNode<E> left;
    TreeNode<E> right;

    public TreeNode(E e) {
      element = e;
    }
  }

  /** Get the number of nodes in the tree */
  public int getSize() {
    return size;
  }

  /** Returns the root of the tree */
  public TreeNode<E> getRoot() {
    return root;
  }

  /** Returns a path from the root leading to the specified element */
  public java.util.ArrayList<TreeNode<E>> path(E e) {
    java.util.ArrayList<TreeNode<E>> list =
      new java.util.ArrayList<TreeNode<E>>();
    TreeNode<E> current = root; // Start from the root

    while (current != null) {
      list.add(current); // Add the node to the list
      if (e.compareTo(current.element) < 0) {
        current = current.left;
      }
      else if (e.compareTo(current.element) > 0) {
        current = current.right;
      }
      else
        break;
    }

    return list; // Return an array of nodes
  }

  /** Delete an element from the binary tree.
   * Return true if the element is deleted successfully
   * Return false if the element is not in the tree */
  public boolean delete(E e) {
    // Locate the node to be deleted and also locate its parent node
    TreeNode<E> parent = null;
    TreeNode<E> current = root;
    while (current != null) {
      if (e.compareTo(current.element) < 0) {
        parent = current;
        current = current.left;
      }
      else if (e.compareTo(current.element) > 0) {
        parent = current;
        current = current.right;
      }
      else
        break; // Element is in the tree pointed by current
    }

    if (current == null)
      return false; // Element is not in the tree

    // Case 1: current has no left children
    if (current.left == null) {
      // Connect the parent with the right child of the current node
      if (parent == null) {
        root = current.right;
      }
      else {
        if (e.compareTo(parent.element) < 0)
          parent.left = current.right;
        else
          parent.right = current.right;
      }
    }
    else {
      // Case 2: The current node has a left child
      // Locate the rightmost node in the left subtree of
      // the current node and also its parent
      TreeNode<E> parentOfRightMost = current;
      TreeNode<E> rightMost = current.left;

      while (rightMost.right != null) {
        parentOfRightMost = rightMost;
        rightMost = rightMost.right; // Keep going to the right
      }

      // Replace the element in current by the element in rightMost
      current.element = rightMost.element;

      // Eliminate rightmost node
      if (parentOfRightMost.right == rightMost)
        parentOfRightMost.right = rightMost.left;
      else
        // Special case: parentOfRightMost == current
        parentOfRightMost.left = rightMost.left;
    }

    size--;
    return true; // Element inserted
  }

  /** Obtain an iterator. Use inorder. */
  public java.util.Iterator iterator() {
    return inorderIterator();
  }

  /** Obtain an inorder iterator */
  public java.util.Iterator inorderIterator() {
    return new InorderIterator();
  }

  // Inner class InorderIterator
  class InorderIterator implements java.util.Iterator {
    // Store the elements in a list
    private java.util.ArrayList<E> list = new java.util.ArrayList<E>();
    private int current = 0; // Point to the current element in list

    public InorderIterator() {
      inorder(); // Traverse binary tree and store elements in list
    }

    /** Inorder traversal from the root*/
    private void inorder() {
      inorder(root);
    }

    /** Inorder traversal from a subtree */
    private void inorder(TreeNode<E> root) {
      if (root == null)return;
      inorder(root.left);
      list.add(root.element);
      inorder(root.right);
    }

    /** Next element for traversing? */
    public boolean hasNext() {
      if (current < list.size())
        return true;

      return false;
    }

    /** Get the current element and move cursor to the next */
    public Object next() {
      return list.get(current++);
    }

    /** Remove the current element and refresh the list */
    public void remove() {
      delete(list.get(current)); // Delete the current element
      list.clear(); // Clear the list
      inorder(); // Rebuild the list
    }
  }

  /** Remove all elements from the tree */
  public void clear() {
    root = null;
    size = 0;
  }

  public Object clone() {
    BinaryTree<E> tree1 = new BinaryTree<E>();

    copy(root, tree1);

    return tree1;
  }

  private void copy(TreeNode<E> root, BinaryTree<E> tree) {
    if (root != null) {
      tree.insert(root.element);
      copy(root.left, tree);
      copy(root.right, tree);
    }
  }


  /**Default height method
    *
    *Call height from the root node of the tree.
    */
  public int height() {
    System.out.println("#-- Finding the tree height from: " + getRoot().element);
    return height(getRoot());
  }


  /**Height method specifying a tree
    *
    *  Recursively find the maximum distance between the rootval
    *  and the trees.  Termination happens when all nodes are traversed,
    *  indicated by the state of a null node or a node with left and right
    *  null nodes.
    *
    *@param node - tree node to start with
    */
  public int height(TreeNode<E> node) {
      if (node == null) {
        return 0;
      }
      else if (node.left == null && node.right == null) {
        System.out.println("path terminated at: " + node.element);
        return 0;
      } else {
        String msg = "following path through ";
        if (node.left != null){
          msg = msg + node.left.element + ", " ;
        }

        if (node.right != null) {
          msg = msg + node.right.element + ", ";
        }
        System.out.println(msg);
        return java.lang.Math.max(height(node.left), height(node.right)) + 1;
      }
  }

  /**Default nonleaves method
    */
  public int getNumberOfNonLeaves() {
    System.out.println("#-- Finding the nonleaf nodes from: " + getRoot().element);
    return getNumberOfNonLeaves(getRoot());
  }

  /**Call getNumberOfNonLeaves function specifying a starting node.
    *
    * Recursively count the non-leaves in the subtree starting
    * at the specified node.  Nodes with null left and right pointers
    * do not count, as they are leaves, and terminate the recursion.
    * left and right paths that are not nulls are followed.
    *
    *@param node - tree node to start with
    */
  public int getNumberOfNonLeaves(TreeNode<E> node) {
    if (node.right == null && node.left == null) {
      System.out.println(node.element + " is a leaf.");
      return 0;
    } else {
      int cnt = 1;
      System.out.println(node.element + " is found to not be a leaf.");
      if (node.left != null) {
        cnt = cnt + getNumberOfNonLeaves(node.left);
      }

      if (node.right != null) {
        cnt = cnt + getNumberOfNonLeaves(node.right);
      }
      return cnt;
    }
  }

  /**Default getNumberOfLeaves method
    */
  public int getNumberOfLeaves() {
    System.out.println("#-- Finding the leaf nodes from: " + getRoot().element);
    return getNumberOfLeaves(getRoot());
  }

  /**Call getNumberOfLeaves function specifying a starting node.
    *
    *  Recursively find the leaves of the tree starting
    *  at the supplied node.  Recursion terminates, and the
    *  count increases, when the left and right null pointers
    *  are null: indicating a leaf has been found.
    *
    *@param node - tree node to start with
    */
  public int getNumberOfLeaves(TreeNode<E> node) {
    if (node.right == null && node.left == null) {
      System.out.println("Found " + node.element + " to be a leaf.");
      return 1;
    } else {
      int cnt = 0;
      if (node.left != null) {
        System.out.println("Found path to " + node.left.element);
        cnt = cnt + getNumberOfLeaves(node.left);
      }

      if (node.right != null) {
        System.out.println("Found path to " + node.right.element);
        cnt = cnt + getNumberOfLeaves(node.right);
      }

      return cnt;
    }

  }

}
