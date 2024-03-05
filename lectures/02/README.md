# Lecture 2 Examples

`Expressions.java` demonstrates the answers to the 'expressions quiz' from
the lecture.

`Input.java` is an example of how the [Scanner][scan] class can be used
to obtain a numeric value from user input (in this case an `int`).
`Input2.java` is a modified version of `Input.java` which uses the
'try with resources' approach to ensure that Scanner resources are cleaned
up properly.

`Input3.java` demonstrates an alternative approach to obtaining user input,
involving the `Console` class.  In this case, the methods of the class
return the input as a string, which must be converted by hand if wishing
to treat that input as a number of some kind.

`Output.java` shows some examples of how to do [formatted printing][fmt] in
Java, using a similar approach to that used with `printf` in C.

[scan]: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Scanner.html
[fmt]: https://docs.oracle.com/javase/tutorial/essential/io/formatting.html
