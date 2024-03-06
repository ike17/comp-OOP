// Utility class for obtaining user input
// (This could become part of a library, used by multiple programs)

import java.util.Scanner;

public class Input {
  // Define Scanner object as a static field of the class, so it
  // can be shared by the various static methods
  private static Scanner input = new Scanner(System.in);

  public static int getInteger(String prompt) {
    System.out.print(prompt);
    return input.nextInt();
  }

  public static double getDouble(String prompt) {
    System.out.print(prompt);
    return input.nextDouble();
  }

  // etc
}
