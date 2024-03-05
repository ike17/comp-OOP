// Example of using Console to read from standard input
// (Compare with Input.java & Input2.java)

import java.io.Console;

class Input3 {
  public static void main(String[] args) {
    Console con = System.console();
    String input = con.readLine("Enter an integer value: ");

    // Console methods provide user input as a string,
    // which must be parsed to a number separately

    int n = Integer.parseInt(input);

    System.out.println("n = " + n);
  }
}
