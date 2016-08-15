/**TestRunner
  *
  * Run all unittests
  */
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**Run all tests in the TestTree and TestHuffmanTranslator classes
  */
public class TestRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(TestTree.class,
                                           TestHuffmanTranslator.class,
                                           TestLZW.class,
                                           TestEncryption.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println(result.wasSuccessful());
   }
}
