package com.tensquare.notice.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.notice.client.ArticleClient;
import com.tensquare.notice.client.UserClient;
import com.tensquare.notice.dao.NoticeDao;
import com.tensquare.notice.dao.NoticeFreshDao;
import com.tensquare.notice.pojo.Notice;
import com.tensquare.notice.pojo.NoticeFresh;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.*;

@Service
public class NoticeService {
    @Autowired
    private NoticeDao noticeDao;

    @Autowired
    private NoticeFreshDao noticeFreshDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private UserClient userClient;

    @Autowired
    private ArticleClient articleClient;

    /**
     * 根据id查询消息成功
     * @param noticeId
     * @return
     */
    public Notice getNoticeById(String noticeId) {
        Notice notice = this.noticeDao.selectById(noticeId);
        this.getNoticeInfo(notice);
        return notice;
    }

    /**
     * 根据条件分页查询消息通知
     * @param map
     * @param page
     * @param size
     * @return
     */
    public Page<Notice> searchNotice(Map<String, Object> map, Integer page, Integer size) {
        Set<String> keySet = map.keySet();
        EntityWrapper<Notice> wrapper = new EntityWrapper<>();
        for(String key:keySet){
            if(map.get(key)!=null){
                wrapper.eq(key,map.get(key));
            }
        }
        Page<Notice> data = new Page<>(page,size);
        List<Notice> list = this.noticeDao.selectPage(data,wrapper);
        for(Notice notice:list){
            getNoticeInfo(notice);
        }
        data.setRecords(list);
        return data;
    }

    /**
     * 新增通知
     * @param notice
     */
    @Transactional
    public void addNotice(Notice notice) {
        notice.setState("0");//0表示该消息未读
        notice.setCreatetime(new Date());
        String id = idWorker.nextId()+"";
        notice.setId(id);
        this.noticeDao.insert(notice);
        NoticeFresh noticeFresh = new NoticeFresh();
        noticeFresh.setUserId(notice.getReceiverId());
        noticeFresh.setNoticeId(id);
        this.noticeFreshDao.insert(noticeFresh);
    }

    /**
     * 修改通知
     * @param notice
     */
    @Transactional
    public void updateNotice(Notice notice) {
        this.noticeDao.updateById(notice);
        NoticeFresh noticeFresh = new NoticeFresh();
        noticeFresh.setNoticeId(notice.getId());
        noticeFresh.setUserId(notice.getReceiverId());
        this.noticeFreshDao.update(noticeFresh,new EntityWrapper<>(noticeFresh));
    }

    /**
     * 根据用户id查询该用户的待推送消息（新消息）
     * @param userId
     * @param page
     * @param size
     * @return
     */
    public Page<NoticeFresh> getFresh(String userId, Integer page, Integer size) {
        EntityWrapper<NoticeFresh> wrapper = new EntityWrapper<>();
        wrapper.eq("userId",userId);
        Page<NoticeFresh> data = new Page<>(page,size);
        List list = this.noticeFreshDao.selectPage(data,wrapper);
        data.setRecords(list);
        return data;
    }

    /**
     * 删除新信息
     * @param noticeFresh
     */
    public void deleteFresh(NoticeFresh noticeFresh) {
        this.noticeFreshDao.delete(new EntityWrapper<>(noticeFresh));
    }

    public void getNoticeInfo(Notice notice){
       Result userResult = userClient.getUserById(notice.getOperatorId());
       HashMap userMap = (HashMap) userResult.getData();
       notice.setOperatorName((String) userMap.get("nickname"));
        if ("article".equals(notice.getTargetType())) {
            Result articleResult = articleClient.findById(notice.getTargetId());
            HashMap articleMap = (HashMap) articleResult.getData();
            notice.setTargetName((String) articleMap.get("title"));
        }
    }
}
