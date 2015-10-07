/**TestEval
  *
  *@author Justin Ely
  */

import java.util.EmptyStackException;
import java.nio.BufferOverflowException;

/**Test the expression translator
  *
  * <p>This module provides pseudo-unit tests
  *    against the postfix translator. All core functions
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
public class TestEval{

  /**Main test driver
    *
    *@param args[]   Holds command line arguments: Not used
    */
  public static void main(String[]args){
    System.out.println("###################################");
    System.out.println("# Testing the Postfix Evaluator   #");
    System.out.println("#                                 #");
    System.out.println("# STDOUT is OK, Errors will be    #");
    System.out.println("# raised for failing tests        #");
    System.out.println("###################################");

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
                                    "(AB+)",
                                    "12+",
                                    "!",
                                    "{}",
                                    ""};

    //Run the scenario given in the problem explanation
    givenScenario();

    //Check a sample of valid expressions
    System.out.println("#########################################");
    System.out.println("# The following expressions should pass #");
    System.out.println("#########################################");
    for (int i=0; i<valid.length; i++){
      assert checkValidExpression(valid[i]): i + "th expression didn't evaluate";
    }

    //Check a sample of invalid expressions
    System.out.println("#########################################");
    System.out.println("# The following expressions should fail #");
    System.out.println("#########################################");
    for (int i=0; i<inValid.length; i++){
      assert checkValidExpression(inValid[i]) == false:
                                              i + "th expression didn't fail";
    }


    System.out.println("####################################");
    System.out.println("# Finished Postfix Evaluator tests #");
    System.out.println("####################################");
  }


  /**Visual test against case given in problem
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

  /**Check if expression is valid by attempting to translate.
    *
    *@param postfix string expression to evaluate
    *@return <code>true</code> if no error thrown by
    *         <code>translate</code> function
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
