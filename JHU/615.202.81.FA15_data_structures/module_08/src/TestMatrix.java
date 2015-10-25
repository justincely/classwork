import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;

public class TestMatrix {
   @Test
   public void testMatrixCreation() {
      System.out.println("Testing the matrix instantiation");
      Matrix myMatrix = new Matrix(6);
      myMatrix = new Matrix(3);
      myMatrix = new Matrix();
   }

   @Test
   public void testMatrixMinor() {
     System.out.println("Testing the minor method");
     Matrix myMatrix = new Matrix(6);
     //int[][] m = myMatrix.minor(myMatrix, 0, 0);
   }

   @Test
   public void testPrint() {
     System.out.println("Testing the print method");
     Matrix myMatrix = new Matrix(6);
     myMatrix.Print();
   }


}
