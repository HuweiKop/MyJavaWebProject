package com.hw.mybatis.dao.impl;

import com.hw.mybatis.dao.IUserDao;
import com.hw.mybatis.model.UserDb;
import com.hw.mybatis.sql.SqlHelper;
import com.hw.mybatis.sql.SqlModel;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wei Hu (J) on 2017/1/12.
 */
@Repository("userDao")
public class UserDaoImpl extends CommonDaoImpl<UserDb> implements IUserDao {

    private final static String userNamespace = "com.yizhi.test.dao.user.IUserDao.";

    public UserDaoImpl() throws IOException {
        super(UserDb.class);
    }

    @Override
    public List<UserDb> getUserPage(String username) {
        SqlModel sqlModel = new SqlModel();
        sqlModel.page(1,5).orderBy("id desc").where("name", SqlModel.SqlOperator.contain,username);
//                .where("age", SqlModel.SqlOperator.greaterthanEquals,0);
        Map<String, Object> map = SqlHelper.getQuerySql(sqlModel,UserDb.class);
        List<HashMap<String, Object>> result = super.getSqlSession().selectList(commonNamespace+"getByFilter", map);
        return getListResult(result);
    }

    @Override
    public UserDb getUserById(int id) {
        UserDb user = super.getSqlSession().selectOne(userNamespace+"getUserById",id);
        return user;
    }
}
