import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a point in space and time, recorded by a GPS sensor.
 *
 * @author YOUR sc23mas
 */
public class Track {
  private List<Point> points;
  private DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;

  //constructor to takes a filename and reads the data
  public Track(String filename) throws IOException {
      points = new ArrayList<>();
      readFile(filename);
  }

  public Track() {
      points = new ArrayList<>();
  }

  //read data, create point objects and validation steps
  void readFile(String filename) throws IOException {
      points.clear();
      try (Scanner scanner = new Scanner(Files.newBufferedReader(Paths.get(filename)))) {
          while (scanner.hasNextLine()) {
              String[] data = scanner.nextLine().split(",");
              if (data.length != 4) {
                  throw new GPSException("Incorrect data format");
              }
              ZonedDateTime timestamp = ZonedDateTime.parse(data[0], formatter);
              double longitude = Double.parseDouble(data[1]);
              double latitude = Double.parseDouble(data[2]);
              double elevation = Double.parseDouble(data[3]);
              points.add(new Point(timestamp, longitude, latitude, elevation));
          }
      }
  }

  //find number of points
  public int size() {
      return points.size();
  }

  //get point object from a given index
  public Point get(int index) {
      if (index < 0 || index >= points.size()) {
          throw new GPSException("Invalid index: " + index);
      }
      return points.get(index);
  }

  //add point objects
  public void add(Point point) {
      points.add(point);
  }

  //find point objects for lowest and highest points
  public Point lowestPoint() {
      if (points.isEmpty()) {
          throw new GPSException("Track is empty");
      }
      Point lowestPoint = points.get(0);
      for (Point point : points) {
          if (point.getElevation() < lowestPoint.getElevation()) {
              lowestPoint = point;
          }
      }
      return lowestPoint;
  }

  public Point highestPoint() {
      if (points.isEmpty()) {
          throw new GPSException("Track is empty");
      }
      Point highestPoint = points.get(0);
      for (Point point : points) {
          if (point.getElevation() > highestPoint.getElevation()) {
              highestPoint = point;
          }
      }
      return highestPoint;
  }

  //calculate total distance travelled
  public double totalDistance() {
      if (points.size() < 2) {
          throw new GPSException("Not enough points for calculation");
      }
      double total = 0.0;
      for (int i = 0; i < points.size() - 1; i++) {
          total += Point.greatCircleDistance(points.get(i), points.get(i + 1));
      }
      return total;
  }

  //calculate average speed travelled
  public double averageSpeed() {
      if (points.size() < 2) {
          throw new GPSException("Not enough points for calculation");
      }
      long durationInSeconds = ChronoUnit.SECONDS.between(points.get(0).getTime(), points.get(points.size() - 1).getTime());
      return totalDistance() / durationInSeconds;
  }
}
