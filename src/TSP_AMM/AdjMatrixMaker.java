package TSP_AMM;

import java.io.*;

import static java.lang.Math.pow;

public class AdjMatrixMaker {
    public int[][] TSPfile2AM(String TSPfilename, int num) throws IOException {
        // 读取 TSP 文件到二维数组 tsparr 中 （File.separator路径分割符）
        //     如 r 6734 1453 => tsp{6734, 1453} 第 r 行
        File file = new File(TSPfilename);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        int[][] tsparr = new int[num][2];
        String[] tmp;
        int r = 0, c = 0;
        String line;
        while((line = br.readLine()) != null){
            tmp = line.split(" ");
            for(int i=0;i<3;++i){
                if(i==0) continue;
                tsparr[r][c++%2] = Integer.parseInt(tmp[i]);
            }
            r++;
        }
        br.close();

        // 求邻接矩阵 AM
        int[][] AM = new int[num][num];
        for(r=0;r<num;++r){
            for(c=r+1;c<num;++c){
                AM[r][c] = AM[c][r] = (int)(Math.sqrt(
                        pow(tsparr[r][0] - tsparr[c][0], 2) +
                        pow(tsparr[r][1] - tsparr[c][1], 2)
                ));
            }
        }
        return AM;
    }
}
