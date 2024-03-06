// An example application for Java maps: word frequencies

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


class FrequencyTable {
  // Use TreeMap, because we want words to be sorted
  private Map<String,Integer> frequencies = new TreeMap<>();

  public FrequencyTable(String filename) throws IOException {
    try (Scanner input = new Scanner(Paths.get(filename))) {
      while (input.hasNext()) {
        // Read a word and normalise it by converting to lowercase
        // and removing anything that isn't a letter

        String word = input.next().toLowerCase().replaceAll("[^a-z]", "");

        if (word.length() == 0) {
          continue;   // no letters remain, so fetch another word
        }

        // Update frequency of occurrence for the word
 
        int freq = frequencies.getOrDefault(word, 0);
        frequencies.put(word, freq+1);
      }
    }
  }

  public void display() {
    // Iterate over keys and values, printing them
    // (Contrast this with approach used in MapCreation.java)
    frequencies.forEach((word, freq) -> {
      System.out.printf("%5d %s\n", freq, word);
    });
  }
}


class WordFrequency {
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.out.println("Usage: java WordFrequency <filename>");
      System.exit(1);
    }

    FrequencyTable table = new FrequencyTable(args[0]);
    table.display();
  }
}
