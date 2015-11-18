/** Custom exception to catch invalid huffman decoding
  *
  */

/** Extends the <code>Error</code> class to cause a
  * runtime error and not a compilation error
  */
public class BadDecoding extends Error {
    public BadDecoding(String message) {
        super(message);
    }
}
