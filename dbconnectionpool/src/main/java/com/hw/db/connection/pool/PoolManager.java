package com.hw.db.connection.pool;

/**
 * Created by huwei on 2017/3/24.
 */
public class PoolManager {

    public static MyPool getPool(){
        return PoolCreator.pool;
    }

    private static class PoolCreator {
        public static MyPool pool = new MyPool();
    }
}
