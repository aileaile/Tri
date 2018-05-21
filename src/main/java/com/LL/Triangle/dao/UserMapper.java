package com.LL.Triangle.dao;

import com.LL.Triangle.pojo.po.UserPo;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    int deleteById(Integer id);

    int insert(UserPo record);

    UserPo selectById(Integer id);

    UserPo selectByName(String name);

    int updateById(UserPo record);

    UserPo findByNameAndPassword(@Param("name")String name, @Param("password")String password);

}