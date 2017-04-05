package com.hw.my.mybatis.mapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huwei on 2017/3/31.
 */
public class ConfigMapper {
    protected String namespace;
    protected Map<String, MapperObj> map = new HashMap<>();

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Map<String, MapperObj> getMap() {
        return map;
    }

    public void setMap(Map<String, MapperObj> map) {
        this.map = map;
    }
}
