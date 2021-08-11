package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

@Service
public class LabelService {

    @Autowired
    private LabelDao labeldao;

    @Autowired
    private IdWorker idWorker;
    /**
     * 增加标签
     * @param label
     */
    public void addLabel(Label label) {
        //利用idworker增加id
//        label.setId(idWorker.nextId()+"");
        //设置初始化state
        label.setState("0");
        //设置初始化fans
        label.setFans(0L);
        //设置初始化count
        label.setCount(0L);
        this.labeldao.insert(label);
    }
}
