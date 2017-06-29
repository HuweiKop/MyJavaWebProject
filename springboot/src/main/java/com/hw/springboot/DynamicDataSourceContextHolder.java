package com.hw.springboot;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by huwei on 2017/6/29.
 */
public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    public static Set<String> dataSourceIds = new HashSet<>();

    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static String getDataSourceType() {
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }

    /**
     * 判断指定DataSrouce当前是否存在
     *
     * @param dataSourceId
     * @return
     * @author SHANHY
     * @create  2016年1月24日
     */
    public static boolean containsDataSource(String dataSourceId){
        return dataSourceIds.contains(dataSourceId);
    }
}
