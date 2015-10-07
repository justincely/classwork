/**PostfixEval
  *
  *This program provides the interface and main translation
  *of given postfix expressions into machine instructions.
  *
  *Postfix expressions can be contained as separate lines in an
  *input file, or supplied directly by the command line.
  */


import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.util.EmptyStackException;


/**Evaluate input postfix expressions into machine language
  *
  */
public class PostfixEval{

  /**Main driver to parse and evaluate expressions
    *
    *@param args[]   Holds command line arguments: filenames or
    *                postfix strings.
    */
  public static void main(String[]args){
    boolean isFileInput = false;

    /* For each input argument, check if the supplied
     * input string corresponds to an accesible file.
     * If it doesn't, assume the input was an expression
     * to be evaluated and pass directly to the translator.
     */
    for (int i=0; i < args.length; i++) {
      isFileInput = new File(args[i]).exists();

      if (isFileInput){
        System.out.println("----------------");
        System.out.println("Given file name: " + args[i]);
        System.out.println("----------------");
        parseFromFile(args[i]);
      } else {
        System.out.println("----------------");
        System.out.println("Given expression: " + args[i]);
        System.out.println("----------------");
        try{
          translate(args[i]);
        } catch (BadPostfixExpression e) {
          System.err.println(e);
        }
      }
    System.out.println("--Finished parsing all expressions--");
    }
  }


  /**Parse expressions from a supplied file
    *
    *<p> Each line in the supplied file must contain a single
    *    postfix expression or whitespace.  Whitspace will be ignored,
    *    and each expression will be translated.  Output is printed
    *    to STDOUT.
    *</p>
    *
    *@param filename - name of file containing postfix expressions
    */
  public static void parseFromFile(String filename) {
    String line = null;
    int lineCounter = 0;

    try {
      BufferedReader br = new BufferedReader(new FileReader(filename));
      try {
        while ((line = br.readLine()) != null) {
          lineCounter++;
          System.out.println("--line " + lineCounter +
                             "--found Postfix expression: " + line);

          try {
            translate(line);
          } catch (BadPostfixExpression e){
            System.err.println("ERROR: Invalid expression encountered: exiting");
            System.err.println(e);
          }

          System.out.println("\n");
        }

        br.close();
      } catch (java.io.IOException e) {
        System.out.println("EOF encountered");
      }

    } catch (java.io.FileNotFoundException e) {
      System.out.println("Cannot find file: " + filename);
      return;
    }
  }


  /**Check if character is an acceptible operator
    *
    *@param character String - A single-element String
    *@return Boolean - True if in +,-,*,/,$ otherwise False
    */
  private static boolean isOperator(String character) {
		return (character.equals("+") ||
            character.equals("-") ||
            character.equals("*") ||
            character.equals("/") ||
            character.equals("$"));
	}

  /**Select the appropriate machine instruction give the operator
    *
    *@param operator String - A single-element String
    *@return operator String - the corresponding machine instruction
    */
  private static String selectCMD(String operator) {
    //###Should throw an exeption instead of BadOperator
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

  /**Postfix to machine translator
    *
    *<p>input expression must be a single post-fix expression
    *   containing operators (+,-,*,/,$) and alphabetics (A,B,C,etc).
    *   Whitespace is ignored, integers and any other non-operand,
    *   non-alphabetic character is considered an error, including
    *   parentheticals and brackets.
    *</p>
    *@param expression - the postfix expression to be translated
    *@throws BadPostfixExpression - if an invalid or unsupported expression
    *                   is input.
    */
  public static void translate(String expression)
    throws BadPostfixExpression {

    Stack variables = new Stack();
    int tempNum = 1;
    String arg1;
    String arg2;
    String op;
    String command;

    System.out.println("--Translating: " + expression + " into machine code.");

    if (expression.trim().length() == 0) {
      throw new BadPostfixExpression("Operator hit with empty stack");
    }


    for (int i=0; i<expression.length(); i++){
      if (isOperator(expression.substring(i, i+1))){
        if (variables.isEmpty()) {
          throw new BadPostfixExpression("Operator encountered with an empty stack");
        }

        command = selectCMD(expression.substring(i, i+1));

        try {
          arg1 = variables.pop();
        } catch (EmptyStackException e) {
          throw new BadPostfixExpression("Empty stack on first arg");
        }

        try {
          arg2 = variables.pop();
        } catch (EmptyStackException e) {
          throw new BadPostfixExpression("Empty stack on second arg");
        }

        variables.push("TEMP" + tempNum);

        /* Sytax for machine language is always:
         * load arguement 2
         * operate on arg2 and arg1
         * store result
         */
        System.out.println("LD " + arg2);
        System.out.println(command + " " + arg1);
        System.out.println("ST " + "TEMP" + tempNum);
        tempNum++;

        //System.out.println(command + " " + arg1 + " " + arg2);
      } else if (Character.isLetter(expression.charAt(i))) {
        variables.push(expression.substring(i, i+1));
      } else if (Character.isWhitespace(expression.charAt(i))) {
        continue;
      } else {
        throw new BadPostfixExpression("Unrecognized charactor: " +
                                                expression.charAt(i) +
                                                " at position: " + i);
      }
    }

    //Deal with left-over variabls that haven't been loaded into memory,
    //indicating an invalid expression
    if (variables.isEmpty() == false) {
      while (variables.isEmpty() == false) {
        if (variables.pop().startsWith("TEMP") == false){
          throw new BadPostfixExpression("Leftover variables after evaluation.");
        }
      }
    }

  }
}
