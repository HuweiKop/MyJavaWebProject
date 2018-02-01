package com.myproject.redlock;

import java.util.LinkedHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        System.out.println( "Hello World!" );
        RedisLocker distributedLocker = new RedisLocker();
        testRedlock(distributedLocker);
    }

    public static String testRedlock(RedisLocker distributedLocker) throws Exception{

        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(5);
        for (int i = 0; i < 5; ++i) { // create and start threads
            new Thread(new Worker(startSignal, doneSignal, distributedLocker)).start();
        }
        startSignal.countDown(); // let all threads proceed
        doneSignal.await();
        System.out.println("All processors done. Shutdown connection");
        return "redlock";
    }
}
