
/** A specialized Queue for use in huffman encoding
  *
  * <p> This is a linked minimum queue.
  * </p>
  *
  * @author Justin Ely
  * @version 0.0.1
  * @license BSD 3-clause
  */
public class MinQueue {
  public int count = 0;
  public QueueNode top = null;

  /**Default constructor
    *
    *<p> top will be initialized to null, count to 0</p>
    */
  public MinQueue(){
  }

  /**Test if the queue is empty
    *
    *<p> test emptiness by checking if top == null </p>
    *@returns true/false
    */
  public boolean isEmpty(){
    if (top == null) {
      return true;
    }
    return false;
  }

  /**Insert a new node into queue
    *
    *<p></p>
    *
    *@param String element
    *@param int priority
    *@param Tree tree
    */
  public void insert(String element, int priority, Tree tree){
    QueueNode node = new QueueNode(element, priority, tree);
    count++;

    if (isEmpty()){
      top = node;
    } else if (goesBefore(node, top)) {
      node.next = top;
      top = node;
    } else {
      QueueNode current = top.next;
      QueueNode last = top;

      while ((current != null) && (goesBefore(node, current) == false)) {
        last = current;
        current = current.next;
      }

      last.next = node;
      node.next = current;
    }
  }

  /**Enforce rules for precedence in queue
    *
    *@param QueueNode a
    *@param Queuenode b
    *@returns boolean true/false
    */
  private boolean goesBefore(QueueNode a, QueueNode b) {
    if (a.priority < b.priority) {
      return true;
    } else if (a.priority == b.priority) {
      if (a.value.length() < b.value.length()) {
        return true;
      } else if ((a.value.length() == b.value.length()) && (a.value.compareTo(b.value) < 0)){
        return true;
      }
    }
    return false;
  }

  /**Return top element with lowest priority
    *
    *returns QueueNode top
    */
  public QueueNode pop() {
    QueueNode tmp = top;
    top = tmp.next;
    count--;
    return tmp;
  }

  /**Convenience function for printing the content of the queue
    */
  public void print() {
    QueueNode current = top;
    while (current.next != null) {
      System.out.println(current.value + ": " + current.priority);
      current = current.next;
    }
    System.out.println(current.value + ": " + current.priority);
  }

}
