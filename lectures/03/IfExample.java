/*
   An attempt to implement IfExample.c in Java.

   This program won't compile because Java is stricter about the
   expressions used in the test part of an if statement.
*/

class IfExample {
  public static void main(String[] args) {
    int x = 42, y = 0;

    if (x) {
      System.out.println("x is non-zero");
    }
    else {
      System.out.println("x is zero");
    }

    if (y) {
      System.out.println("y is non-zero");
    }
    else {
      System.out.println("y is zero");
    }
  }
}
