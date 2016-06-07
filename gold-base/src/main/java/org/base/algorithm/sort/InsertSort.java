package org.base.algorithm.sort;

/**
 * Created by wangwr on 2016/6/2.
 */
public class InsertSort extends Sort{

    public static void main(String[] args){
        new InsertSort().run();
    }

    public int[] sort(int[] args){
        for(int i=0;i<args.length;i++){
            int key = args[i];
            int j=i-1;
            for( ;j>=0&&args[j]>key;j--){
                //前面的数挨个和key比较,比key大都忘后移动，直到遇到比key小的就终止
                args[j+1] = args[j];//元素向后移动
            }
            //找到合适的位置插入元素
            P.show("insert item %s ",key);
            args[j+1]=key;
        }
        return args;
    }
}
