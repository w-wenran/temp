package org.base.algorithm.sort;

/**
 * Created by wangwr on 2016/6/8.
 */
public class QuickSort extends Sort {

    public static void main(String[] args){
        new QuickSort().run();
    }

    @Override
    int[] sort(int[] args) {
        partition(args,0,args.length-1);
        return args;
    }

    public void partition(int[] args,int low,int high){

        int i,j,s;
        while (high>low){
            i=low;
            j=high;
            s=args[low];
            while (i<j){
                while (args[j]>s){
                    j--;
                }
                args[i]=args[j];
                P.show(Arrays.toStr(args));
                while (s>=args[i] && i<j){
                    i++;
                }
                args[j]=args[i];
                P.show(Arrays.toStr(args));
            }
            args[i]=s;
            P.show("@@@@@@@@@@@@"+s);
            P.show(Arrays.toStr(args));
            partition(args,low,i-1);
            low = i+1;
            P.show("-------------"+low);
            //partition(args,i+1,high);
        }

    }
}
