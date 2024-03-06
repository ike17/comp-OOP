# Lecture 3 Examples

## Coding Style

`BadStyle.java` and `GoodStyle.java` are essentially the same program:
the one examined in Lecture 2 which converts a distance in miles to
a distance in kilometres.  Both compile and run as expected.  They differ
only in terms of coding style.  You should avoid doing the things seen
in `BadStyle.java` and should try to style your programs more like
`GoodStyle.java`.

## Conditional Statements

`IfExample.c` and `IfExample.java` together demonstrate that, whilst C and
Java have the same basic syntax for `if` statements, Java is stricter
about what can be used as the test performed by an `if` or `else if` branch.

You should find `IfExample.c` will compile and run successfully, whereas
`IfExample.java` will trigger compiler errors.

## Strings

`Unicode.java` contains examples of Unicode escape sequences, which you can
use to put 'special' characters in strings.

`Strings.java` demonstrates some of the methods that can be invoked on
`String` objects.  For further details, see the [API documentation][1].

[1]: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html
