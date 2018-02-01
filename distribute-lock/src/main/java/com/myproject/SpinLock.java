package com.myproject;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 15:05 2018/1/10
 * @Modified By
 */
public class SpinLock {
    //初始化为当前线程
    private AtomicReference<Thread> sign = new AtomicReference<>();

    public void lock() {
        Thread current = Thread.currentThread();
        System.out.println(current.getName()+" lock");
        //null 不等于当前线程，返回false !fasle=true进入自选
        while (!sign.compareAndSet(null, current)) {
        }
        System.out.println(current.getName()+" end");
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        System.out.println(current.getName()+" unlock");
        //对比current= 初始化信息，所以为true，并设置为null，此时
        // while (!sign.compareAndSet(null, current))，
        // 所以，null=null，lock中自旋结束，当一个锁完成，sign中有回到初始化状态。
        sign.compareAndSet(current, null);
    }
}
