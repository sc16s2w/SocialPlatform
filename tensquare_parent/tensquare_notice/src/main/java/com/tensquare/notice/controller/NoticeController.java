package com.tensquare.notice.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.notice.pojo.Notice;
import com.tensquare.notice.pojo.NoticeFresh;
import com.tensquare.notice.service.NoticeService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    /**
     * 根据id查询消息成功
     * @param noticeId
     * @return
     */
    @GetMapping("/{noticeId}")
    public Result getNoticeById(@PathVariable("noticeId") String noticeId){
        Notice notice = this.noticeService.getNoticeById(noticeId);
        return new Result(true, StatusCode.OK,"根据id查询通知成功",notice);

    }

    /**
     * 根据条件分页查询消息通知
     * @param map
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result searchNotice(@PathVariable("page") Integer page,
                              @PathVariable("size") Integer size,
                              @RequestBody Map<String,Object> map){
        Page<Notice> data = this.noticeService.searchNotice(map,page,size);
        PageResult<Notice> result = new PageResult<>(
                data.getTotal(), data.getRecords());
        return new Result(true,StatusCode.OK,"查找通知成功",result);
    }

    // 3. 新增通知
    // http://127.0.0.1:9014/notice  POST
    /**
     * 新增通知
     * @param notice
     * @return
     */
    @PostMapping
    public Result addNotice(@RequestBody Notice notice) {
        this.noticeService.addNotice(notice);
        return new Result(true, StatusCode.OK, "新增通知成功");
    }

    // 4. 修改通知
    // http://127.0.0.1:9014/notice  PUT
    /**
     * 修改通知
     * @param notice
     * @return
     */
    @PutMapping
    public Result updateNotice(@RequestBody Notice notice){
        this.noticeService.updateNotice(notice);
        return new Result(true,StatusCode.OK,"修改通知成功");
    }

    // 5. 根据用户id查询该用户的待推送消息（新消息）
    // http://127.0.0.1:9014/notice/fresh/{userId}/{page}/{size}  GET
    /**
     * 根据用户id查询该用户的待推送消息（新消息）
     * @param userId
     * @param page
     * @param size
     * @return
     */
    //没有考虑过消息的隔离性，用户的ID和当前登陆的id不一致，考虑权限的问题
    @GetMapping("/fresh/{userId}/{page}/{size}")
    public Result getFresh(@PathVariable("userId") String userId,
                           @PathVariable("page") Integer page,
                           @PathVariable("size") Integer size){
        Page<NoticeFresh> noticeFresh = this.noticeService.getFresh(userId,page,size);
        PageResult<NoticeFresh> pageResult = new PageResult<>(noticeFresh.getTotal(),noticeFresh.getRecords());
        return new Result(true,StatusCode.OK,"查询该用户未发送消息成功",pageResult);
    }
    // 6. 删除待推送消息（新消息）
    // http://127.0.0.1:9014/notice/fresh  DELETE

    /**
     * 删除新信息
     * @param noticeFresh
     * @return
     */
    @DeleteMapping("/fresh")
    public Result deleteFresh(@RequestBody NoticeFresh noticeFresh){
        this.noticeService.deleteFresh(noticeFresh);
        return new Result(true,StatusCode.OK,"删除新信息成功");

    }
}
