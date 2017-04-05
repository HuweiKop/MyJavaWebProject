package com.hw.my.mybatis.test;


import com.hw.my.mybatis.sqlSession.MySqlSession;

/**
 * Created by huwei on 2017/3/31.
 */
public class Bootstrap {
    public static void main(String[] args){
        MySqlSession sqlSession = new MySqlSession();
        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User user = mapper.selectById(1);
        System.out.println(user.getId());
        System.out.println(user.getName());
    }
}
