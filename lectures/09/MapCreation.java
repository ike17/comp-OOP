// Examples of creating maps with different implementations

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.Map;

class MapCreation {
  public static void main(String[] args) {
    // Create a map with a particular implementation

    Map<String,Integer> map = new HashMap<>();
    //Map<String,Integer> map = new LinkedHashMap<>();
    //Map<String,Integer> map = new TreeMap<>();

    // Put some key-value pairs in it

    map.put("apple",   42);
    map.put("orange",  34);
    map.put("mango",  100);
    map.put("banana",  17);

    // Retrieve & print keys, to see effect of implementation on ordering

    for (String key: map.keySet()) {
      System.out.println(key);
    }
  }
}
