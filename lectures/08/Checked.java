/*
   Example of code that can cause a checked exception.

   The program attempts to open a file called 'foo.txt', throwing
   a FileNotFoundException if the attempt fails.

   FileNotFoundException is a 'checked exception'.  Such exceptions
   must either be handled by your code, using try and catch blocks, or
   you must include an exception specification as part of the
   definition of the method containing the code.  In this example,
   the latter is done.
*/

import java.io.FileNotFoundException;
import java.io.FileReader;

class Checked {
  public static void main(String[] args) throws FileNotFoundException {
                                         // ^exception specification

    FileReader reader = new FileReader("foo.txt");

    System.out.println("Opened 'foo.txt' successfully.");
  }
}
