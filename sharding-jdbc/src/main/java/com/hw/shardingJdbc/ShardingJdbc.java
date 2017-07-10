package com.hw.shardingJdbc;

import com.dangdang.ddframe.rdb.sharding.api.MasterSlaveDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.ShardingDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.api.rule.DataSourceRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.ShardingRule;
import com.dangdang.ddframe.rdb.sharding.api.rule.TableRule;
import com.dangdang.ddframe.rdb.sharding.constant.SQLType;
import com.dangdang.ddframe.rdb.sharding.executor.ExecutorEngine;
import com.dangdang.ddframe.rdb.sharding.jdbc.core.ShardingContext;
import com.dangdang.ddframe.rdb.sharding.jdbc.core.connection.ShardingConnection;
import com.dangdang.ddframe.rdb.sharding.jdbc.core.datasource.MasterSlaveDataSource;
import com.dangdang.ddframe.rdb.sharding.jdbc.core.datasource.ShardingDataSource;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huwei on 2017/7/4.
 */
public class ShardingJdbc {

    public static void main(String[] args) {

        DataSource master = createDataSource("test");
        DataSource slaver1 = createDataSource("test1");
        DataSource slaver2 = createDataSource("test2");

        Map<String, DataSource> result = new HashMap<>(3);
        result.put("test0", master);
        result.put("test1", slaver1);
        result.put("test2", slaver2);

//        DataSource dataSource = MasterSlaveDataSourceFactory.createDataSource("test",master,slaver1,slaver2);
//
//        Map<String, DataSource> dataSourceMap = new HashMap<>(1);
//        dataSourceMap.put("default", dataSource);
        DataSourceRule dataSourceRule = new DataSourceRule(result);
        ShardingRule rule = new ShardingRule.ShardingRuleBuilder().dataSourceRule(dataSourceRule)
                .tableRules(Collections.singleton(new  TableRule.TableRuleBuilder("user").dataSourceRule(dataSourceRule).build())).build();
        ShardingContext shardingContext = new ShardingContext(rule, null, new ExecutorEngine(10));
        ShardingConnection connection = new ShardingConnection(shardingContext);

//        MasterSlaveDataSource dataSource = (MasterSlaveDataSource)MasterSlaveDataSourceFactory.createDataSource("test",master,slaver1,slaver2);

        DataSource dataSource = ShardingDataSourceFactory.createDataSource(rule);
        String sql = "SELECT * from user where id=?";

        try {
            for(int i=0;i<1;i++) {
                Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, 1);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        System.out.println(rs.getInt(1));
                        System.out.println(rs.getString(2));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>(3);
        result.put("test0", createDataSource("test"));
        result.put("test1", createDataSource("test1"));
        result.put("test2", createDataSource("test2"));
        return result;
    }

    /**
     * 创建数据源
     *
     * @param dataSourceName
     * @return
     */
    private static DataSource createDataSource(String dataSourceName) {
        BasicDataSource result = new BasicDataSource();
        result.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        result.setUrl(String.format("jdbc:mysql://localhost:3306/%s", dataSourceName));
        result.setUsername("root");
        result.setPassword("password01!");
        return result;
    }
}
