// Example of using Scanner to parse standard input
// (using the more correct 'try with resources' approach)

import java.util.Scanner;

class Input2 {
  public static void main(String[] args) {
    try (Scanner input = new Scanner(System.in)) {

      System.out.print("Enter an integer value: ");
      int n = input.nextInt();

      System.out.println("n = " + n);
    }
  }
}
