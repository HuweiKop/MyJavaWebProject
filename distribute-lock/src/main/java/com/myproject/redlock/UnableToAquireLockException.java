package com.myproject.redlock;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 11:11 2018/1/10
 * @Modified By
 */
public class UnableToAquireLockException extends RuntimeException {

    public UnableToAquireLockException() {
    }

    public UnableToAquireLockException(String message) {
        super(message);
    }

    public UnableToAquireLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
