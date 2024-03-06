// Utility class for unit conversions
// (This could become part of a library, used by multiple programs)

public class Conversion {
  // Conversion factors are defined as class constants (static & final)
  public static final double KM_PER_MILE = 1.6;
  public static final double CM_PER_INCH = 2.54;

  public static double milesToKm(double miles) {
    return miles * KM_PER_MILE;
  }

  public static double inchesToCm(double inches) {
    return inches * CM_PER_INCH;
  }

  // etc
}
