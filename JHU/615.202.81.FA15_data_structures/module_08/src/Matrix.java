import java.lang.Exception;

/** A Matrix of <code>int</code> precision.
  *
  * <p>The matrix is implemented as an array
  *     with an arbitrary maximum size.
  * </p>
  *
  * @author Justin Ely
  * @version 0.0.1
  * @license BSD 3-clause
  */
public class Matrix{
  // Holds the 2d matrix in a 2d array
  private int[][] data;

  /**Construct matrix with order and data arguments
    *
    *
    *@param int order - the order n of the nxn matrix
    *@param int[] values - a 1D array of input values (flattened from 2d array)
    */
  public Matrix(int order, int[] values){
    data = new int[order][order];
    try{
      loadData(values);
    } catch (Exception e){
      System.err.println(e);
    }
  }

  /**Construct matrix with just order argument
    *
    *<p> Data will be initialized to 0 according to java
    *    language specifications.
    *</p>
    *
    *@param int order - the order n of the nxn matrix
    */
  public Matrix(int order){
    data = new int[order][order];
  }

  /**Construct default matrix
    *
    *<p> Matrix of order 1 will be initialized to 0 according to java
    *    language specifications.
    *</p>
    */
  public Matrix(){
    this(1);
  }

  /**Calculate Determinate of a matrix
    *
    *@param Matrix mat - matrix object on which to calculate determinate.
    */
  public static long Determinate(Matrix mat){
    int[][] subData = mat.getData();
    if (subData.length == 1){
      return subData[0][0];
    } else {
      long sum = 0;
      int y = 0;
      for (int x=0; x<subData.length; x++){
        Matrix minorMatrix = mat.Minor(x, y);
        sum = sum + ((long) Math.pow(-1, x+y) * (long) subData[x][y] * Determinate(minorMatrix));
      }

    return sum;
    }
  }

  /**Print matrix content to STDOUT
    *
    *<p> Convenience function to display matrix contents
    *    To the screen.  Will iterate over array indeces and
    *    display each row on a new line.  Columns will be separated
    *    by spaces.  Termination will be in a newline character.
    *</p>
    */
  public void Print(){
    int size = data.length;
    for (int y=0; y<size; y++){
      for (int x=0; x<size; x++){
        System.out.print(" " + data[x][y] + " ");
      }
      System.out.print("\n");
    }
  }

  /** Fetch core 2d data array
    *
    *@return int[][] data - the core data of the matrix
    */
  public int[][] getData(){
    return data;
  }


  /** Load values into the matrix data structure
    *
    *<p> Load data values into the matrix.  Input data should be the
    *    same size as the matrix, but in 1D form represented as concatenated
    *    rows.
    *</p>
    *
    *@param int[] values - the 1d representation of the 2d input array.
    */
  public void loadData(int[] values) throws Exception{
    // needs error checking
    int size = data.length;
    int inSize = values.length;

    if (size*size > inSize){
      System.out.println("WARNING: not enough values provided, padding with 0");
    } else if (size*size < inSize){
      throw new Exception("Too many values provided to matrix");
    } else{
      for (int i=0; i<values.length; i++){
        data[i%size][i/size] = values[i];
      }
    }
  }

  /** Pull out the Minor of the matrix
    *
    *@param int i - row of the Minor
    *@param int j - column of the Minor
    *@return Matrix - minor matrix of order n-1
    */
  public Matrix Minor(int i, int j){
    int insize = data.length;
    int outsize = insize - 1;
    int[] out_data = new int[outsize*outsize];

    for (int x=0; x<insize; x++){
      for (int y=0; y<insize; y++){
        if ((x == i) || (y == j)){
          continue;
        }

        int tmp_x = x;
        int tmp_y = y;

        if (x > i){
          tmp_x--;
        }
        if (y > j){
          tmp_y--;
        }

        out_data[tmp_x + tmp_y*outsize] = data[x][y];
      }
    }

    return new Matrix(outsize, out_data);
  }

}
