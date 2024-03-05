// Example of using Scanner to parse standard input

import java.util.Scanner;

class Input {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    System.out.print("Enter an integer value: ");
    int n = input.nextInt();

    System.out.println("n = " + n);
  }
}
