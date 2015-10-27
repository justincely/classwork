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
   public void testDeterminate(){
     System.out.println("Testing the determinate");
     int[] data = {1, 1, 1, 1, 1, 1, 1, 1, 1};
     Matrix myMatrix = new Matrix(3, data);

     int det = Matrix.Determinate(myMatrix);
     System.out.println("The determinate is " + det);


     System.out.println("Testing the determinate");
     data = new int[] {2, 3, 5, 9};
     myMatrix = new Matrix(2, data);

     det = Matrix.Determinate(myMatrix);
     System.out.println("The determinate is " + det);
   }

   @Test
   public void testMatrixMinor() {
     System.out.println("Testing the minor method");
     int[] data = {1, 1, 1, 2, 2, 2, 3, 3, 3};

     Matrix myMatrix = new Matrix(3, data);
     myMatrix.Print();

     Matrix smallMatrix = myMatrix.Minor(0, 0);
     //smallMatrix.Print();
     int[][] checkData = new int[][] {{2, 3}, {2, 3}};
     assertEquals(smallMatrix.getData(), checkData);


     smallMatrix = myMatrix.Minor(1, 1);
     //smallMatrix.Print()
     checkData = new int[][] {{1, 3}, {1, 3}};
     assertEquals(smallMatrix.getData(), checkData);


     data = new int[] {1};
     myMatrix = new Matrix(1, data);
     smallMatrix = myMatrix.Minor(0, 0);
     checkData = new int[][] {{1}};
     smallMatrix.Print();
     //assertEquals(smallMatrix.getData(), checkData);
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
