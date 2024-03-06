// Demonstration that creating an array of object references
// doesn't also create the referenced objects

class ArrayObject {
  public static void main(String[] args) {
    Clock[] c = new Clock[3];

    // Show that array contains 3 null references

    System.out.println("Array size is " + c.length);
    System.out.println("c[0] = " + c[0]);
    System.out.println("c[1] = " + c[1]);
    System.out.println("c[2] = " + c[2]);

    // Replace first null reference with a reference to an object

    System.out.println("Initializing c[0]...");

    c[0] = new Clock(7, 30);
    System.out.println("c[0] = " + c[0]);

    // Now that c[0] has been initialized, we can call a method on it
  
    System.out.println("Calling a method on c[0]...");
    c[0].tick();

    // ...but if we attempt this for c[1] we get a NullPointerException

    System.out.println("Calling a method on c[1]...");
    c[1].tick();
  }
}
