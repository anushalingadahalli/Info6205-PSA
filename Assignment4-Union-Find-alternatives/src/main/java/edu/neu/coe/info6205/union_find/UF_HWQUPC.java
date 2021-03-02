/**
 * Original code:
 * Copyright © 2000–2017, Robert Sedgewick and Kevin Wayne.
 * <p>
 * Modifications:
 * Copyright (c) 2017. Phasmid Software
 */
package edu.neu.coe.info6205.union_find;

import java.util.Arrays;
import java.util.Random;

/**
 * Height-weighted Quick Union with Path Compression
 */
public class UF_HWQUPC implements UF {
    /**
     * Ensure that site p is connected to site q,
     *
     * @param p the integer representing one site
     * @param q the integer representing the other site
     */
    public void connect(int p, int q) {
        if (!isConnected(p, q)) union(p, q);
    }

    /**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own
     * component.
     *
     * @param n               the number of sites
     * @param pathCompression whether to use path compression
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public UF_HWQUPC(int n, boolean pathCompression) {
        count = n;
        parent = new int[n];
        height = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            height[i] = 1;
        }
        this.pathCompression = pathCompression;
    }

    /**
     * Initializes an empty union–find data structure with {@code n} sites
     * {@code 0} through {@code n-1}. Each site is initially in its own
     * component.
     * This data structure uses path compression
     *
     * @param n the number of sites
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public UF_HWQUPC(int n) {
        this(n, true);
    }

    public void show() {
        for (int i = 0; i < parent.length; i++) {
            System.out.printf("%d: %d, %d\n", i, parent[i], height[i]);
        }
    }

    /**
     * Returns the number of components.
     *
     * @return the number of components (between {@code 1} and {@code n})
     */
    public int components() {
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
        //int root = p;
        // TO BE IMPLEMENTED
        while (p != parent[p])
        {
            if (this.pathCompression) {
                doPathCompression(p);
            }
            p = parent[p];
        }
        int root = p;
        return root;
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
        // CONSIDER can we avoid doing find again?
        mergeComponents(find(p), find(q));
        count--;
    }

    @Override
    public int size() {
        return parent.length;
    }

    /**
     * Used only by testing code
     *
     * @param pathCompression true if you want path compression
     */
    public void setPathCompression(boolean pathCompression) {
        this.pathCompression = pathCompression;
    }

    @Override
    public String toString() {
        return "UF_HWQUPC:" + "\n  count: " + count +
                "\n  path compression? " + pathCompression +
                "\n  parents: " + Arrays.toString(parent) +
                "\n  heights: " + Arrays.toString(height);
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    private void updateParent(int p, int x) {
        parent[p] = x;
    }

    private void updateHeight(int p, int x) {
        height[p] += height[x];
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

    private final int[] parent;   // parent[i] = parent of i
    private final int[] height;   // height[i] = height of subtree rooted at i
    private int count;  // number of components
    private boolean pathCompression;

    private void mergeComponents(int i, int j) {
        // TO BE IMPLEMENTED make shorter root point to taller one
        int p = find(i);
        int q = find(j);
        if(p == q) {
            return;
        }
     if(height[p]<height[q])
     {
      //parent[i]=j;
         updateParent(p,q);
      height[q]+=height[p];
     }
     else
    {
      //parent[j]=i;
        updateParent(q,p);
      height[p]+=height[q];
    }
    }

    /**
     * This implements the single-pass path-halving mechanism of path compression
     */
    private void doPathCompression(int i) {
        // TO BE IMPLEMENTED update parent to value of grandparent

            parent[i] = parent[parent[i]];


    }
    public static int count(int n)
    {
        Random random=new Random();

        UF_HWQUPC uf = new UF_HWQUPC(n);

        int  counts = 0;

        loopthis:
        while ( uf.count> 1)
        {

            int p = random.nextInt(n);                               //Generate random number pairs p and q
            int q = random.nextInt(n);
            if(p==q) {continue loopthis;}
            if (!uf.connected(p, q))                                 //Check if the pairs are connected
            {
                uf.union(p, q);                                      //If not connected then call union method for the pair
            }
            counts++;                                                //Count the number of connected components made


        }

        return counts;
    }
    public static void main(String[] args) {
        int runs = 100; 											//Number of times the count() function called for value of n
        double coefficient = 0;										//Variable to average the value of m/n*(log(n))
        int expCount = 0;										    //Number of experiments conducted for these runs

        for(int i = 100; i <= 5000; i= i+250) {                      //iterate over for n = 100 to n = 5000 incrementing by 250

            int totalCount = 0;			                            // cumulative sum of generated pairs from count(n) function
            for(int j = 0; j < runs; j++) {
                totalCount+= count(i);

            }

            expCount++;
            int avg = totalCount/runs;		                        //Average value of pairs generated over the number of runs

            double logFactor = Math.log(i) * i;
            coefficient += avg/logFactor;

            System.out.println("For n= " + i + "  number of generated pairs = " + avg + " for "  + runs +  " runs");
            System.out.println("Coefficient for n=" + i + "  and m = " + avg + " is= " +  (avg/logFactor) + "\n");
        }

        System.out.println("Average value of the coefficient (m/n*log(n)) is=" + (coefficient/expCount));
    }
    /**
     * Gets the maximum depth of the tree once all nodes are connected
     *
     * @return the maximum depth of the tree
     */
    public int getMaximumDepth() {

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

}
