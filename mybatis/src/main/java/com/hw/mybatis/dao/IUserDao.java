package com.hw.mybatis.dao;

import com.hw.mybatis.model.UserDb;

import java.util.List;

/**
 * Created by Wei Hu (J) on 2017/1/12.
 */
public interface IUserDao extends ICommonDao<UserDb> {
    List<UserDb> getUserPage(String username);

    UserDb getUserById(int id);
}
