package com.myproject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 15:32 2018/1/10
 * @Modified By
 */
public class MainLock {

    public static void main(String[] args){
        SpinLock lock =new SpinLock();
        InnerLock innerLock = new InnerLock(lock);
        Thread thread1 = new Thread(innerLock);

        Thread thread2 = new Thread(innerLock);
        thread1.start();
        thread2.start();
    }

    static class InnerLock implements Runnable{

        private SpinLock lock;

        public InnerLock(SpinLock lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            lock.lock();

            try {
                System.out.println(Thread.currentThread().getName() + " sleep");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            lock.unlock();
        }

        public void unlock(){
            this.lock.unlock();
        }
    }
}
