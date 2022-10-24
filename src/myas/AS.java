package myas;

public class AS {
    Ant[] ants;
    int[] bestpath;  // 最优路径
    int bestlen;     // 最有路径长度
    int citycount;   // 城市数量
    int antscount;   // 蚂蚁数量
    double alpha;
    double beta;
    double rho;
    double q;
    int[][] AM;      // 城市距离邻接矩阵
    double[][] tao;  // 信息素矩阵（注意这里的tao更新后，需要更新ant类里的tao）

    AS(int citycount, int antscount, double alpha, double beta, double rho, double q, int[][] AM){
        this.citycount = citycount;
        this.antscount = antscount;
        this.alpha = alpha;
        this.beta = beta;
        this.rho = rho;
        this.q = q;
        this.AM = AM;
        bestlen = Integer.MAX_VALUE;
        bestpath = new int[citycount];

        init_tao();

        ants = new Ant[antscount];
        Ant.citycount = citycount;
        Ant.AM = AM;
        Ant.tao = tao;
        for(int i=0;i<antscount;++i) ants[i] = new Ant();
    }

    /**
     * 初始化信息素矩阵
     */
    void init_tao() {
        tao = new double[citycount][citycount];

        // greedy algo len
        int greedylen = 0;
        int curcity = 0;
        int[] fpath = new int[citycount];
        fpath[0] = 1;
        int min;
        int minindex = 0;
        for(int k=0;k<citycount-1;++k) {
            min = Integer.MAX_VALUE;
            // find next city index whose len is min
            for(int j=0;j<citycount;++j) {
                if(curcity == j) continue;
                if(fpath[j] == 1) continue;

                if(min > AM[curcity][j]) {
                    min = AM[curcity][j];
                    minindex = j;
                }
            }

            // go next city
            fpath[minindex] = 1;
            greedylen += AM[curcity][minindex];
            curcity = minindex;
        }
        greedylen += AM[curcity][0];
//        System.out.println("greedylen: "+greedylen);

        // tao(i, j) = m / greedylen
//        System.out.println("init xinxisu:"+antscount*1.0 / greedylen);
        for(int i=0;i<citycount;++i) {
            for(int j=0;j<citycount;++j) {
                tao[i][j] = antscount*1.0 / greedylen;
                tao[j][i] = tao[i][j];
            }
        }
    }

    /**
     * 更新 tao 矩阵中的信息素，历史最优长度，历史最优路径
     */
    void update_tao() {
        // tao = (1-rho) * tao
        for (int i = 0; i < citycount; i++) {
            for (int j = i+1; j < citycount; j++) {
                tao[i][j] *= (1-rho);
                tao[j][i] *= (1-rho);
            }
        }

        // tao = tao + 1/ant_i.len & update bestlen and bestpath
        for (int i = 0; i < antscount; i++) {
            ants[i].callen();
            // 更新最优长度、最优路径
            if(bestlen > ants[i].len) {
                bestlen = ants[i].len;
                System.arraycopy(ants[i].path, 0, bestpath, 0, citycount);
            }
            // 更新 tao 元素
            for (int j = 0; j < citycount; j++) {
                int x = ants[i].path[j];
                int y = ants[i].path[(j+1)%citycount];
                tao[x][y] += q / ants[i].len;
                tao[y][x] = tao[x][y];
            }
        }

        // update ants' tao
        Ant.tao = tao;
    }

    public void run(int step) {
        for (int s = 0; s < step; s++) {
            for (int ant = 0; ant < antscount; ant++) {
                for (int c = 0; c < citycount-1; c++) {
                    ants[ant].goNext(alpha, beta);
                }
            }
            update_tao();
            for (int ant = 0; ant < antscount; ant++) {
//                System.out.println("ant"+(ant+1)+"'s len:"+ants[ant].len);
//                for (int i = 0; i < citycount; i++) {
//                    System.out.printf("%d,", ants[ant].path[i]);
//                }
//                System.out.println();
                ants[ant].reset();
            }
            System.out.println("step:"+(s+1)+", bestlen:"+bestlen);
//            System.out.println(bestlen);
//            System.out.print("best path: ");
//            for (int i = 0; i < citycount; i++) {
//                System.out.printf("%d,", bestpath[i]);
//            }
//            System.out.println();
        }
    }

    public void report(){
        System.out.println("best length: " + bestlen);
        System.out.print("best path: ");
        for (int i = 0; i < citycount; i++) {
            System.out.printf("%d,", bestpath[i]);
        }
        System.out.println();
    }
}
