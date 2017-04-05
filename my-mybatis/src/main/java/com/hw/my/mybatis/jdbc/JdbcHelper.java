package com.hw.my.mybatis.jdbc;

import com.hw.db.connection.pool.PoolManager;

import java.sql.*;
import java.util.List;

/**
 * Created by huwei on 2017/3/31.
 */
public class JdbcHelper {

//    private static String url = "jdbc:mysql://localhost:3306/test"; // 数据库地址
//    private static String username = "root"; // 数据库用户名
//    private static String password = "password01!"; // 数据库密码
//
//    static {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    public static Connection getConnection() throws SQLException {
        return PoolManager.getPool().getConnection();
    }

    // 释放连接
    public static void free(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null) {
                rs.close(); // 关闭结果集
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close(); // 关闭Statement
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.close(); // 关闭连接
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ResultSet query(Connection conn, String sql, List<Object> params){
        ResultSet rs = null;
        System.out.println(sql);
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql) ;
            for(int i=0;i<params.size();i++){
                if(params.get(i) instanceof Integer){
                    pstmt.setInt(i+1,(int)params.get(i));
                }
                if(params.get(i) instanceof String){
                    pstmt.setString(i+1,params.get(i).toString());
                }
            }
            rs= pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("数据库连接失败！");
            e.printStackTrace();
        }
        return rs;
    }
}
