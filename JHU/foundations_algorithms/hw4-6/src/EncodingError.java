/** Custom exception to catch invalid huffman encoding
  *
  */

/** Extends the <code>Error</code> class to cause a
  * runtime error and not a compilation error
  */
public class EncodingError extends Error {
    public EncodingError(String message) {
        super(message);
    }
}
