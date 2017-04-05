package com.hw.my.mybatis.mapper;

/**
 * Created by huwei on 2017/3/31.
 */
public class MapperManager {
    public static Mapper getMapper(){
        return MapperInit.mapper;
    }

    static class MapperInit{
        public static Mapper mapper = new Mapper();
    }
}
