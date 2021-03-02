package edu.neu.coe.info6205.union_find;

import edu.neu.coe.info6205.util.Benchmark_Timer;

import java.util.Random;
import java.util.function.Supplier;

public class UF_Benchmark {
    public static void main(String[] args) {

        Benchmark_Timer<Integer> benchmarkWQU_Depth = new Benchmark_Timer<>("Benchmark Test for Union Find Experiment", null, (a) -> getCountWQU_Depth(a), null);
        Benchmark_Timer<Integer> benchmarkWQUPCR = new Benchmark_Timer<>("Benchmark Test for Union Find Experiment", null, (a) -> getCountWQUPCR(a), null);
        Benchmark_Timer<Integer> benchmarkWQUPC = new Benchmark_Timer<>("Benchmark Test for Union Find Experiment", null, (a) -> getCountWQUPC(a), null);
        Benchmark_Timer<Integer> benchmarkHWQU = new Benchmark_Timer<>("Benchmark Test for Union Find Experiment", null, (a) -> getCountHWQU(a), null);
        Benchmark_Timer<Integer> benchmarkWQU_Size = new Benchmark_Timer<>("Benchmark Test for Union Find Experiment", null, (a) -> getCountWQU_Size(a), null);

        int runs = 10;
        int start = 100;
        int end = 100000;

        for(int i = start; i < end; i = i*2) {

            int index = i;

            //Generate supplier to provide the value of n
            Supplier<Integer> supplier = (() -> index);

            //Weighted Quick Union with Depth
            double wqu_Depth_Time = benchmarkWQU_Depth.runFromSupplier(supplier, runs);
            System.out.println("For nodes= " + i + "time taken to connect all nodes in weighted quick union(with depth) = " + wqu_Depth_Time);

            //Weighted Quick Union with Size
            double wqu_Size_Time = benchmarkWQU_Size.runFromSupplier(supplier, runs);
            System.out.println("For nodes= " + i + " time taken to connect all nodes in weighted quick union(with size) = " + wqu_Size_Time);

           //Height Weighted Quick Union
            double hwquTime = benchmarkHWQU.runFromSupplier(supplier, runs);
            System.out.println("For nodes= " + i + " time taken to connect all nodes in height weighted quick union = " + hwquTime);

            //Weighted Quick Union with One Pass Path Compression (Grandparent fix)
            double wqupcTime = benchmarkWQUPC.runFromSupplier(supplier, runs);
            System.out.println("For nodes=" + i + "time taken to connect all nodes in weighted quick union with one pass path compression = " + wqupcTime);

            //Weighted Quick Union with Two Pass Path Compression (Connect intermediate node to root)
            double wqupc_2Time = benchmarkWQUPCR.runFromSupplier(supplier, runs);
            System.out.println("For nodes= " + i + "time taken to connect all nodes in weighted quick union with two pass path compression = " + wqupc_2Time);

            System.out.println("\n" + "-------------------------------------------------------------------------------------------------------------------------" + "\n");
        }
        for(int i = start; i < end; i = i*2) {
            /* * for getting depths */

            int wquDepth = 0;
            int hwquDepth = 0;
            int wqupcDepth = 0;
            int wqupc_2Depth = 0;

            for(int j = 0; j < 1; j++) {
                getCountWQU_Depth(i);
                wquDepth += ufClientDepth.getMaximumDepth();
                getCountHWQU(i);
                hwquDepth += ufClientHeight.getMaximumDepth();
                getCountWQUPC(i);
                wqupcDepth += ufClientPC.getMaximumDepth();
                getCountWQUPCR(i);
                wqupc_2Depth += ufClientPC.getMaximumDepth();
            }

            System.out.println("For nodes: " + i + " the average depth in weighted quick union = " + wquDepth);
            System.out.println("For nodes: " + i + " the average depth in height weighted quick union =  " + hwquDepth);
            System.out.println("For nodes: " + i + " the average depth in weighted quick union with one pass path compression = " + wqupcDepth);
            System.out.println("For nodes: " + i + " the average depth in weighted quick union with two pass path compression =  " + wqupc_2Depth);
            System.out.println("\n" + "------------------------------------------------------------------------------------------------------------" + "\n");
        }

    }

    /**
     * Method to conduct union find experiment using weighted quick union find with depth
     *
     * @param n Number of sites
     */
    public static void getCountWQU_Depth(int n) {
        ufClientDepth = new WQU_Depth(n);
        Random random = new Random();
        while(ufClientDepth.count()>1) {
            int p = random.nextInt(n);
            int q = random.nextInt(n);
            if(!ufClientDepth.connected(p, q)) {

                ufClientDepth.union(p, q);
            }
        }

    }
    /**
     * Method to conduct union find experiment using weighted quick union find with size
     *
     * @param n Number of sites
     */
    public static void getCountWQU_Size(int n) {
        ufClientSize = new WQUPC(n);
        Random random = new Random();
        while(ufClientSize.count()>1) {
            int p = random.nextInt(n);
            int q = random.nextInt(n);
            if(!ufClientSize.connected(p, q)) {
                ufClientSize.union(p, q);
            }
        }

    }

    /**
     * Method to conduct union find experiment using height weighted quick union find
     *
     * @param n Number of sites
*/
    public static void getCountHWQU(int n) {
        ufClientHeight = new UF_HWQUPC(n, false);
        Random random = new Random();
        while(ufClientHeight.components()>1) {
            int p = random.nextInt(n);
            int q = random.nextInt(n);
            if(!ufClientHeight.connected(p, q)) {

                ufClientHeight.union(p, q);
            }
        }
    }

    /**
     * Method to conduct union find experiment using weighted quick union find with
     * one pass path compression
     *
     * @param n Number of sites
     */
    public static void getCountWQUPC(int n) {
        ufClientPC = new WQUPC_2(n, true, 2);
        Random random = new Random();
        while(ufClientPC.count()>1) {
            int p = random.nextInt(n);
            int q = random.nextInt(n);
            if(!ufClientPC.connected(p, q)) {

                ufClientPC.union(p, q);
            }
        }

    }


    /**
     * Method to conduct union find experiment using weighted quick union find with
     * two pass path compression
     *
     * @param n Number of sites
     */
    public static void getCountWQUPCR(int n) {
        ufClientPC = new WQUPC_2(n, true, 1);
        Random random = new Random();
        while(ufClientPC.count()>1) {
            int p = random.nextInt(n);
            int q = random.nextInt(n);
            if(!ufClientPC.connected(p, q)) {

                ufClientPC.union(p, q);
            }
        }
    }

    static private WQU_Depth ufClientDepth;
    static private WQUPC ufClientSize;
    static private UF_HWQUPC ufClientHeight;
    static private WQUPC_2 ufClientPC;
}
