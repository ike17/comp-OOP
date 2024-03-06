# Lecture 5 Examples

## Clock.java

`Clock.java` is a class to represent a 24-hour clock.

A `Clock` object's internal state comprises three fields, representing
the time on the clock in hours, minutes and seconds.

A `Clock` object can be constructed in three different ways, represented
by its three overloaded constructors.

The current time on a `Clock` object can be queried by calling its
'getter' methods: `getHours()`, `getMinutes()`, `getSeconds()`.

A `Clock` object has a `display()` method that displays current time
as text on the standard output stream.

The time on a `Clock` object can be advanced by one second by calling
its `tick()` method.  The `run()` method simulates a ticking clock
by calling `display()` and `tick()` once every second.

## ClockDemo.java

`ClockDemo.java` is a program that executes the `run()` method of a
`Clock` object in a separate thread.

## ErrorDemo.java

`ErrorDemo.java` demonstrates the run-time error that occurs when
attempting to create an invalid `Clock` object.
