// Don't write code like this!

import java.util.Scanner ;

class milestokm {
    public static void main(String[] args ) {
  Scanner INPUT = new Scanner(System.in);
  System.out.print( "Enter distance in miles: ");
      double d=INPUT.nextDouble();
      double Km  = d* 1.6;
    System.out.printf("Distance in km = %.1f\n",
                      Km );
       }
  }
