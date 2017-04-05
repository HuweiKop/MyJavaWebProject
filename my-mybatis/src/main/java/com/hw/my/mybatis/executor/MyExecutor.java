package com.hw.my.mybatis.executor;

/**
 * Created by huwei on 2017/3/31.
 */
public interface MyExecutor {
    <T> T query(Class<T> resultType, String sql, Object parameter);
}
