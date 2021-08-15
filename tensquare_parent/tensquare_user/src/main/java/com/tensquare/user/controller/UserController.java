package com.tensquare.user.controller;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 根据输入的用户名密码判断登陆是否成功
     * @param user
     * @return
     */
    @PostMapping("login")
    public Result login(@RequestBody User user){
        User result = userService.login(user);
        if(result != null){
            return new Result(true, StatusCode.OK,"登陆成功",result);
        }
        return new Result(false,StatusCode.OK,"用户名或密码错误");
    }


    /**
     * 根据id查询用户
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public Result getUserById(@PathVariable("userId") String userId){
        User user = this.userService.getUserById(userId);
        return new Result(true,StatusCode.OK,"查询用户成功",user);
    }


}
