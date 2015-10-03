public class PostfixEval{

  public static void main(String[]args){
    for (int i = 0; i < args.length; i++) {
      System.out.println(args[i]);
    }

    String testing_string = "ABC*+DE-/";

    translate(testing_string);
  }


  /**Not implemented yet
    */
  //public static String parseFromFile(String filename) {
  //
  //}

  private static boolean isOperator(String character) {
		return (character.equals("+") || character.equals("-") || character.equals("*") || character.equals("/"));
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
    } else {
      return "BadOperator";
    }

  }

  public static void translate(String expression) {
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
        //Check for empty stack

        command = selectCMD(expression.substring(i, i+1));
        arg1 = variables.pop();
        arg2 = variables.pop();
        variables.push("TEMP" + tempNum);

        System.out.println("LD " + arg2);
        System.out.println(command + " " + arg1);
        System.out.println("ST " + "TEMP" + tempNum);

        tempNum++;
        //System.out.println(command + " " + arg1 + " " + arg2);
      } else {
        variables.push(expression.substring(i, i+1));
      }


    }

  }


}
