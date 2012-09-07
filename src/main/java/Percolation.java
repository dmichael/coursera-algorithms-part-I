/**
 * David Michael
 * Algorithms, Part I
 * Programming Assignment 1: Percolation
 * 8/21/12 6:48 PM
 *
 * Notes: A default value of 0 for arrays of integral types is guaranteed by the language spec:
 * Each class variable, instance variable, or array component is initialized with a default value when it is created
 * (§15.9, §15.10) [...]
 * For type int, the default value is zero, that is, 0.
 *
 * This fact is taken advantage of in the initialization of the 1-dimensional storage array
 *
 */
public class Percolation {
    private final int[] array;
    private final int n;
    private final WeightedQuickUnionUF uf;

    // Given: row and column length are the same
    public Percolation(final int N) {
        this.n = N;
        int size = (n * n) + 2;  // add 1 for virtual top and 1 for virtual bottom

        array  = new int[size];
        uf     = new WeightedQuickUnionUF(size);

    }

    public void open(final int row, final int col) {
        int index  = index(row, col);
        int left   = index - 1;
        int right  = index + 1;
        int top    = index - n;
        int bottom = index + n;

        // Open the site
        array[index] = 1;

        // in top row?
        if (row == 1) { uf.union(0, index); }
        // in bottom row?


        // Connect the adjacent sites that are also open
        // Remember, when testing columns, we are accessing a 1-indexed array
        if (col > 1 && isOpen(left))   { uf.union(index, left); }
        if (col < n && isOpen(right))  { uf.union(index, right); }
        if (row > 1 && isOpen(top))    { uf.union(index, top); }
        if (row < n && isOpen(bottom)) { uf.union(index, bottom); }


        if (row == n) {
            // StdOut.printf("(%s,%s: %s) %s, %s, %s, %s --> %s", row,col,index,top, bottom, left, right,
            // array.length);
            uf.union(index, array.length - 1);
        }
    }

    public boolean isOpen(final int i, final int j) {
       return array[index(i, j)] == 1;
    }

    // Is the site connected to the top?
    // This is really an alias to WeightedQuickUnionUF#connected() ...
    public boolean isFull(final int i, final int j) {
        // 0 is the "virtual top", since all row/column calls are 1-indexed
        return uf.connected(0, index(i, j));
    }

    // Is the top connected to the bottom?
    public boolean percolates() {
        return uf.connected(0, array.length - 1);
    }

    // Convert 1-indexed row, column access to 0-indexed storage array index
    // AKA convert 2D array to 1D array
    private int index(final int i, final int j) {
        if (i <= 0 || j <= 0 || i > n || j > n) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        // Let's be sure not to modify the reference
        int i1 = i - 1;
        int j1 = j - 1;

        int oneDimension = (i1 * n + 1) + j1;
        // we add one here to account for the virtual top
        return oneDimension;
    }

    // This method is used by open() since the proper index is already calculated

    private boolean isOpen(final int i) {
        return array[i] == 1;
    }
}
