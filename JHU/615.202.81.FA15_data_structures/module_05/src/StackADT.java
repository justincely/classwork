/** Interface for the Stack class
  *
  * @author Justin Ely
  * @version 0.0.1
  * @license BSD 3-clause
  */
public abstract interface StackADT{

  public boolean isEmpty();

  public void push(String value);

  public String pop();

  public String peek();

}
