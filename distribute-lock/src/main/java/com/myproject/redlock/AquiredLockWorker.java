package com.myproject.redlock;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 11:08 2018/1/10
 * @Modified By
 */
public interface AquiredLockWorker<T> {
    T invokeAfterLockAquire() throws Exception;
}
