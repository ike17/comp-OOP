// Demonstration of selected String methods

class Strings {
  public static void main(String[] args) {
    String s = "Hello World!";

    System.out.printf("String content:     %s\n", s);

    System.out.printf("length():           %d\n", s.length());
    System.out.printf("isEmpty():          %s\n", s.isEmpty());

    System.out.printf("charAt(1):          %c\n", s.charAt(1));
    System.out.printf("indexOf('W'):       %d\n", s.indexOf('W'));
    System.out.printf("startsWith(\"He\"):   %s\n", s.startsWith("He"));
    System.out.printf("endsWith(\".\"):      %s\n", s.endsWith("."));

    System.out.printf("substring(1, 4):    %s\n", s.substring(1, 4));

    System.out.printf("replace('o', '*'):  %s\n", s.replace('o', '*'));
    System.out.printf("String content:     %s\n", s);

    System.out.printf("toUpperCase():      %s\n", s.toUpperCase());
    System.out.printf("String content:     %s\n", s);
  }
}
