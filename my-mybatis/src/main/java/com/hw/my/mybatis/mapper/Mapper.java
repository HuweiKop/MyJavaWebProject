package com.hw.my.mybatis.mapper;

import com.hw.my.mybatis.test.UserConfigMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huwei on 2017/3/31.
 */
public class Mapper {
    private Map<String, ConfigMapper> map = new HashMap<>();

    public Mapper(){
        map.put("com.hw.my.mybatis.test.UserDao",new UserConfigMapper());
    }

    public ConfigMapper getConfigMapper(String key){
        return map.get(key);
    }
}
