// Demonstration of how Money objects are sortable

import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.Collections;

public class MoneySort {
  public static void main(String[] args) {
    Random rng = new Random();

    List<Money> payments = new ArrayList<>();
    for (int i = 0; i < 10; ++i) {
      int euros = rng.nextInt(25);
      int cents = rng.nextInt(100);
      payments.add(new Money(euros, cents));
    }

    System.out.println("Before sorting:");
    for (Money m: payments) {
      System.out.println(m);
    }

    Collections.sort(payments);   // uses compareTo method

    System.out.println("\nAfter sorting:");
    for (Money m: payments) {
      System.out.println(m);
    }
  }
}
