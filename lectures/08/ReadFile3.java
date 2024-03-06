// Reading a text file line by line with a Scanner
// (also using a try-with-resources block)

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

class ReadFile3 {
  public static void main(String[] args) throws IOException {
    // Check for a filename on command line

    if (args.length == 0) {
      System.out.println("Usage: java ReadFile3 <filename>");
      System.exit(1);
    }

    try (Scanner input = new Scanner(Paths.get(args[0]))) {

      // Fetch lines for as long as we can...

      while (input.hasNextLine()) {
        String line = input.nextLine();
        System.out.println(line);        // just print line in this case
      }
    }
  }
}
