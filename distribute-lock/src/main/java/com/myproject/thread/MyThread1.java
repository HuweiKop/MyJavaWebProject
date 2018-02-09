package com.myproject.thread;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 15:47 2018/2/8
 * @Modified By
 */
public class MyThread1 extends Thread{

    private Object lock;
    private String name;

    public MyThread1(Object lock, String name){
        this.lock = lock;
        this.name = name;
    }

    @Override
    public void run() {
        int i = 0;
        while (i<3) {
            i++;
            synchronized (lock) {
                lock.notify();
                try {
                    System.out.println("---------------------");
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("before " + name);
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("after " + name);
            }
        }
    }

    public static void main(String[] args){
        Object lock = new Object();
        MyThread1 m1 = new MyThread1(lock,"1111");
        MyThread1 m2 = new MyThread1(lock,"2222");
        new Thread(m1).start();
        new Thread(m2).start();
    }
}
