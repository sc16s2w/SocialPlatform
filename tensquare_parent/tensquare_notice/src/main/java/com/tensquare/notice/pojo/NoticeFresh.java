package com.tensquare.notice.pojo;


import com.baomidou.mybatisplus.annotations.TableName;


@TableName("tb_notice_fresh")
public class NoticeFresh {
    private String userId;//接收的用户id
    private String noticeId;//接收的信息id

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }
}
