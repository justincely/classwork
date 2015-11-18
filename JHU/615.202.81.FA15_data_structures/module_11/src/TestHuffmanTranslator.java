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

    @Test
    public void testDecoder() {
      HuffmanTranslator translator = new HuffmanTranslator();
      translator.buildEncoderTree();

      translator.decode("1111110100");
      translator.decode("01011001010110011111011011");
      translator.decode("10110000101010011011101101100010110010101100010111000110111");
      translator.decode("11111110001000111111101011111011001111111000100011111000001010000001110010111");
      translator.decode("1101101000010001111100011111101000000101100");
    }

    @Test
    public void testEncoder() {
      HuffmanTranslator translator = new HuffmanTranslator();
      translator.buildEncoderTree();
      translator.encode("EIEIOH");
    }

}
