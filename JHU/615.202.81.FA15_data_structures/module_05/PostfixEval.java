import java.io.FileReader;
import java.io.BufferedReader;
import java.lang.UnsupportedOperationException;
import java.util.EmptyStackException;

public class PostfixEval{

  public static void main(String[]args){
    for (int i=0; i < args.length; i++) {
      parseFromFile(args[i]);
    }

    //String testing_string = "ABC*+DE-/";

    //translate(testing_string);
  }


  /**Not implemented yet
    */
  public static void parseFromFile(String filename) {
    System.out.println("----------------");
    System.out.println("Given file name: " + filename);
    System.out.println("----------------");

    try{
      BufferedReader br = new BufferedReader(new FileReader(filename));

      try{
        String line = null;
        while ((line = br.readLine()) != null) {
          System.out.println("  Found Postfix expression: " + line);
          System.out.println("  Translating into:");
          try {
            translate(line);
            System.out.println("\n");
          } catch (UnsupportedOperationException e){
            System.err.println("ERROR: Invalid Expression encountered: exiting \n");
          }
        }

        br.close();
      } catch (java.io.IOException e) {
        System.out.println("EOF");
      }

    } catch (java.io.FileNotFoundException e) {
      System.out.println("cannot find file: " + filename);
      return;
    }

  }

  private static boolean isOperator(String character) {
		return (character.equals("+") ||
            character.equals("-") ||
            character.equals("*") ||
            character.equals("/") ||
            character.equals("$"));
	}

  private static String selectCMD(String operator) {

    if (operator.equals("+")) {
      return "AD";
    } else if (operator.equals("-")) {
      return "SB";
    } else if (operator.equals("*")) {
      return "ML";
    } else if (operator.equals("/")) {
      return "DV";
    } else if (operator.equals("$")) {
      return "PW";
    } else {
      return "BadOperator";
    }

  }

  public static void translate(String expression)
    throws UnsupportedOperationException  {

    System.out.println("Input Expression: " + expression);

    Stack variables = new Stack();
    Stack memory = new Stack();
    Stack register;
    int tempNum = 1;
    String arg1;
    String arg2;
    String op;
    String command;

    for (int i=0; i<expression.length(); i++){
      if (isOperator(expression.substring(i, i+1))){
        if (variables.isEmpty()) {
          System.out.println("Empty Stack!");
          throw new UnsupportedOperationException();
        }

        //Check for non-alpha characters - treat as error in expression
        command = selectCMD(expression.substring(i, i+1));

        try {
          arg1 = variables.pop();
        } catch (EmptyStackException e) {
          System.err.println("Empty stack encountered");
          throw new UnsupportedOperationException();
        }

        try {
          arg2 = variables.pop();
        } catch (EmptyStackException e) {
          System.err.println("Empty stack encountered");
          throw new UnsupportedOperationException();
        }

        variables.push("TEMP" + tempNum);

        System.out.println("LD " + arg2);
        System.out.println(command + " " + arg1);
        System.out.println("ST " + "TEMP" + tempNum);

        tempNum++;
        //System.out.println(command + " " + arg1 + " " + arg2);
      } else if (Character.isLetter(expression.charAt(i))) {
        variables.push(expression.substring(i, i+1));
      } else {
        throw new UnsupportedOperationException();
      }


    }

  }


}
