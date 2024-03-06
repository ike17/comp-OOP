// Class to represent a 24-hour clock

public class Clock implements Runnable {
  // Constants used by methods of Clock class
  // (static means they are shared by all instances)

  private static final int SECONDS_IN_A_MINUTE = 60;
  private static final int MINUTES_IN_AN_HOUR = 60;
  private static final int HOURS_IN_A_DAY = 24;

  // Private fields to represent internal state of a clock

  private int hours;
  private int minutes;
  private int seconds;

  // Constructors that initialize a clock three different ways

  public Clock() {
    this(0, 0, 0);   // calls three-parameter constructor
  }

  public Clock(int h, int m) {
    this(h, m, 0);
  }

  public Clock(int h, int m, int s) {
    setHours(h);    // initialization is delegated to setters
    setMinutes(m);
    setSeconds(s);
  }

  // Getter methods allow class users to query current state of a clock

  public int getHours() { return hours; }

  public int getMinutes() { return minutes; }

  public int getSeconds() { return seconds; }

  // Setter methods are private, so act as 'helpers' to constructor
  // (could make them public if users needed to adjust time on a clock)

  private void setHours(int h) {
    // Check validity of provided hours value (note use of class constant!)
 
    if (h < 0 || h >= HOURS_IN_A_DAY) {
      throw new IllegalArgumentException("invalid hours");
    }

    hours = h;   // assignment only happens if there is no exception
  }

  private void setMinutes(int m) {
    if (m < 0 || m >= MINUTES_IN_AN_HOUR) {
      throw new IllegalArgumentException("invalid minutes");
    }
    minutes = m;
  }

  private void setSeconds(int s) {
    if (s < 0 || s >= SECONDS_IN_A_MINUTE) {
      throw new IllegalArgumentException("invalid seconds");
    }
    seconds = s;
  }

  // toString() method provides a string representation of clock state
  // (called automatically whenever you print a Clock object)
  // Note use of the @Override annotation

  @Override
  public String toString() {
    return String.format("%02d:%02d:%02d", hours, minutes, seconds);
  }

  // display() method displays current time on the clock

  public void display() {
    System.out.println(this);   // calls toString() automatically
  }

  // tick() method makes clock tick once, advancing time by one second

  public void tick() {
    ++seconds;
    if (seconds == SECONDS_IN_A_MINUTE) {
      seconds = 0;
      ++minutes;
      if (minutes == MINUTES_IN_AN_HOUR) {
        minutes = 0;
        ++hours;
        if (hours == HOURS_IN_A_DAY) {
          hours = 0;
        }
      }
    }
  }

  // run() simulates ticking of clock by invoking tick() once per second

  @Override
  public void run() {
    try {
      while (true) {
        display();
        tick();
        Thread.sleep(1000);
      }
    }
    catch (InterruptedException error) {
      System.exit(1);
    }
  }
}
