# Lecture 1 Examples

## A Simple Program

1. Examine `Hello.java'.  Then compile it like so:

       javac Hello.java

2. List the contents of the current directory.  You should see that a new
   file named `Hello.class` has been created.  Check the size of this
   file (e.g., using `ls -l` on Linux or macOS).

3. `Hello.class` contains **Java bytecode**.  To see what bytecode looks
   like in a readable form, disassemble it with this command:

       javap -c Hello

4. Finally, run the bytecode in the Java Virtual Machine like so:

       java Hello

## Initialization of Variables

1. Examine `Initalize.c`, then compile it:

       gcc -o Initialize Initialize.c

   This should succeed without errors.

2. What do you think will be printed when you run the program?  Run it
   with `./Initialize`, to see if you are right.

3. Try recompiling the program, this time including the `-Wall` option,
   to turn on all warnings.  The compiler will now warn you about the
   uninitialized variable (although it still proceeds to compile the code).

4. Examine `Initialize.java`, then attempt to compile it:

       javac Initalize.java

   This should fail, with an error reported on line 10 (where the program
   attempts to use the uninitialized variable).

## Array Creation & Element Access

1. Examine `ArrayExample.c`, then compile it:

       gcc -o ArrayExample ArrayExample.c

2. What behaviour do you expect to see from this program?  Run it with
   `./ArrayExample`, to see if you are right.

3. Examine `ArrayExample.java`, then compile and run it:

       javac ArrayExample.java
       java ArrayExample

   Notice the program still crashes, but it does so in a controlled way,
   at the first attempt to access the array illegally.  The error is
   an **exception**, which conveys information about what went wrong and
   where in the program it happened.

   Contrast this with `ArrayExample.c`, where the segmentation fault that you
   see is the operating system telling you that you've attempted to access
   memory illegally.  Since the OS knows nothing about your program, the
   error is much less informative and useful than the one you see in Java.
