package com.hw.my.mybatis.mapper;

/**
 * Created by huwei on 2017/3/31.
 */
public class MapperObj {
    private String sql;
    private String parameterType;
    private String resultType;
    private String executeMethod;

    public MapperObj(String sql, String parameterType, String resultType, String executeMethod) {
        this.sql = sql;
        this.parameterType = parameterType;
        this.resultType = resultType;
        this.executeMethod  = executeMethod;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getExecuteMethod() {
        return executeMethod;
    }

    public void setExecuteMethod(String executeMethod) {
        this.executeMethod = executeMethod;
    }
}
