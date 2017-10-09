package com.hw.mutil.datasource.dao;

import com.hw.mutil.datasource.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by huwei on 2017/6/15.
 */
public interface IUserDao {
    int insert(User user);

    @Update("update user set username=#{username}  where id=#{id}")
    int updateUser(User user);

    @Delete("delete from user where id=#{id}")
    int delete(long id);

    @Select("select * from user where id=#{id}")
    User getUser(@Param("id") int id);
}
