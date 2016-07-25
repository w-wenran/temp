package org.base.algorithm.sort;

/**
 * Created by wangwr on 2016/6/7.
 */
public class CombSort extends Sort {

    public static void main(String[] args){
        new CombSort().run();
    }

    @Override
    int[] sort(int[] args) {
        int N = args.length;
        boolean swapped;
        double shrink = 1.3d;
        int gap = N;

        do{
            gap = (int) Math.floor(gap/shrink);
            if(gap<1){
                gap = 1;
            }
            swapped = false;
            for(int i = 0;i+gap<N;i++){
                if(args[i]>args[i+gap]){
                    int temp = args[i];
                    args[i] = args[i+gap];
                    args[i+gap]=temp;
                    swapped=true;
                }
            }
        }while (gap!=1||swapped);

        return args;
    }
}
