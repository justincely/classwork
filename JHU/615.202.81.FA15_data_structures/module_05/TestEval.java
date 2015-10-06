import java.util.EmptyStackException;
import java.nio.BufferOverflowException;

public class TestEval{

  public static void main(String[]args){
    System.out.println("Testing the evaluator");
    String[] valid = new String[] {"AB+CG*I/",
                                   "AB+",
                                   "A B + ",
                                   " BB*C$     ",
                                   "ZBFDS++++"};

   String[] inValid = new String[] {"A",
                                    "AB",
                                    "   ",
                                    "*",
                                    "+-*",
                                    "(AB+)"};

    //Run the scenario given in the problem
    givenScenario();

    for (int i=0; i<valid.length; i++){
      assert checkValidExpression(valid[i]): i + "th expression didn't evaluate";
    }

    for (int i=0; i<inValid.length; i++){
      assert checkValidExpression(inValid[i]) == false:
                                              i + "th expression didn't fail";
    }
  }


  /**Just the given one
    *
    */
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
    System.out.println("\n");
  }

  /**Check if expression is valid
    *
    */
  public static boolean checkValidExpression(String postfix) {
    try{
      PostfixEval.translate(postfix);
    } catch (BadPostfixExpression e) {
      return false;
    }

    return true;
  }

}
