package org.base.algorithm.sort;

/**
 * Created by wangwr on 2016/6/2.
 */
public class BubbleSort extends Sort{

    public static void main(String[] args){
        new BubbleSort().run();
    }

    @Override
    int[] sort(int[] args) {
        boolean swaped;
        int N = args.length;
        do{
            swaped=false;
            for(int i=1;i<N;i++){
                if(args[i-1]>args[i]){//小的往上冒，大的往下沉
                    P.show("swap item %s <--> %s",args[i-1],args[i]);
                    int temp = args[i];
                    args[i]=args[i-1];
                    args[i-1]=temp;
                    swaped=true;//有交换就继续走，直到没有交换则停止
                }
            }
            N--;
        }while (swaped);
        return args;
    }
}
