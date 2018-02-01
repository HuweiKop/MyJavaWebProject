package com.myproject.redlock;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 11:09 2018/1/10
 * @Modified By
 */
public interface DistributedLocker {

    /**
     * 获取锁
     * @param resourceName  锁的名称
     * @param worker 获取锁后的处理类
     * @param <T>
     * @return 处理完具体的业务逻辑要返回的数据
     * @throws UnableToAquireLockException
     * @throws Exception
     */
    <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws UnableToAquireLockException, Exception;

    <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime) throws UnableToAquireLockException, Exception;
}
