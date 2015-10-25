public class Matrix{
  private int[][] data;

  public Matrix(int order){
    data = new int[order][order];
    System.out.println("Matrix of order " + data.length + " initialized.");
  }

  public Matrix(){
    this(1);
  }

  public void Determinate(){

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

  public int[][] Minor(int[][] a, int i, int j){
    int insize = a.length;
    int outsize = insize--;
    int[][] out = new int[outsize][outsize];

    for (int x=0; x<insize; x++){
      for (int y=0; y<insize; y++){
        if ((x == i) || (y == j)){
          continue;
        } else {
          out[i][j] = a[i][j];
        }
      }
    }
    return out;
  }

}
