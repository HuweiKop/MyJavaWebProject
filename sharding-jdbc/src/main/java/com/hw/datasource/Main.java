package com.hw.datasource;

import com.dangdang.ddframe.rdb.sharding.constant.SQLType;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwei on 2017/7/6.
 */
public class Main {
    public static void main(String[] args){

        DataSource master = createDataSource("test");
        DataSource slaver1 = createDataSource("test1");
        DataSource slaver2 = createDataSource("test2");
        List<DataSource> slavers = new ArrayList<>(2);
        slavers.add(slaver1);
        slavers.add(slaver2);

        DataSource dataSource = new MyMasterSlaveDataSource(master,slavers);

        String sql = "SELECT * from user where id=?";
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, 1);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println(rs.getInt(1));
                    System.out.println(rs.getString(2));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
