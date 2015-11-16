public class Tree<T> {
    private T data;
    private Tree<T> parent = null;
    private Tree<T> right = null;
    private Tree<T> left = null;

    public Tree() {
    }

    public Tree(T value) {
        this.data = value;
    }

    public Tree(T value, Tree<T> rightChild, Tree<T> leftChild) {
      this.data = value;

      this.right = rightChild;
      rightChild.parent = this;

      this.left = leftChild;
      leftChild.parent = this;
    }

    public void inOrderTraverse() {
      if (this.left != null)
         this.left.inOrderTraverse();

      System.out.println(this.data);

      if (this.right != null)
         this.right.inOrderTraverse();
    }

    public void preOrderTraverse() {
      System.out.println(this.data);

      if (this.left != null)
         this.left.inOrderTraverse();

      if (this.right != null)
         this.right.inOrderTraverse();
    }

    public void postOrderTraverse() {
      if (this.left != null)
         this.left.inOrderTraverse();

      if (this.right != null)
         this.right.inOrderTraverse();

      System.out.println(this.data);
    }

}
