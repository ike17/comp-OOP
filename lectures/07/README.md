# Lecture 7 Examples

`ArrayExample.c` and `ArrayExample.java`, first seen in Lecture 1, compare
array access in C and Java, demonstrating that C does no initialization
of array elements and no bounds checking, whereas Java ensures that arrays
of primitives are initialized and that we cannot exceed the bounds of
an array.

`ArrayInit.java` shows different approaches to array initialization,
including examples of **static initialization**.

`ArrayObject.java` shows how arrays of objects differ from arrays of
primitives.  An 'array of objects' is really an array of **object
references**.  Creating the array gives us a way of referring to multiple
objects as a sequence, **but it doesn't create those referenced objects**.

`ArrayLoop.java` demonstrates the two styles of for loop that can be used
to iterate over array contents.

`ArrayPrint.java` shows that you don't get useful output if you try to
print an array using `System.out.println()`.  It also shows how you can
use the `toString()` and `deepToString()` methods of the `Arrays` utility
class to do 'pretty printing' of array contents.

`ArrayUtil.java` demonstrate use of the various utility methods provided
in the `Arrays` class.

`Wrapper.java` shows how **wrapper classes** such as `Integer` are used
to store lists of values that are normally represented in Java using
primitive types.

`ListBenchmark.java` compares the performance of `ArrayList` and `LinkedList`
in a scenario where a list is constructed by repeatedly inserting items
at its front.  Try running it like so:

    java ListBenchmark 5000
    java ListBenchmark 20000
    java ListBenchmark 100000
    java ListBenchmark 200000

`ListMethods.java` contains examples of invoking various methods on lists.

`Lotto.java` shows how list methods and some of the utility methods from
the `Collections` class can be used to simulate the National Lottery.
