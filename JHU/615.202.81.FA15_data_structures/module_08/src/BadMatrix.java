/** Custom exception to catch invalid Matrix
  *
  */

/** Extends the <code>Error</code> class to cause a
  * runtime error and not a compilation error
  */
public class BadMatrix extends Error {
    public BadMatrix(String message) {
        super(message);
    }
}
