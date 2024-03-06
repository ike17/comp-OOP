// Demonstration of how exceptions affect flow of control

import java.util.Scanner;
import static java.lang.System.out;

class Thing {
  public void calculate(int value) {
    out.println("calculate() started");
    divideBy(value);
    out.println("calculate() ended");
  }

  public void divideBy(int value) {
    out.println("divideBy() started");
    int result = 100 / value;
    out.printf("result = %d\n", result);
    out.println("divideBy() ended");
  }
}

public class Control {
  public static void main(String[] args) {
    out.println("Program started");

    Scanner input = new Scanner(System.in);
    out.print("Enter value: ");
    int value = input.nextInt();

    Thing t = new Thing();
    t.calculate(value);

    out.println("Program ended");
  }
}
