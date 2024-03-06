// Building a long string inefficiently, via concatenation

class BadBuild {
  public static void main(String[] args) {

    long start = System.nanoTime();

    String line = "";
    for (int i = 0; i < 10000; ++i) {
      line += "*";
    }

    long end = System.nanoTime();

    double t = (end - start) / 1.0e+6;
    System.out.printf("%.1f milliseconds\n", t);
  }
}
