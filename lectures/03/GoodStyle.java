// Compare this with BadStyle.java

import java.util.Scanner;

class MilesToKm {
  public static void main(String[] args) {

    // Obtain distance in miles

    Scanner input = new Scanner(System.in);

    System.out.print("Enter distance in miles: ");
    double distanceMiles = input.nextDouble();

    // Compute and display distance in km

    double distanceKm = distanceMiles * 1.6;
    System.out.printf("Distance in km = %.1f\n", distanceKm);
  }
}
