/*
 * Copyright (c) 2017. Phasmid Software
 */

package edu.neu.coe.info6205.randomwalk;

import java.util.Random;
import java.util.Scanner;

public class RandomWalk {

    private int x = 0;
    private int y = 0;


    private final Random random = new Random();



    /**
     * Private method to move the current position, that's to say the drunkard moves
     *
     * @param dx the distance he moves in the x direction
     * @param dy the distance he moves in the y direction
     */
    private void move(int dx, int dy) {
        // TODO you need to implement this

        x = x + dx;
        y = y + dy;
    }




    /**
     * Perform a random walk of m steps
     *
     * @param m the number of steps the drunkard takes
     */
    private void randomWalk(int m) {
        // TO BE IMPLEMENTED
        for (int i = 0; i < m; i++) {
            randomMove();
        }
    }


    /**
     * Private method to generate a random move according to the rules of the situation.
     * That's to say, moves can be (+-1, 0) or (0, +-1).
     */
    private void randomMove() {
        /*boolean ns = random.nextBoolean();
        int step = random.nextBoolean() ? 1 : -1;
        move(ns ? step : 0, ns ? 0 : step);*/


        //if number = 0(north), 1(south), 2(east), 3(west) (person will take step of length = 2)

        int randomNo = random.nextInt(4);

        if     (randomNo==0) { move(0,1);  }
        else if(randomNo==1) { move(0,-1); }
        else if(randomNo==2) { move(1,0);  }
        else                 { move(-1,0); }
    }

    /**
     * Method to compute the distance from the origin (the lamp-post where the drunkard starts) to his current position.
     *
     * @return the (Euclidean) distance from the origin to the current position.
     */
    public double distance() {
        // TO BE IMPLEMENTED
        double distance = 0;
        distance = Math.pow(x, 2) + Math.pow(y,2);
        distance = Math.sqrt(distance);
        return distance;

    }

    /**
     * Perform multiple random walk experiments, returning the mean distance.
     *
     * @param m the number of steps for each experiment
     * @param n the number of experiments to run
     * @return the mean distance
     */
    public static double randomWalkMulti(int m, int n) {
        double totalDistance = 0;
        for (int i = 0; i < n; i++) {
            RandomWalk walk = new RandomWalk();
            walk.randomWalk(m);
            totalDistance = totalDistance + walk.distance();
        }
        return totalDistance / n;
    }

    public static void main(String[] args) {

       /* if (args.length==0)
            throw new RuntimeException("Syntax: RandomWalk steps [experiments]");
Integer.parseInt(args[0]);*/

       /* int m, n;
        //int m = 60;
        //int n = 6;
        //accept the input from user for number of steps and no of experiments
        Scanner reader = new Scanner(System.in);
        System.out.print("Enter number of steps: ");
        int noOfSteps = reader.nextInt();
        System.out.print("Enter number of experiments to run: ");
        int noOfExperiments = reader.nextInt();
        reader.close();
        m = noOfSteps;
        n = noOfExperiments;
        for (int i = 1; i <= n; i++) {
            double meanDistance = randomWalkMulti(m, n);
            //System.out.println("Steps=" + m + " Experiment times= " + n + " Distance= " + meanDistance);
            System.out.println("MeanDistance=" +meanDistance);
            m = m + 10;
        }*/
        if (args.length == 0)
            throw new RuntimeException("Syntax: RandomWalk steps [experiments]");
        int m = Integer.parseInt(args[0]);
        int n = 30;
        if (args.length > 1) n = Integer.parseInt(args[1]);
        double meanDistance = randomWalkMulti(m, n);
        System.out.println(m + " steps: " + meanDistance + " over " + n + " experiments");

    }
    }


