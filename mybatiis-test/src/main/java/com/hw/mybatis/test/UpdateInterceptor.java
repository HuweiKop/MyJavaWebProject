package com.hw.mybatis.test;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 15:19 2017/8/31
 * @Modified By
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Intercepts(
        {
                @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        }
)
public class UpdateInterceptor implements Interceptor {
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("intercept========================");
        Object[] args = invocation.getArgs();
        MappedStatement statement = (MappedStatement)args[0];
        Object parm = args[1];
        BoundSql boundSql = statement.getBoundSql(parm);
        for (ParameterMapping mapping:boundSql.getParameterMappings()){
            System.out.println(mapping.getProperty());
        }
        System.out.println(boundSql.getSql());
        System.out.println(statement.getSqlCommandType());
        System.out.println(JSONObject.toJSONString(parm));
        Object result = invocation.proceed();
        return result;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {

    }
}
