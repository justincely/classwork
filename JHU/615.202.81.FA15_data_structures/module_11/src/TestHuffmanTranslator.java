/**TestTree
  *
  *@author Justin Ely
  */


import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import java.lang.IndexOutOfBoundsException;

/**Test the Tree object
  *
  * <p>Tests are performed using the JUnit testing framework.
  *    A final output to STDOUT of true, indicates that all tests passed.
  *    Final output of false indicates a failing test.
  * </p>
  */
public class TestHuffmanTranslator {

    @Test
    public void testDefaultCreation() {
      HuffmanTranslator translator = new HuffmanTranslator();
      translator.printFrequencies();
    }

    @Test
    public void testTreeCreation() {
      HuffmanTranslator translator = new HuffmanTranslator();
      translator.buildEncoderTree();
    }

}
