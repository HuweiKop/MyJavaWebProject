package com.hw.my.mybatis.mapper;

import com.hw.my.mybatis.sqlSession.MySqlSession;

import java.lang.reflect.Proxy;

/**
 * Created by huwei on 2017/3/31.
 */
public class MyMapperProxyFactory {

    public <T> T newInstance(Class<T> type, MySqlSession sqlSession){
        System.out.println(type.getName());
        MyMapperProxy proxy = new MyMapperProxy(sqlSession, MapperManager.getMapper().getConfigMapper(type.getName()));
        return (T) Proxy.newProxyInstance(type.getClassLoader(),new Class[]{type},proxy);
    }
}
