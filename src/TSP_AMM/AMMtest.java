package TSP_AMM;

import java.io.IOException;

public class AMMtest {
    public static void main(String[] args) throws IOException {
        AdjMatrixMaker amm = new AdjMatrixMaker();
        String filepath = "../../TSP_source/att48.tsp";
        int[][] am = amm.TSPfile2AM(filepath, 48);
        for (int i = 0; i < 48; i++) {
            for (int j = 0; j < 48; j++) {
                System.out.printf("%d,", am[i][j]);
            }
            System.out.println();
        }
    }
}
