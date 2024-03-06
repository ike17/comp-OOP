// Reading text files using Java's NIO classes

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.lang.System.out;

class ReadFile1 {
  public static void main(String[] args) throws IOException {
    // Check for a filename on command line

    if (args.length == 0) {
      out.println("Usage: java ReadFile1 <filename>");
      System.exit(1);
    }

    // Create Path representing file specified on command line

    Path path = Paths.get(args[0]);

    // Read entire file into a string

    out.println("Calling readString()...");

    String text = Files.readString(path);

    out.printf("%d characters read:\n\n%s", text.length(), text);

    // Read file into a list of lines

    out.printf("\n\nCalling readAllLines()...\n");

    List<String> lines = Files.readAllLines(path);

    out.printf("%d lines read:\n\n", lines.size());

    for (String line: lines) {
      out.println(line);
    }
  }
}
