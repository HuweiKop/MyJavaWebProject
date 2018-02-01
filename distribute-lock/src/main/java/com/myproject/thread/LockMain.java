package com.myproject.thread;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 17:46 2018/1/12
 * @Modified By
 */
public class LockMain {

    public static void main(String[] args){
        LockTest test = new LockTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    test.read();
                }
            }
        }).start();
        new Thread(()->{
            for(int i=0;i<10;i++){
                test.write();
            }
        }).start();
    }

    static class LockTest{

        ReentrantReadWriteLock reentrant = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = reentrant.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = reentrant.writeLock();

        public void read(){
            readLock.lock();
            System.out.println("lock read");
            readLock.unlock();
            Thread.yield();
        }

        public void write(){
            writeLock.lock();
            System.out.println("write lock");
            writeLock.unlock();
            Thread.yield();
        }
    }
}
