package com.myproject.redlock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 11:24 2018/1/10
 * @Modified By
 */
public class RedisLocker implements DistributedLocker {

    private final static String LOCKER_PREFIX = "lock:";

    @Override
    public <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws UnableToAquireLockException, Exception {
        return lock(resourceName, worker, 100);
    }

    @Override
    public <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime) throws UnableToAquireLockException, Exception {
        RedissonClient redisson= RedissonConnector.getClient();
        RLock lock = redisson.getLock(LOCKER_PREFIX + resourceName);
        // Wait for 100 seconds seconds and automatically unlock it after lockTime seconds
        boolean success = lock.tryLock(100, lockTime, TimeUnit.SECONDS);
        if (success) {
            try {
                return worker.invokeAfterLockAquire();
            } finally {
                lock.unlock();
            }
        }
        throw new UnableToAquireLockException();
    }
}
