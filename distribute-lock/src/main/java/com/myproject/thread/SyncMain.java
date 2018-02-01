package com.myproject.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 13:47 2018/1/23
 * @Modified By
 */
public class SyncMain {

    public static void main(String[] args){
        SyncTest test = new SyncTest();
        Thread t1 = new Thread(test);
        Thread t2 = new Thread(test);
        t1.start();
        t2.start();
//        t1.interrupt();
//        t2.interrupt();
    }

    static class SyncTest implements Runnable {
        @Override
        public void run() {
            synchronized (this){
                System.out.println("run.....");
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
