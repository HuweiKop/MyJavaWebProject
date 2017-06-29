package com.hw.mutil.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by huwei on 2017/6/28.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return  DataSourceContextHolder.getDataSourceType();
    }
}
