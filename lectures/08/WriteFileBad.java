// Example of writing formatted data to a text file, with an error

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;
import java.io.PrintWriter;

class WriteFileBad {
  public static void main(String[] args) throws IOException {
    // Check for a filename on command line

    if (args.length == 0) {
      System.out.println("Usage: java WriteFileBad <filename>");
      System.exit(1);
    }

    // Create a writer for formatted text output to the given file

    Path path = Paths.get(args[0]);
    PrintWriter out = new PrintWriter(Files.newBufferedWriter(path));

    // Print 100 pairs of values

    for (int i = 0; i < 100; ++i) {
      int x = (int) (250*Math.random());
      int y = (int) (250*Math.random());
      out.printf("%3d %3d\n", x, y);
    }

    // Forgot to close the file here!
    // (But better to use 'try with resources')
  }
}
