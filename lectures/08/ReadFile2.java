// Reading a text file line by line with a BufferedReader

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class ReadFile2 {
  public static void main(String[] args) throws IOException {
    // Check for a filename on command line

    if (args.length == 0) {
      System.out.println("Usage: java ReadFile2 <filename>");
      System.exit(1);
    }

    // Create a reader for buffered input from the specified file

    Path path = Paths.get(args[0]);
    BufferedReader input = Files.newBufferedReader(path);

    // Attempt to read a line

    String line = input.readLine();

    // Process line and fetch next one for as long as we can...

    while (line != null) {
      System.out.println(line);   // just print line in this case
      line = input.readLine();
    }

    input.close();    // better to do this using try-with-resources
  }
}
