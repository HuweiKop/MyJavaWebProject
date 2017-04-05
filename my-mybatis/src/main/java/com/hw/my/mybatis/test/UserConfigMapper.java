package com.hw.my.mybatis.test;


import com.hw.my.mybatis.mapper.ConfigMapper;
import com.hw.my.mybatis.mapper.MapperObj;

/**
 * Created by huwei on 2017/3/31.
 */
public class UserConfigMapper extends ConfigMapper {

    public UserConfigMapper(){
        this.namespace = "com.hw.my.mybatis.test.UserDao";

        String sql = "select * from t_user where id=#{id}";
        MapperObj obj = new MapperObj(sql,"int","com.hw.my.mybatis.test.User", "selectOne");
        this.map.put("selectById",obj);
    }
}
