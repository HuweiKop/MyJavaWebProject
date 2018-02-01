package com.myproject;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 14:59 2018/1/10
 * @Modified By
 */
public class Lock {

    private boolean isLocked = false;

    public synchronized void lock() throws InterruptedException {
        while (isLocked) {
            wait();
        }
        isLocked = true;
    }

    public synchronized void unlock() {
        isLocked = false;
        notify();
    }
}
