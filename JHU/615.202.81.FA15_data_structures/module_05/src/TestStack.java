/**TestStack
  *
  *@author Justin Ely
  */

import java.util.EmptyStackException;
import java.nio.BufferOverflowException;

/**Test the stack interface
  *
  * <p>This module provides pseudo-unit tests
  *    against the stack class. All core functions
  *    are excercised to test
  *    their stated function, along with the cases
  *    and errors that should be thrown.
  * </p>
  * <p>Tests are performed with the <code>assert</code>
  *    statement, such that a failing test will halt the
  *    execuation at the failing statement.  This behavior
  *    is deliberate, as a failure is critical and should not
  *    be missed amongst the standard output
  * </p>
  */
public class TestStack{

  /**Main test driver
    *
    *@param args[]   Holds command line arguments: Not used
    */
  public static void main(String[]args){
    System.out.println("###################################");
    System.out.println("# Testing the stack functionality #");
    System.out.println("#                                 #");
    System.out.println("# STDOUT is OK, Errors will be    #");
    System.out.println("# raised for failing tests        #");
    System.out.println("###################################");

    //Test push, pop, peek, isEmpty, size
    coreFunctionality();

    //Test the failure cases
    peekErrorHandling();
    popErrorHandling();
    pushErrorHandling();

    System.out.println("###################################");
    System.out.println("#     Finished Stack Testing      #");
    System.out.println("###################################");
  }

  /**Test the core functions of the stack
    *
    *<p>A list of strings will be sequentially pushed
    *   onto the stack, and subsequently peeked and popped
    *   off.  Before and after, the size and empty state will
    *   be checked.
    *</p>
    *<p>Testing is done through <code>assert</code> statements,
    *   with additional output to visualize testing.  As such,
    *   output does not indicate a positive or negative test,
    *   but execution is a positive test and halting execution
    *   is a failure.
    *</p>
    */
  public static void coreFunctionality(){
    Stack tester = new Stack();
    String[] alpha = new String[]{"a", "b", "c", "d", "e", "f", "g"};
    int length = 7;

    assert tester.isEmpty() : "New stack doesn't appear empty";
    assert tester.size() == 0 : "New stack size isn't 0";
    System.out.println("Testing empty() and size(): ok");

    System.out.println("Running push tests");
    for (int i=0; i<7; i++){
      tester.push(alpha[i]);
      assert tester.size() == i+1: "Size mismatch on iter: " + (i+1);
      System.out.println(tester.peek() + " push ok");
    }

    System.out.println("Running pop tests");
    int i = length - 1;
    while (tester.isEmpty() == false){
      System.out.println(tester.peek() + " == " + alpha[i]);
      assert tester.size() == i+1: "Size mismatch on iter: " + (i);
      assert alpha[i] == tester.pop() : i + "ith pop didn't match";
      i--;
    }

    assert tester.isEmpty() : "Final stack doesn't appear empty";
    assert tester.size() == 0 : "Final stack size isn't 0";
    System.out.println("Running final empty tests: ok");
  }


  /**Verify peek function error
    */
  public static void peekErrorHandling(){
    Stack tester = new Stack();
    boolean thrownCorrect = false;

    try {
      tester.peek();
    } catch (EmptyStackException e) {
      thrownCorrect = true;
    }

    assert thrownCorrect : "Peek doesn't throw right";
  }

  /**Verify pop function error
    */
  public static void popErrorHandling(){
    Stack tester = new Stack();
    boolean thrownCorrect = false;

    try {
      tester.pop();
    } catch (EmptyStackException e) {
      thrownCorrect = true;
    }

    assert thrownCorrect : "Pop doesn't throw right";
  }

  /**Verify push function error
    */
  public static void pushErrorHandling(){
    Stack tester = new Stack();
    boolean thrownCorrect = false;

    try {
      for (int i=0; i<500; i++){
        tester.push("a");
      }
    } catch (BufferOverflowException e) {
      thrownCorrect = true;
    }

    assert thrownCorrect : "Peek doesn't throw right";
  }
}
