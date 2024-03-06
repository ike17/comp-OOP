/*
   Example of procedural programming using static methods.

   Note the use of 'private' for getDistanceInMiles() and milesToKm().
   This is because they don't need to be seen outside the class.
*/

import java.util.Scanner;

public class MilesToKm {
  private static double getDistanceInMiles() {
    System.out.print("Enter distance in miles: ");
    Scanner input = new Scanner(System.in);
    return input.nextDouble();
  }

  private static double milesToKm(double distMiles) {
    return distMiles * 1.6;
  }

  public static void main(String[] args) {
    double distMiles = getDistanceInMiles();
    double distKm = milesToKm(distMiles);
    System.out.printf("%.1f miles = %.1f km\n", distMiles, distKm);
  }
}
