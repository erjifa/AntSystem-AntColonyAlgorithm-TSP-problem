package myas;

import TSP_AMM.AdjMatrixMaker;

import java.io.IOException;

public class ASMain {
    public static void main(String[] args) throws IOException {
        int citycount = 48;
        int epochs = 1000;
        String filepath = "D:\\Java Project\\Computational-Intelligence Course Experiment\\AS\\TSP_source\\att48.tsp";
        // AM
        AdjMatrixMaker amm = new AdjMatrixMaker();
        int[][] am = amm.TSPfile2AM(filepath, citycount);
        double startTime = System.currentTimeMillis();
        AS as = new AS(citycount, 600, 1.0, 2.0, 0.7, 2.0, am);
        as.run(epochs);
        as.report();
        double endTime = System.currentTimeMillis();
        double usedTime = (endTime-startTime)/1000.0;
        System.out.println(usedTime);
    }
}
