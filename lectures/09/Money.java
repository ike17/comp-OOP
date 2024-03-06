// Example of a small Java class representing a sum of money

import java.util.Objects;

public class Money implements Comparable<Money> {
  private int euros, cents;

  public Money(int e, int c) {
    euros = e;
    cents = c;
  }

  public int getEuros() {
    return euros;
  }

  public int getCents() {
    return cents;
  }

  @Override
  public String toString() {
    return String.format("€%d.%02d", euros, cents);
  }

  public Money plus(Money other) {
    int sumEuros = euros + other.euros;
    int sumCents = cents + other.cents;
    return new Money(sumEuros + sumCents / 100, sumCents % 100);
  }

  // Methods below allow Money objects to work properly in collections

  @Override
  public int hashCode() {
    return Objects.hash(euros, cents);
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    else if (other instanceof Money) {
      Money m = (Money) other;
      return euros == m.euros && cents == m.cents;
    }
    else {
      return false;
    }
  }

  public int compareTo(Money other) {
    int result = Integer.compare(euros, other.euros);
    if (result == 0) {
      result = Integer.compare(cents, other.cents);
    }
    return result;
  }
}
