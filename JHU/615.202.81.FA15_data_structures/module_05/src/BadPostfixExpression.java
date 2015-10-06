/** Custom exception to catch an invalid postfix expression
  *
  * Multiple errors can occur while attempting to process
  * expressions, such as I/O errors, sytax errors,
  * division errors.  In the case that the input
  * simply does not conform to the accepted postfix standars
  * for the assignment, a custom error to signify that
  * is particularly useful.
  */

/** Extends the <code>Error</code> class to cause a
  * runtime error and not a compilation error
  */
public class BadPostfixExpression extends Error {
    public BadPostfixExpression(String message) {
        super(message);
    }
}
