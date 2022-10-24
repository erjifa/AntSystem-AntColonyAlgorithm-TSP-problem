package myas;

public class Test {
    public static void main(String[] args) {
        Ant.citycount = 10;
        Ant ant = new Ant();
        System.out.print("path: ");
        for (int i = 0; i < 10; i++) {
            System.out.print(ant.path[i]+",");
        }
        System.out.println("curcity: "+ant.curcity);
    }
}
