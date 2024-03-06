/*
   Example of using try, catch and finally to handle exceptions.

   This program reads floating-point numbers from a file, one per line,
   and computes their average.  The entire code to perform this task
   is placed within a try block and is followed by three different
   catch blocks - one for each type of exception that could be thrown.

   If an exception is thrown, control will pass to the matching catch
   block.  The code inside the finally block will always execute,
   regardless of whether an exception was detected or not.
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.System.out;

public class CatchTest1 {
  public static void main(String[] args) {
    if (args.length == 0) {
      out.println("Usage: java CatchTest1 <datafile>");
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
    catch (NoSuchFileException error) {
      out.println("File does not exist!");
    }
    catch (IOException error) {
      out.println("Error reading from data file");
    }
    catch (NumberFormatException error) {
      out.println("Data parsing error");
    }
    finally {
      out.println("Program finished");
    }
  }
}
