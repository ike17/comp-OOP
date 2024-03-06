import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Track {
    private List<Point> points = new ArrayList<>();

    // Constructor that initializes the list from a file
    public Track(String filename) throws IOException {
        readFile(filename);
    }

    // Alternate no-arg constructor to support tests expecting an empty Track
    public Track() {
        this.points = new ArrayList<>();
    }

    // Modified to package-private or public to allow testing, if necessary
    void readFile(String filename) throws IOException {
        try (Scanner scanner = new Scanner(Files.newBufferedReader(Paths.get(filename)))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                if (data.length != 4) throw new IllegalArgumentException("Incorrect data format");
                ZonedDateTime timestamp = ZonedDateTime.parse(data[0]);
                double longitude = Double.parseDouble(data[1]);
                double latitude = Double.parseDouble(data[2]);
                double elevation = Double.parseDouble(data[3]);
                Point point = new Point(timestamp, longitude, latitude, elevation);
                points.add(point);
            }
        }
    }

    // Existing methods unchanged
    public int size() {
        return points.size();
    }

    public Point get(int index) {
        if (index < 0 || index >= points.size()) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        return points.get(index);
    }

    public void add(Point point) {
        points.add(point);
    }


}
