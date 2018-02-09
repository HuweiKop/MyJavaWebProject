package com.myproject.redlock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 15:19 2018/1/11
 * @Modified By
 */
public class MainThread {

    private  static int x =1;

    public static  void main(String[] args){
//        test3();
        System.out.println("xxxx");
        String s2 = "didi java";
        String s1 = new String("didi java").intern();

        System.out.println("s1 == s2: " + (s1 == s2)); // 同一个
        System.out.println("s1.equals(s2): " + s1.equals(s2)); // 相等

    }

    private static void test1(){
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(100);
                for(int i=0;i<100;i++){
                    x+=i;
                }
                return x;
            }
        };
        FutureTask<Integer> task = new FutureTask(callable);
        Thread t = new Thread(task);
        t.start();

        try {
            Integer a = task.get();
            System.out.println(a);
            System.out.println(x);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void test2(){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<200;i++)
                    System.out.println(Thread.currentThread().getName()+" "+i);
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t1.join();
                } catch (InterruptedException e) {
                }
                for(int i=0;i<200;i++)
                    System.out.println(Thread.currentThread().getName()+" "+i);
            }
        });
        t1.start();
        t2.start();
    }

    private static void test3(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName() + " " + i);
                }
            }
        });
        for(int i=0;i<100;i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
            if(i==30){
                thread.setDaemon(true);
//                thread.setPriority(Thread.MAX_PRIORITY);
                thread.start();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(i==50){
                Thread.yield();
            }
        }
    }
}
