/*
   Example of using try, catch and finally to handle exceptions.

   This program is almost identical to CatchExample1.java.  The only
   difference is that a single catch block is used to intercept all
   exceptions.  This works because all exception classes inherit
   from the superclass Exception.
*/

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.System.out;

public class CatchTest2 {
  public static void main(String[] args) {
    if (args.length == 0) {
      out.println("Usage: java CatchTest2 <datafile>");
      System.exit(1);
    }

    try {
      // Access file (could trigger NoSuchFileException)

      Path path = Paths.get(args[0]);
      BufferedReader input = Files.newBufferedReader(path);

      out.println("Starting to read file...");

      double total = 0;
      int numValues = 0;

      while (true) {
        // Read line (could trigger IOException)

        String line = input.readLine();
        if (line == null)
          break;

        // Parse line (could trigger NumberFormatException)

        double value = Double.parseDouble(line);
        total += value;
        ++numValues;
      }

      double average = total / numValues;
      out.printf("Average = %.3f%n", average);
    }
    catch (Exception error) {
      out.println("Some kind of error encountered :(");
      out.println(error);
    }
    finally {
      out.println("Program finished");
    }
  }
}
