// Example of using a while loop to sum numbers read from standard input

import java.util.Scanner;

public class Sum {
  public static void main(String[] args) {

    System.out.println("Enter numbers, or Ctrl+D to quit");

    double sum = 0.0;

    try (Scanner input = new Scanner(System.in)) {
      while (input.hasNextDouble()) {
        sum += input.nextDouble();
      }
    }

    System.out.printf("Sum = %f\n", sum);
  }
}
