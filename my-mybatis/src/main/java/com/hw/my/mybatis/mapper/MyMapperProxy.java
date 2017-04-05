package com.hw.my.mybatis.mapper;

import com.hw.my.mybatis.sqlSession.MySqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by huwei on 2017/3/31.
 */
public class MyMapperProxy implements InvocationHandler {

    private MySqlSession sqlSession;
    private ConfigMapper configMapper;

    public MyMapperProxy(MySqlSession sqlSession, ConfigMapper configMapper) {
        this.sqlSession = sqlSession;
        this.configMapper = configMapper;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke");
        System.out.println(method.getName());
        Object arg = null;
        if(args.length==1)
            arg = args[0];

        MapperObj obj = this.configMapper.getMap().get(method.getName());
        if(obj.getExecuteMethod().equals("selectOne")){
            return this.sqlSession.selectOne(method.getReturnType(), obj.getSql(), arg);
        }

        return null;
    }
}
