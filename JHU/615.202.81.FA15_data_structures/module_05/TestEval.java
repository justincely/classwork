import java.util.EmptyStackException;
import java.nio.BufferOverflowException;

public class TestEval{

  public static void main(String[]args){
    System.out.println("Testing the evaluator");

    //Run the scenario given in the problem
    givenScenario();

  }

  public static void givenScenario(){
    String testing_string = "ABC*+DE-/";
    PostfixEval.translate(testing_string);
    System.out.println("\nThe above string should match the following:");
    System.out.println("LD B");
    System.out.println("ML C");
    System.out.println("ST TEMP1");
    System.out.println("LD A");
    System.out.println("AD TEMP1");
    System.out.println("ST TEMP2");
    System.out.println("LD D");
    System.out.println("SB E");
    System.out.println("ST TEMP3");
    System.out.println("LD TEMP2");
    System.out.println("DV TEMP3");
    System.out.println("ST TEMP4");
  }

  public static boolean checkValidExpressions(String postfix) {

    try{
      PostfixEval.translate(postfix);
    } catch (Exception e) {
      return false;
    }

    return true;
  }

}
