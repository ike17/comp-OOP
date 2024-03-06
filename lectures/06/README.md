# Lecture 6 Examples

`BadClock.java` is a bad implementation of a class to represent a clock.
The class is rather like a C struct, in that users are able to freely
the fields and change their values (due to the use of the `public`
modifier).  This means that there is no way of preventing a `Clock` object
from getting into an invalid state.

`Clock.java` (first seen in Lecture 5) is a better implementation, with
private fields and a set of public methods that interact with those fields
in a controlled manner.  The values for hours, minutes and seconds are
validated when a `Clock` object is created, and thereafter cannot be
changed by the class user, except by calling the `tick()` method.  This
means that a `Clock` object can never get into an invalid state.  The class
also defines a range of other methods that make it useful - read the
comments in the code for more explanation.

`ClockDemo.java` is a program that uses `Clock` to simulate a ticking clock.

`Rectangle.java` and `RectangleTest.java` contain the code for the quiz
questions that appeared in the lecture.
