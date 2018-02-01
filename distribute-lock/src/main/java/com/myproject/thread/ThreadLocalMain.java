package com.myproject.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 19:44 2018/1/25
 * @Modified By
 */
public class ThreadLocalMain {

    static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args){
        ExecutorService service = Executors.newFixedThreadPool(3);

        for(int i=1;i<10;i++){
            service.submit(new ThreadLocalTest("thread "+i));
        }
    }

    static class ThreadLocalTest implements Runnable {

        private String msg;

        public ThreadLocalTest(String msg){
            this.msg = msg;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+"thread:"+this.msg);
            System.out.println(Thread.currentThread().getName()+threadLocal.get());
            threadLocal.set(this.msg);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
