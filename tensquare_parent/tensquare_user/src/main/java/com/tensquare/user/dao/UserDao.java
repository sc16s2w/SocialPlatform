package com.tensquare.user.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.tensquare.user.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
}
