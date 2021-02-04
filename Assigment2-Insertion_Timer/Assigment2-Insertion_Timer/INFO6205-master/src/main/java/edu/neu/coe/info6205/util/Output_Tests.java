package edu.neu.coe.info6205.util;

import edu.neu.coe.info6205.sort.simple.InsertionSort;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Output_Tests {

    public static void main(String[] args) {

        Output_Tests.testMethod(1,100);
        Output_Tests.testMethod(1,200);
        Output_Tests.testMethod(1,400);
        Output_Tests.testMethod(1,800);
        Output_Tests.testMethod(1,1600);
        Output_Tests.testMethod(1,3200);
        Output_Tests.testMethod(1,6400);
        Output_Tests.testMethod(1,12800);
        Output_Tests.testMethod(1,25600);
        System.out.println();
        Output_Tests.testMethod(2,100);
        Output_Tests.testMethod(2,200);
        Output_Tests.testMethod(2,400);
        Output_Tests.testMethod(2,800);
        Output_Tests.testMethod(2,1600);
        Output_Tests.testMethod(2,3200);
        Output_Tests.testMethod(2,6400);
        Output_Tests.testMethod(2,12800);
        Output_Tests.testMethod(2,25600);
        System.out.println();
        Output_Tests.testMethod(3,100);
        Output_Tests.testMethod(3,200);
        Output_Tests.testMethod(3,400);
        Output_Tests.testMethod(3,800);
        Output_Tests.testMethod(3,1600);
        Output_Tests.testMethod(3,3200);
        Output_Tests.testMethod(3,6400);
        Output_Tests.testMethod(3,12800);
        Output_Tests.testMethod(3,25600);
        System.out.println();
        Output_Tests.testMethod(4,100);
        Output_Tests.testMethod(4,200);
        Output_Tests.testMethod(4,400);
        Output_Tests.testMethod(4,800);
        Output_Tests.testMethod(4,1600);
        Output_Tests.testMethod(4,3200);
        Output_Tests.testMethod(4,6400);
        Output_Tests.testMethod(4,12800);
        Output_Tests.testMethod(4,25600);
        System.out.println();
    }
    public static void testMethod(int arrayType,int n) {

        InsertionSort<Integer> sorter = new InsertionSort<Integer>();
        Supplier<Integer[]> sup1 = () -> orderedArray(n);
        Supplier<Integer[]> sup2 = () -> partialOrderArray(n);
        Supplier<Integer[]> sup3 = () -> randomArray(n);
        Supplier<Integer[]> sup4 = () -> reversedArray(n);
        String arrayTypeName=null;
        Consumer<Integer[]> con = (t)->{sorter.sort(t, 0,t.length);};
        Supplier<Integer[]> sup =sup1;
        if(arrayType==1)
        {
            sup=sup1;
            arrayTypeName="Ordered Array";
        }else if(arrayType==2)
        {
            sup=sup2;
            arrayTypeName="PartiallyOrdered Array";
        }else if(arrayType==3)
        {
            sup=sup3;
            arrayTypeName="Random Array";
        }else if(arrayType==4)
        {
            sup=sup4;
            arrayTypeName="Reversed Array";
        }
        Benchmark_Timer<Integer[]> bt =new Benchmark_Timer<Integer[]>("Benchmark test for InsertionSort ",con);
        double meantime =bt.runFromSupplier(sup, 10);
        //System.out.println("meanTime(n="+n+")"+meantime);
        System.out.println("For "+ arrayTypeName + " of input size: " + n + " insertion sort takes meantime of :  " + meantime);
        System.out.println("---------------------------------------------------------------------------------------------------");
    }
    public static Integer[] reversedArray(int n)
    {
        Integer[] in = new Integer[n];
        int count =1;
        for(int i=0;i<n;i++)
        {
            in[i]=n+2-count;
            count++;
        }
        return in;
    }
    public static Integer[] orderedArray(int n)
    {
        Integer[] in = new Integer[n];
       for(int i=0;i<n;i++)
        {
            in[i]=i+1;
        }
        return in;

    }
    public static Integer[] randomArray(int n)
    {
        Random r= new Random();
        Integer[] in = new Integer[n];
        for(int i=0;i<n;i++)
        {
            in[i]=r.nextInt(n);
        }
        return in;
    }
    public static Integer[] partialOrderArray(int n) {
        Random r = new Random();
        Integer[] in = new Integer[n];
        for (int i = 0; i < n / 4; i++) {
            in[i] = i + 1;
        }
        for (int i = n / 4; i < n * 3 / 4; i++) {
            in[i] = r.nextInt(n);
        }
        for (int i = n * 3 / 4; i < n; i++) {
            in[i] = i + 1;
        }
        return in;

    }
}

