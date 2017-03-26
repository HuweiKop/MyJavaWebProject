package com.hw.mybatis.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wei Hu (J) on 2017/1/18.
 */
public class SqlModel<T> {
    private T model;
    private List<String> selectField;
    private List<String> orderby;
    private SqlLimit limit;
    private List<SqlWhere> sqlWheres;

    public SqlModel() {
        this.selectField = new ArrayList<>();
        this.orderby = new ArrayList<>();
        this.sqlWheres = new ArrayList<>();
    }

    public SqlModel<T> where(String fieldName, SqlOperator operator, Object value){
        SqlWhere where = new SqlWhere();
        where.sqlField = fieldName;
        where.operator = operator;
        where.sqlValue = value;
        this.sqlWheres.add(where);
        return this;
    }

    public SqlModel<T> orderBy(String orderby){
        this.orderby.add(orderby);
        return this;
    }

    public SqlModel<T> limit(int start, int count){
        this.limit = new SqlLimit();
        this.limit.startNum = start;
        this.limit.countNum = count;
        return this;
    }

    public SqlModel<T> page(int pageIndex, int pageSize){
        int start = (pageIndex-1)*pageSize;
        return limit(start,pageSize);
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public List<String> getSelectField() {
        return selectField;
    }

    public void setSelectField(List<String> selectField) {
        this.selectField = selectField;
    }

    public List<SqlWhere> getSqlWheres() {
        return sqlWheres;
    }

    public List<String> getOrderby() {
        return orderby;
    }

    public SqlLimit getLimit() {
        return limit;
    }

    class SqlWhere{
        private String sqlField;
        private SqlOperator operator;
        private Object sqlValue;

        public String getSqlField() {
            return sqlField;
        }

        public SqlOperator getOperator() {
            return operator;
        }

        public Object getSqlValue() {
            return sqlValue;
        }
    }

    class SqlLimit{
        private int startNum;
        private int countNum;

        public int getStartNum() {
            return startNum;
        }

        public int getCountNum() {
            return countNum;
        }
    }

    public enum SqlOperator{
        contain("like",""),
        equals("eq","="),
        lessthanEquals("lt_eq","<="),
        lessthan("lt","<"),
        greaterthan("gt",">"),
        greaterthanEquals("gt_eq",">=");

        private String name;
        private String symbol;

        SqlOperator(String name, String symbol){
            this.name = name;
            this.symbol = symbol;
        }

        public String getName() {
            return name;
        }

        public String getSymbol() {
            return symbol;
        }
    }
}
