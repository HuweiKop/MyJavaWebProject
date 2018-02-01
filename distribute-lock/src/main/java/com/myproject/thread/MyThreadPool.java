package com.myproject.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 15:14 2018/1/16
 * @Modified By
 */
public class MyThreadPool {

    private int workerNum = 3;

    private MyWorker[] workers;

    private BlockingQueue<Runnable> queue;

    private int queueNum = 20;

    private ReentrantLock mainLock = new ReentrantLock();

    public MyThreadPool(int workerNum, int queueNum) {
        this.workerNum = workerNum;
        this.queueNum = queueNum;
        init();
    }

    public MyThreadPool() {
        init();
    }

    public void execute(Runnable task) {
        if (task == null) {
            throw new RuntimeException("task 为空");
        }
        synchronized (queue) {
            if (queue.size() >= this.queueNum) {
                throw new RuntimeException("queue 已满");
            } else {
                this.queue.add(task);
                this.queue.notify();
            }
        }
    }

    public void execute(Runnable[] tasks) {
        if (tasks == null) {
            throw new RuntimeException("task 为空");
        }
        synchronized (queue) {
            for (int i = 0, length = tasks.length; i < length; i++) {
                if (tasks[i] != null) {
                    this.queue.add(tasks[i]);
                }
            }
            this.queue.notify();
        }
    }

    public void destroy() {
        while (!this.queue.isEmpty()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0, length = this.workers.length; i < length; i++) {
            workers[i].stopWorker();
            workers[i] = null;
        }

        this.queue.clear();
    }

    private void init() {
        this.workers = new MyWorker[workerNum];
        this.queue = new ArrayBlockingQueue<Runnable>(this.queueNum);

        for (int i = 0; i < this.workerNum; i++) {
            workers[i] = new MyWorker();
            workers[i].start();
        }
    }

    private class MyWorker extends Thread {

        private boolean isRunning = true;

        @Override
        public void run() {
            Runnable r = null;
            while (isRunning) {
                synchronized (queue) {
                    try {
                        if (!queue.isEmpty()) {
                            r = queue.take();
                        } else {
                            queue.wait(20);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (r != null) {
                    r.run();
                }
                r = null;
            }
        }

        public void stopWorker() {
            this.isRunning = false;
        }
    }

    public static void main(String[] args) {
        MyThreadPool pool = new MyThreadPool();
        pool.execute(new Runnable[]{new Task(), new Task(), new Task(), new Task(), new Task()});
        pool.destroy();
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " 开始执行");
        }
    }
}
