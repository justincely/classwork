public class QueueNode<T>{
  public T value = null;
  public int priority = 0;
  public next = null;
  public previous = null;

  public QueueNode(T value, Integer priority){
      this.value = value;
      this.priority = priority;
  }


}
