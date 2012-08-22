import com.sun.xml.internal.ws.util.StringUtils;

/**
 * Author: dmichael
 * 8/21/12 6:48 PM
 *
 * Notes: A default value of 0 for arrays of integral types is guaranteed by the language spec:
 * Each class variable, instance variable, or array component is initialized with a default value when it is created (§15.9, §15.10) [...] For type int, the default value is zero, that is, 0.
 *
 *   0
 * 1 2 3
 * 4 5 6
 * 7 8 9
 *  10
 *
 */
public class Percolation {
    // private int[][] matrix;
    private int[] array;
    private int n;  // row and column length are the same
    private WeightedQuickUnionUF uf;

    public Percolation(int n){
        this.n = n;
        array  = new int[n * n]; // add 1 for virtual top and 1 for virtual bottom
        uf     = new WeightedQuickUnionUF(n * n);
    }


    // Tiny methods to convert row, column to index
    private int index  (int i, int j) { return i * n + j; }
    private int left   (int i, int j) { return (i * n - 1) + j; }
    private int right  (int i, int j) { return (i * n + 1) + j; }
    private int top    (int i, int j) { return (i * n) + (j - n); }
    private int bottom (int i, int j) { return (i * n) + (j + n); }

    // 1-indexed access
    public void open(int i, int j){
        i = i - 1;
        j = j - 1;
        // open the site
        array[index(i, j)] = 1;

        // connect the adjacent sites that are also open

        int left   = left(i, j);
        int right  = right(i,j);
        int top    = top(i,j);
        int bottom = bottom(i,j);
        int index  = index(i, j);

        StdOut.printf("left %s", left);
        if(j > 0 && isOpen(left)) uf.union(index, left);

        StdOut.printf("right %s", right);
        if(j < n - 1 && isOpen(right)) uf.union(index, right);

        StdOut.printf("top %s", top);
        if(i > 0 && isOpen(top)) uf.union(index, top);

        StdOut.printf("bottom %s", bottom);
        if(i < n - 1 && isOpen(bottom)) uf.union(index, bottom);
    }

    public boolean isOpen(int i){
        return array[i] == 1;
    }

    public boolean isOpen(int i, int j){
        StdOut.print(index(i, j));
        StdOut.print("-");
        StdOut.print(array[index(i, j)] == 1);
       return array[index(i, j)] == 1;
    }

    public boolean isFull(int i, int j){
        // 0 is the "virtual top", since all row/column calls are 1-indexed
        return uf.connected(0, index(i,j));
    }

    public boolean percolates(){
        return true;
    }

    private void printArray(){
        for(int i : array){
            StdOut.print(i);
        }
    }

    public static void main(String[] args){
        Percolation percolation = new Percolation(3);
        percolation.open(3,3);
        percolation.open(2,3);
        StdOut.print(percolation.uf.connected(5,8));

    }
}
