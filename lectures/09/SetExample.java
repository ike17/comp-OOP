// Simple example of using sets

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;
import java.util.Scanner;
import java.util.Set;

import static java.lang.System.out;

class SetExample {
  public static void main(String[] args) {
    // Create an empty set for integers

    Set<Integer> set = new HashSet<>();
    //Set<Integer> set = new LinkedHashSet<>();
    //Set<Integer> set = new TreeSet<>();

    // Populate set with integers obtained from user

    out.println("Enter numbers below, or Ctrl+D to quit...");

    try (Scanner input = new Scanner(System.in)) {
      while (input.hasNextInt()) {
        int number = input.nextInt();
        set.add(number);
      }
    }

    // Display contents of set

    if (set.isEmpty()) {
      out.println("\nSet is empty!");
    }
    else {
      out.print("\nSet contains:");
      for (int number: set) {
        out.printf(" %d", number);
      }
      out.println();
    }
  }
}
