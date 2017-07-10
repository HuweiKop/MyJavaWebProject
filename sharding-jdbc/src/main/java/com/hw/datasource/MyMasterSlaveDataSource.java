package com.hw.datasource;

import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by huwei on 2017/7/6.
 */
public class MyMasterSlaveDataSource extends AbstractDataSource {

    private final DataSource masterDataSource;
    private final List<DataSource> slaveDataSources;

    public MyMasterSlaveDataSource(DataSource masterDataSource, List<DataSource> slaveDataSources){
        this.masterDataSource = masterDataSource;
        this.slaveDataSources = slaveDataSources;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.slaveDataSources.get(1).getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }
}
