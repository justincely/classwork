/**TestMatrix
  *
  *@author Justin Ely
  */


import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import java.lang.IndexOutOfBoundsException;

/**Test the Matrix object
  *
  * <p>Tests are performed using the JUnit testing framework.
  *    A final output to STDOUT of true, indicates that all tests passed.
  *    Final output of false indicates a failing test.
  * </p>
  */
public class TestMatrix {

   @Test
   public void testMatrixCreation() {
      Matrix myMatrix = new Matrix(6);
      myMatrix = new Matrix(3);
      myMatrix = new Matrix();
   }
   
   @Test(expected=BadMatrix.class)
   public void testMatrixException(){
     Matrix myMatrix = new Matrix(-6);
     myMatrix.Print();
   }

   @Test
   public void testDeterminate(){
     int[] data = {1, 1, 1, 1, 1, 1, 1, 1, 1};
     Matrix myMatrix = new Matrix(3, data);
     long det = Matrix.Determinate(myMatrix);
     assertEquals(det, 0);

     data = new int[] {2, 3, 5, 9};
     myMatrix = new Matrix(2, data);
     det = Matrix.Determinate(myMatrix);
     assertEquals(det, 3);

     data = new int[6];
     myMatrix = new Matrix(6, data);
     det = Matrix.Determinate(myMatrix);
     assertEquals(det, 0);

     data = new int[1];
     data[0] = 4;
     myMatrix = new Matrix(1, data);
     det = Matrix.Determinate(myMatrix);
     assertEquals(det, 4);
   }

   @Test
   public void testMatrixMinor() {
     int[] data = {1, 1, 1, 2, 2, 2, 3, 3, 3};

     Matrix myMatrix = new Matrix(3, data);

     Matrix smallMatrix = myMatrix.Minor(0, 0);
     int[][] checkData = new int[][] {{2, 3}, {2, 3}};
     assertEquals(smallMatrix.getData(), checkData);


     smallMatrix = myMatrix.Minor(1, 1);
     checkData = new int[][] {{1, 3}, {1, 3}};
     assertEquals(smallMatrix.getData(), checkData);
   }

   @Test(expected = IndexOutOfBoundsException.class)
   public void testMatrixMinorError() {
     int[] data = new int[] {1};
     Matrix myMatrix = new Matrix(1, data);
     Matrix smallMatrix = myMatrix.Minor(1, 2);
   }

   @Test
   public void testPrint() {
     Matrix myMatrix = new Matrix(6);
     myMatrix.Print();
   }

   @Test
   public void testLoad() {
     Matrix myMatrix = new Matrix(3);
     int[] data = {1, 1, 1, 2, 2, 2, 3, 3, 3};
     try{
       myMatrix.loadData(data);
     } catch (Exception e){
       org.junit.Assert.fail();
     }
   }

}
