// Examples of using printf for formatted output

class Output {
  public static void main(String[] args) {

    System.out.println("Pi = " + Math.PI);

    System.out.printf("Pi = %.3f%n", Math.PI);
    System.out.printf("Pi = %.7f%n", Math.PI);
    System.out.printf("Pi = |%12.7f|%n", Math.PI);
    System.out.printf("Pi = |%-12.7f|%n", Math.PI);

  }
}
