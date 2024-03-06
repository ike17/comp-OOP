// Examples of array initialization

class ArrayInit {
  public static void main(String[] args) {

    // Legal initialisations for one-dimensional arrays

    int[] p = null;

    int[] q = new int[5];   // elements will be 0

    int[] r = { 2, 4, 6, 8, 10 };

    // This won't work (type mismatch):

    //int[] bad = { 0.1, 0.3, 0.5, 0.7 };

    // Legal initializations for two-dimensional arrays

    int[][] w = null;

    int[][] x = new int[3][4];

    int[][] y = { { 1, 2, 3, 4 },
                  { 2, 4, 6, 8 },
                  { 7, 5, 3, 1 } };

    System.out.println(y[2][1]);   // what does this print?
  }
}
