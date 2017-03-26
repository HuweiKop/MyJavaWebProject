package com.hw.mybatis.dao.impl;


import com.hw.mybatis.annotation.FieldName;
import com.hw.mybatis.annotation.IdName;
import com.hw.mybatis.annotation.TableName;
import com.hw.mybatis.dao.ICommonDao;
import com.hw.mybatis.sql.SqlHelper;
import com.hw.mybatis.sql.SqlModel;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wei Hu (J) on 2017/1/12.
 */
public abstract class CommonDaoImpl<T> extends SqlSessionDaoSupport implements ICommonDao<T> {

    private Class<T> tClass;
    private String idName;
    private String tableName;

    protected final static String commonNamespace = "com.yizhi.test.dao.common.ICommonDao.";

    public CommonDaoImpl(Class<T> tClass) throws IOException {
        this.tClass = tClass;
        TableName table = this.tClass.getAnnotation(TableName.class);
        tableName = table.value();
        IdName id = this.tClass.getAnnotation(IdName.class);
        idName = id.value();
    }

    @Override
    public T getById(int id) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("tablename", this.tableName);
        hashMap.put("idname", this.idName);
        hashMap.put("id", id);
        HashMap<String, Object> result = super.getSqlSession().selectOne(commonNamespace+"getById", hashMap);
        return getResult(result);
    }

    @Override
    public List<T> getByWhere(HashMap<String, Object> map) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("tablename", this.tableName);
        hashMap.put("idname", this.idName);
        hashMap.putAll(map);
//        HashMap<String,Object> result = this.sqlSession.selectOne("getByWhere",hashMap);
//        return getResult(result);
        List<HashMap<String, Object>> result = super.getSqlSession().selectList(commonNamespace+"getByWhere", hashMap);
        return getListResult(result);
    }

    @Override
    public boolean insert(T bean) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("tablename", this.tableName);
        putBeanToMap(bean, hashMap);

        return super.getSqlSession().insert(commonNamespace+"insert",hashMap)>0;
    }

    @Override
    public boolean updateById(T bean, Object id) {
        SqlModel<T> sqlModel = new SqlModel<T>();
        sqlModel.setModel(bean);
        sqlModel.where(this.idName, SqlModel.SqlOperator.equals,id);
        Map<String, Object> map = SqlHelper.getUpdateSql(sqlModel,this.tClass);
        return super.getSqlSession().update(commonNamespace+"update",map)>0;
    }

    protected T getResult(HashMap<String, Object> hashMap) {
        try {
            T entity = this.tClass.newInstance();
            Field[] fields = this.tClass.getDeclaredFields();
            for (Field field :
                    fields) {
                String name = field.getName();
                FieldName fn = field.getAnnotation(FieldName.class);
                if (fn != null)
                    name = fn.value();
                if (hashMap.containsKey(name)) {
                    field.setAccessible(true);
                    System.out.println(field.getName()+"-----"+field.getType().getName());
                    switch (field.getType().getName()) {
                        case "int":
                            field.setInt(entity, Integer.parseInt(hashMap.get(name).toString()));
                            break;
                        default:
                            field.set(entity, hashMap.get(name));
                            break;
                    }
                }
            }
            return entity;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected List<T> getListResult(List<HashMap<String, Object>> result){
        List<T> list = new ArrayList<>();
        for (HashMap<String, Object> hm : result) {
            list.add(getResult(hm));
        }
        return list;
    }

    private void putBeanToMap(T bean, HashMap<String, Object> map) {
        Field[] fields = this.tClass.getDeclaredFields();
        String strField = "";
        String strValue =  "";
        for (Field field :
                fields) {
            FieldName fn = field.getAnnotation(FieldName.class);
            String name = field.getName();
            if (fn != null)
                name = fn.value();
            if(name.equals(this.idName))
                continue;
            field.setAccessible(true);
            strField+=name+",";
            strValue+="#{"+name+"},";
            try {
                map.put(name,field.get(bean));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        map.put("fields",strField.substring(0,strField.length()-1));
        map.put("values",strValue.substring(0,strValue.length()-1));
    }
}
