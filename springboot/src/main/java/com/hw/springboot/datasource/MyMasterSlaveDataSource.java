package com.hw.springboot.datasource;

import com.hw.springboot.DataSourceTypeConst;
import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

/**
 * Created by huwei on 2017/7/6.
 */
public class MyMasterSlaveDataSource extends AbstractDataSource {

    private DataSource masterDataSource;
    private List<DataSource> slaveDataSources;

    private static Random random = new Random(2);
//    public MyMasterSlaveDataSource(DataSource masterDataSource, List<DataSource> slaveDataSources){
//        this.masterDataSource = masterDataSource;
//        this.slaveDataSources = slaveDataSources;
//    }

    @Override
    public Connection getConnection() throws SQLException {
        return getTargetDataSource().getConnection();
    }

    public DataSource getTargetDataSource(){
        Integer dataSourceType = DataSourceTypeThreadLocal.getCurrentDataSourceType();
        if(dataSourceType==null||dataSourceType== DataSourceTypeConst.MasterDataSource){
            System.out.println("使用 master data source");
            return masterDataSource;
        }
        int index = random.nextInt(slaveDataSources.size());
        System.out.println("使用 slaver data source "+index);
        return slaveDataSources.get(index);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    public DataSource getMasterDataSource() {
        return masterDataSource;
    }

    public void setMasterDataSource(DataSource masterDataSource) {
        this.masterDataSource = masterDataSource;
    }

    public List<DataSource> getSlaveDataSources() {
        return slaveDataSources;
    }

    public void setSlaveDataSources(List<DataSource> slaveDataSources) {
        this.slaveDataSources = slaveDataSources;
    }
}
