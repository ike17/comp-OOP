// Examples of manipulating a map

import java.util.Map;
import java.util.TreeMap;

import static java.lang.System.out;

class MapExample {
  public static void main(String[] args) {
    // Create a map containing some keys & values

    Map<String,Integer> map = new TreeMap<>();

    map.put("apple",  42);
    map.put("orange", 34);
    map.put("mango",  100);
    map.put("banana", 17);

    // Call various map methods

    out.printf("Size = %d\n", map.size());
    out.printf("Empty? %s\n", map.isEmpty());

    out.printf("Contains \"mango\"? %s\n", map.containsKey("mango"));
    out.printf("Contains \"kiwi\"?  %s\n", map.containsKey("kiwi"));

    out.printf("Value for mango = %d\n", map.get("mango"));
    out.printf("Value for kiwi  = %d\n", map.get("kiwi"));

    // Demonstrate removal of a key-value pair

    out.printf("\nRemoving \"mango\"...\n");
    map.remove("mango");

    out.printf("Size = %d\n", map.size());
    out.printf("Contains \"mango\"? %s\n", map.containsKey("mango"));
    out.printf("Value for mango = %d\n", map.get("mango"));

    // Demonstrate effect of calling clear method

    out.printf("\nClearing map...\n");
    map.clear();

    out.printf("Size = %d\n", map.size());
    out.printf("Empty? %s\n", map.isEmpty());
  }
}
