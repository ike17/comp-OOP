// Example of attempting to create an invalid Clock

public class ErrorDemo {
  public static void main(String[] args) {
    Clock clock = new Clock(10, -1);   // minutes cannot be negative!

    System.out.println("This line shouldn't be printed!");
  }
}
