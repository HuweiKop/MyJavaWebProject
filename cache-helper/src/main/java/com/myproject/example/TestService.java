package com.myproject.example;

import com.myproject.annotation.Cache;
import com.myproject.annotation.CacheDelete;
import com.myproject.annotation.CacheKey;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 11:07 2017/10/30
 * @Modified By
 */
@Service
public class TestService {

    @Cache(key = "user", expireTime = 0, isCacheKey = false)
    public List<UserDO> getUser(@CacheKey String name){
        List<UserDO> list = new ArrayList<>(5);
        for(int i=0;i<5;i++){
            UserDO userDO = new UserDO();
            userDO.setId(Long.valueOf(i));
            userDO.setName("user"+name+i);
            list.add(userDO);
        }
        return list;
    }

    @CacheDelete(key = "user")
    public boolean updateUser(@CacheKey String name){
        return true;
    }
}
