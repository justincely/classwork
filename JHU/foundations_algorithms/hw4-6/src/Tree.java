/** A generic Binary Tree structure.
  *
  * <p> This is a linked tree structure, and supports generic typing.
  *     Each tree has data, a parent, and left and right children. Each node
  *     will have at max 2 children, which enforces the binary tree structure.
  * </p>
  *
  * @author Justin Ely
  * @version 0.0.1
  * @license BSD 3-clause
  */
public class Tree<T> {
    public T data;
    public Tree<T> parent = null;
    public Tree<T> right = null;
    public Tree<T> left = null;
    public int priority = 0;

    /**Default constructor
      *
      *<p> All attributes will be initialized to null </p>
      */
    public Tree() {
    }

    /**Constructor with value
      *
      *<p> data attribute is set to value, all others are unll </p>
      *@param T value
      */
    public Tree(T value) {
        this.data = value;
    }

    /**Constructor with value and priority.
      *
      *@param T value
      *@param int priority
      */
    public Tree(T value, int priority) {
        this.data = value;
        this.priority = priority;
    }

    /**Constructor with value, priority, and both childs
      *
      *<p>Children's parents will be set appropriately to the created node.</p>
      *@param T value
      *@param int priority
      *@param Tree rightChild
      *@param Tree leftChild
      */
    public Tree(T value, int priority, Tree<T> rightChild, Tree<T> leftChild) {
      this.data = value;
      this.priority = priority;

      this.right = rightChild;
      rightChild.parent = this;

      this.left = leftChild;
      leftChild.parent = this;
    }

    /**Check whether current node is a leaf
      *
      *<p>Tests whether node has no children.</p>
      *
      *@return boolean true/false
      */
    public boolean isLeaf() {
      if ((right == null) && (left == null)) {
        return true;
      }
      return false;
    }

    /**Check whether current node is the root
      *
      *<p>Tests whether the current node has no parent.</p>
      *@returns true/false
      */
    public boolean isRoot() {
      if (parent == null) {
        return true;
      }
      return false;
    }

    /**Perform an in-order traversal of all nodes
      *
      *<p>Visit left child recursively, then current node,
      *   then right child recursively.  Values will be printed
      *   to STDOUT.
      *</p>
      */
    public void inOrderTraverse() {
      if (this.left != null)
         this.left.inOrderTraverse();

      System.out.println(this.data + ": " + this.priority);

      if (this.right != null)
         this.right.inOrderTraverse();
    }

    /**Perform a pre-order traversal of all nodes
      *
      *<p>Visit current node first, then the left child recursively,
      *   followed by the right child recursively. Values will be printed
      *   to STDOUT.
      *</p>
      */
    public void preOrderTraverse() {
      System.out.println(this.data + ": " + this.priority);

      if (this.left != null)
         this.left.preOrderTraverse();

      if (this.right != null)
         this.right.preOrderTraverse();
    }

    /**Perform a post-order traversal of all nodes
      *
      *<p>Visit the left child recursively,
      *   followed by the right child recursively,
      *   lastly visit the current node. Values will be printed
      *   to STDOUT.
      *</p>
      */
    public void postOrderTraverse() {
      if (this.left != null)
         this.left.postOrderTraverse();

      if (this.right != null)
         this.right.postOrderTraverse();

      System.out.println(this.data + ": " + this.priority);
    }
}
