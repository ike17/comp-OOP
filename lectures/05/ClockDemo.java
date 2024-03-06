// Demonstation of a Clock object in action

public class ClockDemo {
  public static void main(String[] args) {
    // Create a Clock set to a time just before midnight
    Clock clock = new Clock(23, 59, 50);

    // Create a thread to execute run() method of Clock object
    Thread thread = new Thread(clock);

    // Start the clock ticking!
    thread.start();

    System.out.println("Clock running (Ctrl+C to quit)...");
  }
}
