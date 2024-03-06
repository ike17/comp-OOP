// Examples of iterating over an array

class ArrayLoop {
  public static void main(String[] args) {

    double[] data = { 1.0, 2.5, 4.0, 5.5, 7.0 };

    // Traditional indexing for loop

    for (int i = 0; i < data.length; ++i) {
      System.out.println(data[i]);
    }

    System.out.println();

    // 'for each' loop
 
    for (double number: data) {
      System.out.println(number);
    }
  }
}
