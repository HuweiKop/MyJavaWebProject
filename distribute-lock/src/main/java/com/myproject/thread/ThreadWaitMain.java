package com.myproject.thread;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 15:43 2018/1/12
 * @Modified By
 */
public class ThreadWaitMain {

    public static void main(String[] args){
        Account account = new Account();
        account.setId(1);
        account.setAccount(0L);
        new Thread(new JianAccount(account,1)).start();
        new Thread(new AddAccount(account,1)).start();
    }

    static class AddAccount implements Runnable{

        private Account account;
        private long amount;

        public AddAccount(Account account, long amount){
            this.account = account;
            this.amount = amount;
        }

        @Override
        public void run() {
            for(int i=0;i<10;i++){
                this.account.add(amount);
            }
        }
    }

    static class JianAccount implements Runnable{

        private Account account;
        private long amount;

        public JianAccount(Account account, long amount){
            this.account = account;
            this.amount = amount;
        }

        @Override
        public void run() {
            for(int i=0;i<10;i++){
                this.account.jian(amount);
            }
        }
    }

    static class Account{
        private Integer id;
        private Long account;

        public synchronized void add(long money){
            if(this.account>=5){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("加："+money);
            this.account = this.account+money;

            System.out.println("当前："+this.account);
            notifyAll();
        }

        public void jian(long money) {
            synchronized (this) {
                    if (this.account <= 0) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("减：" + money);
                    this.account = this.account - money;

                    System.out.println("当前：" + this.account);
                    notifyAll();
            }
        }

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
