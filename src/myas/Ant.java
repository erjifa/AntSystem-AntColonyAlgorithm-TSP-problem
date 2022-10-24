package myas;

import java.util.Random;

public class Ant {
    int[] path;   // 走过的路径
    int[] fpath;  // 标记城市路径是否经过，1经过，0没经过
    int curcity;  // 当前所在的城市（path中索引）
    int len;
    static int citycount;
    static int[][] AM;
    static double[][] tao;
    static Random random = new Random();

    Ant(){
        path = new int[citycount];
        fpath = new int[citycount];
        curcity = 0;
//        path[0] = random.nextInt(citycount);
        path[0] = 0;
        fpath[path[0]] = 1;
    }

    void reset() {
        path = new int[citycount];
        fpath = new int[citycount];
        curcity = 0;
        path[0] = random.nextInt(citycount);
        fpath[path[0]] = 1;
    }

    void callen() {
        len = 0;
        for(int i=0;i<citycount;++i) {
            len += AM[path[i]][path[(i+1)%citycount]];
        }
    }

    void goNext(double alpha, double beta) {
        // imp $p_k(i, j)$
        double[] tmp = new double[citycount];  // for $[tao(i, j)]^alpha * [eta(i, j)]^beta$
        double sum = 0;                        // for $\sum[tao(i, j)]^alpha * [eta(i, j)]^beta$
        for(int i=0;i<citycount;++i) {
            tmp[i] = 0;
            if(path[curcity] == i) continue;         // i = i
            if(fpath[i] == 1) continue;        // i 访问过
            tmp[i] = Math.pow(tao[path[curcity]][i], alpha)
                    * Math.pow(1.0/AM[path[curcity]][i], beta);
            sum += tmp[i];
        }

        for(int i=0;i<citycount;++i) {
            tmp[i] /= sum;
//            System.out.print(tmp[i]+",");
        }
//        System.out.println("sum:"+sum);

        // imp RWS algo selecting a city
        sum = 0;
        double r = random.nextDouble();
        for(int i=0;i<citycount;++i) {
            sum += tmp[i];
            if(sum >= r) {
                path[++curcity] = i;
                fpath[i] = 1;
                break;
            }
        }
    }
}