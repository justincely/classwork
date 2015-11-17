public class QueueNode {
  public String value = null;
  public int priority = 0;
  public QueueNode next = null;
  public Tree tree = null;

  public QueueNode(){
  }

  public QueueNode(String value, Integer priority){
      this.value = value;
      this.priority = priority;
  }

  public QueueNode(String value, Integer priority, Tree tree) {
    this.value = value;
    this.priority = priority;
    this.tree = tree;
  }

  public boolean isEnd() {
    if (this.next == null) {
      return true;
    }
    return false;
  }

}
