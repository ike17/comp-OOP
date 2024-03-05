/* Example of lack of run-time bounds checking for array access */

#include <stdio.h>

int main()
{
  int x[10], i;

  for (i = 0; i < 10000; ++i) {
    printf("%5d: %d\n", i, x[i]);
  }

  return 0;
}
