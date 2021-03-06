package com.tensquare.notice.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value="tensquare-article")
public interface ArticleClient {
    /**
     * 根据id查到文章
     * @param id
     * @return
     */
    @RequestMapping(value = "article/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable("id")String id);
}
