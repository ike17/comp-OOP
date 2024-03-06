// Example of using static methods from utility classes

public class MilesToKm {
  public static void main(String[] args) {
    double distMiles = Input.getDouble("Enter distance in miles: ");
    double distKm = Conversion.milesToKm(distMiles);
    System.out.printf("%.1f miles = %.1f km\n", distMiles, distKm);
  }
}
