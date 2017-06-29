package com.hw.springboot;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by huwei on 2017/6/29.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
