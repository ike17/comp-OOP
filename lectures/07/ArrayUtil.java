// Examples of using the array utilities in java.util.Arrays

import java.util.Arrays;

class ArrayUtil {
  public static void main(String[] args) {

    int[] data = { 1, 7, -2, 0, 12, 5, 1, 9 };

    // One-liner to print a small array (useful for debugging, etc)
 
    System.out.println(Arrays.toString(data));

    // Copying and sorting an array

    int[] dataCopy = Arrays.copyOf(data, data.length);
    Arrays.sort(dataCopy);
    System.out.println(Arrays.toString(dataCopy));

    // Searching a sorted array for a value

    int pos = Arrays.binarySearch(dataCopy, 0);
    System.out.printf("0 found at index %d\n", pos);

    pos = Arrays.binarySearch(dataCopy, 9);
    System.out.printf("9 found at index %d\n", pos);

    // Filling an array with a value

    Arrays.fill(dataCopy, 1);
    System.out.println(Arrays.toString(dataCopy));
  }
}
