public class Matrix{
  private int[][] data;

  public Matrix(int order, int[] values){
    data = new int[order][order];
    loadData(values);
    System.out.println("Matrix of order " + data.length + " initialized.");
  }

  public Matrix(int order){
    data = new int[order][order];
    System.out.println("Matrix of order " + data.length + " initialized to 0.");
  }

  public Matrix(){
    this(1);
  }

  public static int Determinate(Matrix mat){
    int[][] subData = mat.getData();
    if (subData.length == 1){
      return subData[0][0];
    } else {
      int sum = 0;
      int y = 0;
      for (int x=0; x<subData.length; x++){
        Matrix minorMatrix = mat.Minor(x, y);
        sum = sum + ((int) Math.pow(-1, x+y) * subData[x][y] * Determinate(minorMatrix));
      }
      return sum;
    }
  }

  public void Print(){
    int size = data.length;
    for (int y=0; y<size; y++){
      for (int x=0; x<size; x++){
        System.out.print(" " + data[x][y] + " ");
      }
      System.out.print("\n");
    }
  }

  public int[][] getData(){
    return data;
  }

  public void loadData(int[] values){
    // needs error checking
    int size = data.length;
    for (int i=0; i<values.length; i++){
      data[i%size][i/size] = values[i];
    }
  }

  public Matrix Minor(int i, int j){
    int insize = data.length;
    int outsize = insize - 1;
    int[] out_data = new int[outsize*outsize];
    System.out.println("Finding the minor of matrix");
    //FIX
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
