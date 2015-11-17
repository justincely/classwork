public class QueueNode {
  public String value = null;
  public int priority = 0;
  public QueueNode next = null;

  public QueueNode(String value, Integer priority){
      this.value = value;
      this.priority = priority;
  }

  public boolean isEnd() {
    if (this.next == null) {
      return true;
    }
    return false;
  }

}
