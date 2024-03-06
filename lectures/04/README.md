# Lecture 4 Examples

## Quiz

`BadIf.java`, `ForLoop.java` and `StringQuiz.java` are all related to
the quiz from the start of the lecture.

## String Handling

`BadBuild.java` and `GoodBuild.java` both build a string that is 10,000
characters long, one character at a time.  Both programs measure how long
it takes to do this.  If you try running these programs, you'll find that
the string concatenation approach of `BadBuild` is *significantly* slower
than the approach of `GoodBuild`, which uses a `StringBuilder` object.
Concatenation in a loop is a bad idea because it creates numerous temporary
`String` objects.

`TextBlock.java` is a demonstration of the multiline text block feature
introduced in Java 17.  This example won't compile with older versions
of the JDK.

## Static Methods & Imports

`Root2.c` and `Root2.java` show how to compute square roots in C and Java.
With C, you include the `math.h` header and link your code with the
math library, allowing it to use the `sqrt` library function.  In the case
of Java, square roots are computed by a **static method** defined in the
`Math` class from Java's standard library.  All of the methods defined in
this class are static, they don't need to be invoked on an object.  You
just need to use the class name as a prefix when invoking them.

`MilesToKm.java` shows how you can program in a purely procedural style
in Java.  Each of the functions that you would have written in C are
implemented as static methods in Java. **This is not the recommended way
of programming in Java**; it is much better to use a proper object-oriented
approach (see Lecture 5 onwards).

The `separate` directory contains a version of `MilesToKm.java` in which
the methods for obtaining input and converting distance have been extracted
into separate utility classes.

`PolarToCartesian.java` demonstrates the use of static imports for
`System.out` and methods from the `Math` class.
