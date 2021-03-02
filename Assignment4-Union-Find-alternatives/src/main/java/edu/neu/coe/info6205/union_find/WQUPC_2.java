package edu.neu.coe.info6205.union_find;

import java.util.Arrays;
import java.util.Random;

public class WQUPC_2 {
    public static final String DESCRIPTION = "Weighted Quick Union with Pass Compression";
    private final int[] parent;   // parent[i] = parent of i
    private final int[] size;     // size[i] = size of subtree rooted at i
    private int count;           // number of components connected
    private boolean pathCompression;
    private int typePathCompression;

    /**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own
     * component.
     *
     * @param n the number of sites
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public WQUPC_2(int n, boolean pathCompression) {
        count = n;
        parent = new int[n];
        size = new int[n];
        this.pathCompression = pathCompression;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    /**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own
     * component.
     *
     * @param n the number of sites
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public WQUPC_2(int n, boolean pathCompression, int typePathCompression) {
        count = n;
        parent = new int[n];
        size = new int[n];
        this.pathCompression = pathCompression;
        this.typePathCompression = typePathCompression;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public void show() {
        for (int i = 0; i < parent.length; i++) {
            System.out.printf("%d: %d, %d\n", i, parent[i], size[i]);
        }
    }

    /**
     * Returns the number of components.
     *
     * @return the number of components (between {@code 1} and {@code n})
     */
    public int count() {
        return count;
    }

    /**
     * Returns the component identifier for the component containing site {@code p}.
     *
     * @param p the integer representing one site
     * @return the component identifier for the component containing site {@code p}
     * @throws IllegalArgumentException unless {@code 0 <= p < n}
     */
    public int find(int p) {
        validate(p);
        int root = p;
        if(pathCompression) {
            //If  two pass path compression
            if(typePathCompression == 1) {
                while (root != parent[root]) {
                    root = parent[root];
                }
                //Connect intermediate nodes to root
                while (p != root) {
                    int newp = parent[p];
                    parent[p] = root;
                    p = newp;
                }
            }
            //else If one pass path compression
            else {
                while (root != parent[root]) {
                    parent[root] = parent[parent[root]];
                    root = parent[root];
                }
            }
        }
        //If not path compression
        else {
            while (root != parent[root]) {
                root = parent[root];
            }
        }
        return root;
    }

    @Override
    public String toString() {
        return "WQUPC_2 : weighted quick union with path compression" +
                "\n count: " + count +
                "\n parents: " + Arrays.toString(parent) +
                "\n sizes: " + Arrays.toString(size);
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    /**
     * Returns true if the the two sites are in the same component.
     *
     * @param p the integer representing one site
     * @param q the integer representing the other site
     * @return {@code true} if the two sites {@code p} and {@code q} are in the same component;
     * {@code false} otherwise
     * @throws IllegalArgumentException unless
     *                                  both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * Merges the component containing site {@code p} with the
     * the component containing site {@code q}.
     *
     * @param p the integer representing one site
     * @param q the integer representing the other site
     * @throws IllegalArgumentException unless
     *                                  both {@code 0 <= p < n} and {@code 0 <= q < n}
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }
    /**
     * Used only by testing code
     *
     * @param i the component
     * @return the parent of the component
     */
    private int getParent(int i) {
        return parent[i];
    }
    /**
     * Returns the maximum depth of the tree.
     *
     * @return the maximum depth of the tree
     */
    public int getMaximumDepth() {
        if(this.count() > 1){
            return -1;
        }

        int maxDepth = Integer.MIN_VALUE;
        int currentDepth = 0;

        for(int i = 0; i < parent.length; i++) {
            currentDepth = 0;
            int j = i;
            while (j!= parent[j]) {
                j = parent[j];
                currentDepth++;
            }
            maxDepth = Math.max(maxDepth, currentDepth);
        }
        return maxDepth;
    }
    public static void getCount(WQUPC_2 wq) {
        int conCount = 0;
        while (wq.count > 1) {
            Random random = new Random();
            int p = random.nextInt(wq.parent.length);
            int q = random.nextInt(wq.parent.length);
            wq.union(p, q);
            conCount++;
        }
    }

    public static void main(String[] args) {
        int conCount = 0;
        if (args.length == 0)
            throw new RuntimeException("Need input number of sites to create the WQUPC");
        int n = Integer.parseInt(args[0]);
        System.out.println("For Number of sites n = " + n);
        WQUPC_2 wq = new WQUPC_2(n,false);
        while (wq.count > 1) {
            Random random = new Random();
            int p = random.nextInt(n);
            int q = random.nextInt(n);

            System.out.println("union (" + p + ", " + q + ")");
            wq.union(p, q);
            conCount++;
        }

        System.out.println(wq);
        wq.show();
        System.out.println("With n=" + n + " sites , generates = " + conCount + " connections");
    }
}
