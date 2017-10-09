package com.hw.mybatis.test.dao;

import com.hw.mybatis.test.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 14:07 2017/8/31
 * @Modified By
 */
@Repository
public interface IUserDao {
    int insert(User user);

    int updateUser(User user);

    @Delete("delete from user where id=#{id}")
    int delete(long id);

    @Select("select * from user where id=#{id}")
    User getUser(@Param("id") int id);
}
