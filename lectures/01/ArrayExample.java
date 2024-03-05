// Example of run-time bounds checking for array access
// (Compare with ArrayExample.c)

class ArrayExample {
  public static void main(String[] args) {

    int[] x = new int[10];

    for (int i = 0; i < 10000; ++i) {
      System.out.printf("%5d: %d%n", i, x[i]);
    }
  }
}
