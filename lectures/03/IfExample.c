/*
   Example showing how C isn't very strict about the tests
   performed by if statements.  Essentially a non-zero value
   is treated as true, whereas zero is treated as false.
*/

#include <stdio.h>

int main() {
  int x = 42, y = 0;

  if (x) {
    printf("x is non-zero\n");
  }
  else {
    printf("x is zero\n");
  }

  if (y) {
    printf("y is non-zero\n");
  }
  else {
    printf("y is zero\n");
  }

  return 0;
}
