# Utility Classes Example for Lecture 4

Compare this with `MilesToKm.java` in the directory above.
This directory contains a version of `MilesToKm.java` from the directory
above, in which functionality has been spread across multiple classes.

The code to obtain user input is now in the `Input` class and the
code to do unit conversion is now in the `Conversion` class.  The program
is able to use these classes without any imports, because all the classes
are in the same directory and Java considers them all to be part of
the same package.

The `Input` class can now become a utility class, home to a number of
different static methods that perform different input operations.  Similarly,
`Conversion` can now become a utility class containing various static
method to do conversions between different units.  These classes can then
be compiled into a **library** that can be made available to other programs.
