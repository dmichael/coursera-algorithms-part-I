/**
 * David Michael
 * Algorithms, Part I
 * Programming Assignment 1: Percolation
 * 8/23/12 7:03 PM
 */
public class PercolationStats {
    // Fraction of openSites sites
    private int[] openSites;
    private int n;
    /**
     * Perform t individual experiments on n * n grid:
     * Choose a site (row i, column j) uniformly at random among all blocked sites
     * Open the site
     */

    // The constructor should throw a java.lang.IllegalArgumentException if either N ≤ 0 or T ≤ 0.

    public PercolationStats(final int n, final int t) {
        if (n <= 0 || t <= 0) { throw new IllegalArgumentException(); }

        openSites  = new int[t];
        this.n = n;

        for (int i = 0; i < t; i++) {
            int opened = 0;

            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {

                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;

                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    opened++;
                }
            }
            this.openSites[i] = opened;
        }
    }

    /**
     * By repeating this computation experiment T times and averaging the results, we obtain a more accurate estimate
     * of the percolation threshold. Let xt be the fraction of open sites in computational experiment t.
     * The sample mean μ provides an estimate of the percolation threshold;
     * the sample standard deviation σ measures the sharpness of the threshold.
     */

    public double mean() {
        return StdStats.mean(fractionOfOpenSites());
    }

    public double stddev() {
        return StdStats.stddev(fractionOfOpenSites());
    }

    private double[] confidence(int T) {
        double[] ninetyFive = new double[2];
        double mean = mean();
        // Why in this calculation is the stddev not the sqrt here?
        double calc = (stddev() * 1.96) / Math.sqrt(T);
        ninetyFive[0] = mean - calc;
        ninetyFive[1] = mean + calc;
        return ninetyFive;
    }

    private double[] fractionOfOpenSites() {
        double[] x = new double[openSites.length];

        for (int i = 0; i <= openSites.length - 1; i++) {
            x[i] = ((double) openSites[i] / (n * n));
        }

        return x;
    }

    public static void main(final String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(N, T);

        double mean         = stats.mean();
        double stddev       = stats.stddev();
        double[] confidence = stats.confidence(T);

        StdOut.printf("mean:                    %f\n", mean);
        StdOut.printf("stddev:                  %f\n", stddev);
        StdOut.printf("95%% confidence interval: %s, %s", confidence[0], confidence[1]);
        StdOut.print("\n");
    }
}
