// Example of how to build a long string efficiently

class GoodBuild {
  public static void main(String[] args) {

    long start = System.nanoTime();

    StringBuilder builder = new StringBuilder();

    for (int i = 0; i < 10000; ++i) {
      builder.append('*');
    }

    long end = System.nanoTime();

    double t = (end - start) / 1.0e+6;
    System.out.printf("%.1f milliseconds\n", t);
  }
}
