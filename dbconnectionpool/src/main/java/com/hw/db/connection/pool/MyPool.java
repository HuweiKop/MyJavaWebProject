package com.hw.db.connection.pool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by huwei on 2017/3/24.
 */
public class MyPool {

    private static String driverName = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=UTF-8";
    private static String username = "root";
    private static String password = "password01!";
    private static int initCount = 10;
    private static int addCount = 5;
    private static int maxCount = 50;

    private Vector<MyConnection> connectionPool = new Vector<>();

    public MyPool() {
        init();
    }

    public void init() {
        try {
            Driver driver = (Driver) Class.forName(this.driverName).newInstance();
            DriverManager.registerDriver(driver);

            createConnection(this.initCount);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        MyConnection connection = getRealConnection();
        while (connection==null){
            createConnection(this.addCount);
            connection = getRealConnection();
        }
        return connection.getConnection();
    }

    public synchronized MyConnection getRealConnection() {
        for (MyConnection connection : this.connectionPool) {
            if (!connection.isBusy()) {
                connection.setBusy(true);
                return connection;
            }
        }

        return null;
    }

    public synchronized void createConnection(int count) {
        if (this.maxCount > 0 && count + this.connectionPool.size() > this.maxCount) {
            System.out.println("超过连接池最大限制。。。。");
            throw new RuntimeException("超过连接池最大限制。。。。");
        }

        for(int i=0;i<count;i++){
            try {
                Connection connection = DriverManager.getConnection(url,username,password);
                MyConnection myConnection = new MyConnection(connection,false);
                this.connectionPool.add(myConnection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
