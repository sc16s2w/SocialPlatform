package com.tensquare.notice.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="tensquare-user")
public interface UserClient {
    /**
     * 根据id查询用户
     * @param userId
     * @return
     */
    @GetMapping("user/{userId}")
    public Result getUserById(@PathVariable("userId") String userId);
}
