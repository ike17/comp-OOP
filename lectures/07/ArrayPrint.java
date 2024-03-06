// Examples of how to print arrays conveniently (e.g., for debugging)

import java.util.Arrays;

class ArrayPrint {
  public static void main(String[] args) {

    // One-dimensional array

    int[] data = { 1, 2, 3, 4, 5 };

    System.out.println("1D array:");
    System.out.println(data);
    System.out.println(Arrays.toString(data));

    // Two-dimensional array

    int[][] data2 = { { 1,  2,  3,  4,  5},
                      { 2,  4,  6,  8, 10},
                      { 1,  3,  5,  7,  9} };

    System.out.println();
    System.out.println("2D array:");
    System.out.println(data2);
    System.out.println(Arrays.toString(data2));
    System.out.println(Arrays.deepToString(data2));
  }
}
