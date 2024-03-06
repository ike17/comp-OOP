# Lecture 9 Examples

`StackQueue.java` contains the code from Slide 7, demonstrating the
different the different APIs of stacks and queues.

`MapCreation.java` shows the syntax for creating maps.  Try compiling
and running this.  Compare the order of insertion of key-value pairs into
the map with the order in which keys and values are displayed.  Then
try changing the implementation used from the map from `HashMap` to
`LinkedHashMap`, recompile and run again.  Then try using `TreeMap` as
the implementation.

`MapExample.java` shows examples of manipulating a map by calling various
methods on it.  `SetExample.java` does the same, but for sets.

`WordFrequency.java` is a practical example of using a map to compute the
frequency of occurrence of words in a document.  Compile this, then try
running it on the provided document `sherlock.txt` (*The Adventures of
Sherlock Holmes*, by Arthur Conan Doyle).

`Money.java` is a class representing a sum of money. It demonstrates
implementations of the `hashCode()`, `equals()` and `compareTo()`
methods (needed to ensure proper behaviour of instances of this class in
collections).  `MoneyTest.java` is a program that tests these methods.

`MoneySort.java` demonstrates how implementing `compareTo()` in the `Money`
class allows us to sort a collection of amounts of money.
