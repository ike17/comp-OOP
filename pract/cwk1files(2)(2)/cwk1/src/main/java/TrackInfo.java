
public class TrackInfo {
  public static void main(String[] args) {

      //check if file name is provided as an argument
      if (args.length == 0) {
          System.err.println("Error: No filename provided.");
          System.exit(0);
      }

      try {
          //create a new track object read and output the required data
          Track track = new Track(args[0]);
          System.out.println(track.size() + " points in track");
          System.out.println("Lowest point is " + track.lowestPoint());
          System.out.println("Highest point is " + track.highestPoint());
          System.out.printf("Total distance = %.3f km\n", track.totalDistance() / 1000);
          System.out.printf("Average speed = %.3f m/s\n", track.averageSpeed());
      } catch (GPSException e) {
          System.err.println("GPS data error" + e.getMessage());
          System.exit(1);
      }
  }
}
