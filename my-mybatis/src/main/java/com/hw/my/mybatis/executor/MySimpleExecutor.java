package com.hw.my.mybatis.executor;


import com.hw.my.mybatis.jdbc.JdbcHelper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by huwei on 2017/3/31.
 */
public class MySimpleExecutor implements MyExecutor {

    private Connection conn;

    public MySimpleExecutor(Connection conn){
        this.conn = conn;
    }

    @Override
    public <T> T query(Class<T> resultType, String sql, Object parameter) {
        System.out.println("executor query");
        System.out.println(parameter);

        String regex = "#\\{\\w+}";
        sql.replaceAll(regex, "?");
        System.out.println("sql after:"+sql);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sql);

        List<Object> params = new ArrayList<>();

        if(parameter instanceof Integer){
            System.out.println("integer");
            matcher.find();
            String para = matcher.group().replace("#{","").replace("}","");
            sql = sql.replace(matcher.group(),"?");
            params.add(parameter);
        }
        else{

        }

        ResultSet rs = JdbcHelper.query(this.conn,sql,params);
        Field[] fields = resultType.getDeclaredFields();
        System.out.println(resultType.getName());
        Object result = null;
        try {
            result = resultType.newInstance();
            while (rs.next()){
                System.out.println("set reuslt....");
                for(Field field:fields){
                    System.out.println(field.getType());
                    field.setAccessible(true);
                    if(field.getType().equals(int.class)||field.getType().equals(Integer.class)){
                        field.set(result,rs.getInt(field.getName()));
                    }
                    else if(field.getType().equals(String.class)){
                        field.set(result,rs.getString(field.getName()));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        while (matcher.find()){
            System.out.println(sql.replace(matcher.group(),"?"));
        }

//        Field[] fields = parameter.getClass().getFields();
//        for(Field field:fields){
//            System.out.println(field.getName());
//        }
        return (T) result;
    }
}
