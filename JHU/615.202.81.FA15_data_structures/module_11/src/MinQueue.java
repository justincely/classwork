public class MinQueue {
  public int count = 0;
  public QueueNode head = null;
  public QueueNode tail = null;

  public MinQueue(){
  }

  public isEmpty(){
    if (head == null) && (tail == null){
      return true;
    }
    return false;
  }

  public void enqueue(String element, int priority){
    QueueNode node = new QueueNode(element, priority);

    if (isEmpty()){
      head = node
    }

  }

}
