package org.base.algorithm.sort;

/**
 * Created by wangwr on 2016/6/2.
 */
public class SelectionSort extends Sort{

    public static void main(String[] args){
        new SelectionSort().run();
    }

    public int[] sort(int[] args){
        for(int i=0;i<args.length;i++){
            int minIndex = i;//依次取出元素和后面的比较选出最小的交换
            int j=i+1;
            for(;j<args.length;j++){
                if(args[j]<args[minIndex]){
                    minIndex=j;
                }
            }

            //最小的下标与当前下标不一样，则交换
            if(minIndex!=i){
                P.show("swap %s <--> %s",args[i],args[minIndex]);
                int temp = args[minIndex];
                args[minIndex]=args[i];
                args[i]=temp;
            }
        }
        return args;
    }
}
