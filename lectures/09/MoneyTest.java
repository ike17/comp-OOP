// Test program for hashCode, equals & compareTo methods of Money

import static java.lang.System.out;

public class MoneyTest {
  public static void main(String[] args) {
    Money m1 = new Money(1, 80);
    Money m2 = new Money(2, 5);
    Money m3 = new Money(1, 80);

    out.printf("m1 is %s\n", m1);
    out.printf("m2 is %s\n", m2);
    out.printf("m3 is %s\n\n", m3);

    out.printf("m1 hashes to %d\n", m1.hashCode());
    out.printf("m2 hashes to %d\n", m2.hashCode());
    out.printf("m3 hashes to %d\n\n", m3.hashCode());

    out.printf("m1 == m1? %s\n", m1 == m1);
    out.printf("m1 == m2? %s\n", m1 == m2);
    out.printf("m1 == m3? %s\n\n", m1 == m3);

    out.printf("m1.equals(m1)? %s\n", m1.equals(m1));
    out.printf("m1.equals(m2)? %s\n", m1.equals(m2));
    out.printf("m1.equals(m3)? %s\n\n", m1.equals(m3));

    out.printf("m1.compareTo(m1) = %d\n", m1.compareTo(m1));
    out.printf("m1.compareTo(m2) = %d\n", m1.compareTo(m2));
    out.printf("m1.compareTo(m3) = %d\n", m1.compareTo(m3));
    out.printf("m2.compareTo(m1) = %d\n\n", m2.compareTo(m1));
  }
}
