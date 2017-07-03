package com.hw.springboot.dao;

import com.hw.springboot.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Created by huwei on 2017/6/15.
 */
@Repository
public interface IUserDao {
    int insertUser(User user);

    @Delete("delete from user where id=#{id}")
    int delete(long id);

    @Select("select * from user where id=#{id}")
    User getUser(@Param("id") int id);
}
