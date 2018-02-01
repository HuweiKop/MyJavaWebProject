package com.myproject.thread;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 18:39 2018/1/23
 * @Modified By
 */
public class TestMain {

    public static void main(String[] args){
        int row = 10;

        for(int i=0;i<row;i++){
            int x = i;
            for(int j=0;j<row-x;j++){
                System.out.print(" ");
            }
            for(int k=0;k<x*2-1;k++){
                System.out.print("*");
            }
            System.out.println();
        }

        for(int i=0;i<row;i++){
            int x = row-i;
            for(int j=0;j<row-x;j++){
                System.out.print(" ");
            }
            for(int k=0;k<x*2-1;k++){
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
