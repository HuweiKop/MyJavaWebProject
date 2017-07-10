package com.hw.springboot.datasource;

/**
 * Created by huwei on 2017/7/4.
 */
public class DataSourceTypeThreadLocal {

    private static final ThreadLocal<Integer> dataSourceType = new ThreadLocal<Integer>();

    public static Integer getCurrentDataSourceType(){
        return dataSourceType.get();
    }

    public static void setCurrentDataSourceType(int type){
        dataSourceType.set(type);
    }
}
