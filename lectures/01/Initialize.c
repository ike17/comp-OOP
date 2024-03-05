/*
   Demonstration that initialization of variables is not enforced in C.

   Try compiling with and without the -Wall option.
*/

#include <stdio.h>

int main()
{
  int x;

  printf("%d\n", x);    /* compiler is fine with this */

  return 0;
}
