// Where are the problems here?

import java.util.Scanner;

class BadIf {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    System.out.print("Enter a non-zero number: ");
    int number = input.nextInt();

    if (number) {
      System.out.println("Number is OK");
    }
    else if {
      System.out.println("Number must be non-zero");
    }
  }
}
