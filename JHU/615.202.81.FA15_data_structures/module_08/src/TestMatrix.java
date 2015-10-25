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
     Matrix myMatrix = new Matrix(3);
     int[] data = {1, 1, 1, 2, 2, 2, 3, 3, 3};
     myMatrix.loadData(data);
     myMatrix.Print();

     Matrix smallMatrix = myMatrix.Minor(myMatrix.getData(), 0, 0);
     smallMatrix.Print();
   }

   @Test
   public void testPrint() {
     System.out.println("Testing the print method");
     Matrix myMatrix = new Matrix(6);
     myMatrix.Print();
   }

   @Test
   public void testLoad() {
     System.out.println("Testing the print method");
     Matrix myMatrix = new Matrix(3);
     int[] data = {1, 1, 1, 2, 2, 2, 3, 3, 3};
     myMatrix.loadData(data);
     myMatrix.Print();
   }

}
