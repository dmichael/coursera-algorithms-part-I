 /*
    Exercises: Analysis of Algorithms

    Using the value 303370 for the seed, determine the order of growth of the running time
    of the method call Timing.trial(N, seed) as a function of N. Assume that the running time obeys
    a power law T(N) ~ a N^b. For your answer, enter the constant b with two digits after the decimal
    separator, e.g., 2.34.
  */

public class Analysis {

    public static void main(String[] args) {
        Stopwatch stopwatch = new Stopwatch();
        Timing.trial(10000, 303370);
        StdOut.print(stopwatch.elapsedTime());
    }
}
