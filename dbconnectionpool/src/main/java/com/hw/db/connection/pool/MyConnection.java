package com.hw.db.connection.pool;

import java.sql.Connection;

/**
 * Created by huwei on 2017/3/24.
 */
public class MyConnection {

    private boolean busy;
    private Connection connection;

    public MyConnection(Connection connection, boolean busy){
        this.busy = busy;
        this.connection = connection;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
