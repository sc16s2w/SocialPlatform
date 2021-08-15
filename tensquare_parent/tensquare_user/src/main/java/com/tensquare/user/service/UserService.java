package com.tensquare.user.service;

import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 根据输入的用户名密码判断登陆是否成功
     * @param user
     * @return
     */
    public User login(User user) {
        return null;
    }


    /**
     * 根据id查询用户
     * @param userId
     * @return
     */
    public User getUserById(String userId) {
        return userDao.selectById(userId);
    }
}
