package com.hw.mybatis.sql;


import com.hw.mybatis.annotation.FieldName;
import com.hw.mybatis.annotation.IdName;
import com.hw.mybatis.annotation.TableName;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wei Hu (J) on 2017/1/18.
 */
public class SqlHelper {

    public static <T> Map<String, Object> getQuerySql(SqlModel sqlModel, Class<T> tClass) {

        Map<String, Object> result = new HashMap<>();
        String selectField = "";
        if (sqlModel.getSelectField().size() == 0) {
            selectField = " * ";
        } else {
            List<String> fields = sqlModel.getSelectField();
            for (String field : fields
                    ) {
                selectField += field + ",";
            }
            selectField = selectField.substring(0, selectField.length() - 1);
        }
        result.put("selectSql", selectField);

        TableName table = tClass.getAnnotation(TableName.class);
        IdName id = tClass.getAnnotation(IdName.class);
        result.put("tablename", table.value());
        result.put("idname", id.value());

        if (sqlModel.getLimit() != null) {
            result.put("limitSql", String.format(" limit %d,%d",
                    sqlModel.getLimit().getStartNum(), sqlModel.getLimit().getCountNum()));
            System.out.println(sqlModel.getLimit().getStartNum() + "  " + sqlModel.getLimit().getCountNum());
            System.out.println(result.get("limitSql"));
        }

        if (sqlModel.getOrderby().size() > 0) {
            String orderby = " order by ";
            List<String> orderbys = sqlModel.getOrderby();
            for (String order : orderbys
                    ) {
                orderby += order + ",";
            }
            orderby = orderby.substring(0, orderby.length() - 1);
            result.put("orderbySql", orderby);
            System.out.println(orderby);
        }

        getWhereSql(sqlModel.getSqlWheres(), result);

        return result;
    }

    public static <T> Map<String, Object> getUpdateSql(SqlModel sqlModel, Class<T> tClass) {

        Map<String, Object> result = new HashMap<>();

        TableName table = tClass.getAnnotation(TableName.class);
        IdName id = tClass.getAnnotation(IdName.class);
        result.put("tablename", table.value());
        result.put("idname", id.value());

        String setFieldSql = " ";
        Field[] fields = tClass.getDeclaredFields();
        Object model = sqlModel.getModel();
        for (Field field : fields) {
            String name = field.getName();
            FieldName fn = field.getAnnotation(FieldName.class);
            if (fn != null)
                name = fn.value();
            if(id.value().equals(name))
                continue;
            setFieldSql+=name+"=#{"+name+"},";
            try {
                field.setAccessible(true);
                result.put(name, field.get(model));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        setFieldSql = setFieldSql.substring(0,setFieldSql.length()-1);
        result.put("setFieldSql", setFieldSql);

        getWhereSql(sqlModel.getSqlWheres(), result);

        return result;
    }

    public static void getWhereSql(List<SqlModel.SqlWhere> wheres, Map<String, Object> result) {

        if (wheres.size() > 0) {
            String whereSql = "where 1=1 ";
            for (SqlModel.SqlWhere where : wheres) {
                if (where.getOperator() == SqlModel.SqlOperator.contain) {
                    whereSql += " and " + where.getSqlField() + " like '%" + where.getSqlValue() + "%'";
                } else {
                    String field = where.getSqlField() + "_" + where.getOperator().getName();
                    whereSql += " and " + where.getSqlField() + where.getOperator().getSymbol() + "#{" + field + "} ";
                    result.put(field, where.getSqlValue());
                }
            }
            result.put("whereSql", whereSql);
            System.out.println(whereSql);
        }
    }
}
