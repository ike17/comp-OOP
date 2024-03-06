// Example of using static imports

import java.util.Scanner;

import static java.lang.System.out;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public class PolarToCartesian {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    out.print("Enter distance: ");
    double r = input.nextDouble();

    out.print("Enter angle: ");
    double theta = toRadians(input.nextDouble());

    double x = r*cos(theta);
    double y = r*sin(theta);

    out.printf("x = %.3f, y = %.3f\n", x, y);
  }
}
