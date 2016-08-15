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
public class TestEncryption {

    @Test
    public void testElGamalEncrypt() {
      ArrayList<String> words = new ArrayList<String>();
      words.add("Hello");
      words.add("World");

      //String encrypted = Encrption.ElGamalEncrypt(words, 5, 8, 1220703125, 89);
      //System.out.println(encrypted);

    }

    @Test
    public void TestRSAEncrypt() {
      ArrayList<String> words = new ArrayList<String>();
      words.add("Hello");
      words.add("World");

      Encryption.RSAEncrypt(words);
    }

    @Test
    public void TestRSADecrypt() throws Exception {
      ArrayList<String> words = new ArrayList<String>();
      words.add("[B@deb6432");
      words.add("[B@1b4fb997");

      Encryption.RSADecrypt(words, "privateKey");
    }
}
