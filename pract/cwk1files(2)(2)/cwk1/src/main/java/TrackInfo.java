import java.io.IOException;

public class TrackInfo {
  public static void main(String[] args) {
      if (args.length == 0) {
          System.err.println("Error: No filename provided.");
          System.exit(0);
      }
      
      String filename = args[0];
      try {
          Track track = new Track(filename);
          System.out.println(track.size() + " points in track");
          System.out.println("Lowest point is " + track.lowestPoint());
          System.out.println("Highest point is " + track.highestPoint());
          System.out.printf("Total distance = %.3f km\n", track.totalDistance() / 1000);
          System.out.printf("Average speed = %.3f m/s\n", track.averageSpeed());
      } catch (IOException e) {
          System.err.println("Failed to read the file: " + e.getMessage());
          System.exit(1);
      } catch (GPSException e) {
          System.err.println("GPS data error: " + e.getMessage());
          System.exit(1);
      }
  }
}
