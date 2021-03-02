package edu.neu.coe.info6205.union_find;

import java.util.Arrays;
import java.util.Random;

public class WQU_Depth {
    public static final String DESCRIPTION = "Weighted Quick Union (Store depth)";
    private final int[] parent;   // parent[i] = parent of i
    private final int[] depth;    // depth[i] = depth of subtree rooted at i
    private int count;            // number of components connected

    /**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own
     * component.
     *
     * @param n the number of sites
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public WQU_Depth(int n) {
        count = n;
        parent = new int[n];
        depth = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            depth[i] = 0;
        }
    }

    public void show() {
        for (int i = 0; i < parent.length; i++) {
            System.out.printf("%d: %d, %d\n", i, parent[i], depth[i]);
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
        while (root != parent[root]) {
            root = parent[root];
        }
        return root;
    }

    @Override
    public String toString() {
        return "Weighted Quick Union with Depth:" + "\n  count: " + count +
                "\n  parents: " + Arrays.toString(parent) +
                "\n  depths: " + Arrays.toString(depth);
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
        // make the root with the deepest leaf be the new root of other

        if (depth[rootP] < depth[rootQ]) {
            parent[rootP] = rootQ;

        } else {
            parent[rootQ] = rootP;
            if (depth[rootP] == depth[rootQ]) {
                depth[rootP]++;
            }
        }
        count--;
    }

    /**
     * Update the depth of all nodes/leaves of this site
     * @param p the integer representing one site
     */
    public void updateDepth(int p) {
        for (int i = 0; i < parent.length; i++) {
            if (parent[i] == p && i != p) {
                depth[i]++;
            }
        }
    }

    /**
     * Calculate the depth of this node/subtree
     * @param p the integer representing one site
     * @return the depth of the deepest leaf of this root
     */
    public int deepestLeaf(int p) {
        int root = p;
        int result = 0;
        for (int i = 0; i < depth.length; i++) {
            if (find(i) == find(root) && i != root) {
                if (depth[i] > result) {
                    result = depth[i];
                }
            }
        }

        return result;
    }

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
    public static void getCount(WQU_Depth depth_count) {
        int countCon = 0;
        while (depth_count.count > 1) {
            Random random = new Random();
            int p = random.nextInt(depth_count.parent.length);
            int q = random.nextInt(depth_count.parent.length);
            depth_count.union(p, q);
            countCon++;
        }
    }

    public static void main(String[] args) {
        int countCon = 0;
        if (args.length == 0)
            throw new RuntimeException("Need input number of sites to create the UF_HWQUPC");
        int n = Integer.parseInt(args[0]);
        System.out.println("Number of sites n = " + n);
        WQU_Depth d = new WQU_Depth(n);
        while (d.count > 1) {
            Random random = new Random();
            int p = random.nextInt(n);
            int q = random.nextInt(n);

            System.out.println("union (" + p + ", " + q + ")");
            d.union(p, q);
            countCon++;
        }
        System.out.println(d);
        d.show();
        System.out.println("With n=" + n + " sites , generated= " + countCon + " connections");
    }
}
