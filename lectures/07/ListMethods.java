// Examples of list methods

import java.util.ArrayList;
import java.util.List;
import static java.lang.System.out;   // saves on typing!

class ListMethods {
  public static void main(String[] args) {

    // Create list and add words to it

    List<String> words = new ArrayList<>();
    //List<String> words = new LinkedList<>();

    words.add("mango");
    words.add("orange");
    words.add("apple");
    words.add("raspberry");
    words.add("kiwi");

    // Demonstrate iteration over list

    out.print("List:");

    for (String word: words) {
      out.printf(" %s", word);
    }

    // Demonstrate item retrieval

    out.printf("\n\nSize = %d\n", words.size());
    out.printf("Item at index 0: %s\n", words.get(0));
    out.printf("Item at index 1: %s\n", words.get(1));

    // Demonstrate searching for an item

    int index = words.indexOf("apple");
    int lastIndex = words.lastIndexOf("apple");
    if (index != -1) {
      out.printf("First occurrence of \"apple\" at %d\n", index);
      out.printf("Last occurrence of \"apple\" at %d\n", lastIndex);
    }

    // Demonstrate removal of an item

    out.printf("\nAttempting to remove item at index 1...\n");
    words.remove(1);

    out.printf("Size = %d\n", words.size());
    out.printf("Item at index 0: %s\n", words.get(0));
    out.printf("Item at index 1: %s\n", words.get(1));
  }
}
