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
      Result result = JUnitCore.runClasses(TestMultiStack.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }

      if (result.wasSuccessful() == true) {
        System.out.println("All Unittests passed");
      } else {
        System.out.println("#######################################");
        System.out.println("#         Unittest suite failed       #");
        System.out.println("#######################################");
      }

  }
}
