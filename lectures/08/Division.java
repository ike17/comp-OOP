// Example of code that can trigger an ArithmeticException

import java.util.Scanner;
import static java.lang.System.out;

class Division {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    out.print("Enter numerator: ");
    int num = input.nextInt();

    out.print("Enter denominator: ");
    int denom = input.nextInt();

    int quotient = num / denom;    // possible division by zero here
    int remainder = num % denom;

    out.printf("Quotient = %d\n", quotient);
    out.printf("Remainder = %d\n", remainder);
  }
}
