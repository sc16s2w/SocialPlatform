package com.tensquare.article.client;


import com.tensquare.notice.pojo.Notice;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "tensquare-notice")
public interface NoticeClient {
    /**
     * 新增消息
     * @param notice
     * @return
     */
    @PostMapping("/notice")
    public Result addNotice(@RequestBody Notice notice);
}
