import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Track {
    private List<Point> points = new ArrayList<>();

    public Track(String filename) throws IOException {
        readFile(filename);
    }

    public Track() {
        this.points = new ArrayList<>();
    }

    void readFile(String filename) throws IOException {
      DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME; // Format for parsing ZonedDateTime
      points.clear(); // Clear existing points before reading new data
      try (Scanner scanner = new Scanner(Files.newBufferedReader(Paths.get(filename)))) {
          while (scanner.hasNextLine()) {
              String[] data = scanner.nextLine().split(",");
              if (data.length != 4) { // Check for the correct number of columns
                  throw new GPSException("Incorrect data format");
              }
              ZonedDateTime timestamp = ZonedDateTime.parse(data[0], formatter); // Parse the ZonedDateTime
              double longitude = Double.parseDouble(data[1]);
              double latitude = Double.parseDouble(data[2]);
              double elevation = Double.parseDouble(data[3]);
              points.add(new Point(timestamp, longitude, latitude, elevation)); // Add the new point
          }
      }
  }

    public int size() {
        return points.size();
    }

    public Point get(int index) {
        if (index < 0 || index >= points.size()) {
            throw new GPSException("Invalid index: " + index);
        }
        return points.get(index);
    }

    public void add(Point point) {
        points.add(point);
    }

    public Point lowestPoint() {
        return points.stream()
                     .min(Comparator.comparing(Point::getElevation))
                     .orElseThrow(() -> new GPSException("Track is empty"));
    }

    public Point highestPoint() {
        return points.stream()
                     .max(Comparator.comparing(Point::getElevation))
                     .orElseThrow(() -> new GPSException("Track is empty"));
    }

    public double totalDistance() {
        double total = 0.0;
        if (points.size() < 2) {
          throw new GPSException("Not enough points for calculation");
        }
      
        for (int i = 0; i < points.size() - 1; i++) {
            total += Point.greatCircleDistance(points.get(i), points.get(i + 1));
        }
        return total;
    }

    public double averageSpeed() {
        if (points.size() < 2) {
            throw new GPSException("Not enough points for calculation");
        }
        long durationInSeconds = ChronoUnit.SECONDS.between(points.get(0).getTime(), points.get(points.size() - 1).getTime());
        return totalDistance() / durationInSeconds;
      }
}
