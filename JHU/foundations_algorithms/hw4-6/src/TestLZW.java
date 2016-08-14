/**TestLZW
  *
  *@author Justin Ely
  */


import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import java.lang.IndexOutOfBoundsException;
import java.util.ArrayList;
import java.util.Arrays;

/**Test the Tree object
  *
  * <p>Tests are performed using the JUnit testing framework.
  *    A final output to STDOUT of true, indicates that all tests passed.
  *    Final output of false indicates a failing test.
  * </p>
  */
public class TestLZW {


    @Test
    public void testEncoder() {

      System.out.println("Encoding");
      System.out.println(LZW.compress("TOBEORNOTTOBE"));
    }

    @Test
    public void testDecoder() {
      ArrayList<Integer> compressed = new ArrayList<>(Arrays.asList(84, 79, 66, 69, 79, 82, 78, 79, 84, 256, 258, 260, 265, 259, 261, 263));

      System.out.println("Decoding");
      System.out.println(LZW.decompress(compressed));
    }

}
