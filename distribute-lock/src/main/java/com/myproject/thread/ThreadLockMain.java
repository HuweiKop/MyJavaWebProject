package com.myproject.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 19:23 2018/1/11
 * @Modified By
 */
public class ThreadLockMain {

    public static void main(String[] args){

        Account account = new Account();
        account.setId(1111);
        account.setAccount(500L);

        OperateAccount o = new OperateAccount(account,300);

        Thread t1 = new Thread(o);
        Thread t2 = new Thread(o);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(account.getAccount());
    }

    static class OperateAccount implements Runnable{

        private Account account;
        private long money;

        private Lock lock = new ReentrantLock();

        public OperateAccount(Account account, long money){
            this.account = account;
            this.money = money;
        }

        @Override
        public void run() {
            lock.lock();
                long acc = account.getAccount() - money;
                System.out.println("取出金额:" + money);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                account.setAccount(acc);
                System.out.println("账户余额:" + account.getAccount());
            lock.unlock();
        }
    }

    static class Account{
        private Integer id;
        private Long account;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Long getAccount() {
            return account;
        }

        public void setAccount(Long account) {
            this.account = account;
        }
    }
}
