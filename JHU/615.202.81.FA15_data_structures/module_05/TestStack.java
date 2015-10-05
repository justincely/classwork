import java.util.EmptyStackException;
import java.nio.BufferOverflowException;

public class TestStack{

  public static void main(String[]args){
    System.out.println("Testing the stack functionality");

    coreFunctionality();

    peekErrorHandling();
    popErrorHandling();
    pushErrorHandling();
  }

  public static void coreFunctionality(){
    Stack tester = new Stack();
    String[] alpha = new String[]{"a", "b", "c", "d", "e", "f", "g"};
    int length = 7;

    assert tester.isEmpty() : "New stack doesn't appear empty";
    assert tester.size() == 0 : "New stack size isn't 0";

    System.out.println("Running push tests");
    for (int i=0; i<7; i++){
      tester.push(alpha[i]);
      assert tester.size() == i+1: "Size mismatch on iter: " + (i+1);
      System.out.println(tester.peek());
      //assert tester.push(alpha[i]) : i + "th push failed";
    }

    System.out.println("Running pop tests");
    int i = length - 1;
    while (tester.isEmpty() == false){
      System.out.println(tester.peek() + " == " + alpha[i]);
      assert tester.size() == i+1: "Size mismatch on iter: " + (i);
      assert alpha[i] == tester.pop() : i + "ith pop didn't match";

      i--;
    }

    System.out.println("Running final empty tests");
    assert tester.isEmpty() : "Final stack doesn't appear empty";
    assert tester.size() == 0 : "Final stack size isn't 0";
  }


  public static void peekErrorHandling(){
    Stack tester = new Stack();
    boolean thrownCorrect = false;

    try {
      tester.peek();
    }
      catch (EmptyStackException e) {
        thrownCorrect = true;
    }

    assert thrownCorrect : "Peek doesn't throw right";
  }


  public static void popErrorHandling(){
    Stack tester = new Stack();
    boolean thrownCorrect = false;

    try {
      tester.pop();
    }
      catch (EmptyStackException e) {
        thrownCorrect = true;
    }

    assert thrownCorrect : "Pop doesn't throw right";
  }

  public static void pushErrorHandling(){
    Stack tester = new Stack();
    boolean thrownCorrect = false;

    try {
      for (int i=0; i<500; i++){
        tester.push("a");
      }
    }
      catch (BufferOverflowException e) {
        thrownCorrect = true;
    }

    assert thrownCorrect : "Peek doesn't throw right";
  }
}
