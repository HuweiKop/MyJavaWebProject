package com.hw.shardingJdbc;

import java.util.Random;

/**
 * Created by huwei on 2017/7/3.
 */
public class SlaverDataSourceMap {

    private static String[] dataSources = new String[]{"test1","test2"};
    private static Random random = new Random(2);

    public static String getDataSourceKey(int index){
        if(index>=dataSources.length)
            return null;
        return dataSources[index];
    }

    public static String getDataSourceKeyRandom(){
        return getDataSourceKey(random.nextInt(2));
    }
}
