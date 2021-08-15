package com.tensquare.base.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        label.setId(idWorker.nextId()+"");
        //设置初始化state
        label.setState("0");
        //设置初始化fans
        label.setFans(0L);
        //设置初始化count
        label.setCount(0L);
        this.labeldao.insert(label);
    }

    /**
     * 查询所有标签
     * @return
     */
    public List<Label> getAllLabel() {
        List<Label> result = this.labeldao.selectList(null);
        return result;
    }

    /**
     * 查询推荐的标签
     * @return
     */
    public List<Label> getRecommendLabel() {
        EntityWrapper<Label> wrapper = new EntityWrapper<>();
        wrapper.eq("recommend",1);
        List<Label> result = this.labeldao.selectList(wrapper);
        return result;
    }

    /**
     * 查询有效标签 state=1
     * @return
     */
    public List<Label> getValidLabel() {
        EntityWrapper<Label> wrapper = new EntityWrapper<>();
        wrapper.eq("state","1");
        List<Label> result = this.labeldao.selectList(wrapper);
        return result;
    }


    /**
     * 查询特定标签
     * @param labelId
     * @return
     */
    public Label getOneLabel(String labelId) {
        Label label = this.labeldao.selectById(labelId);
        return label;
    }

    /**
     * 修改标签成功
     * @param labelId
     * @param label
     */
    public void updateLabel(String labelId, Label label) {
        label.setId(labelId);
        this.labeldao.updateById(label);
    }


    /**
     * 删除标签
     * @param labelId
     */
    public void deleteLabel(String labelId) {
        this.labeldao.deleteById(labelId);
    }

    /**
     * 根据输入条件查询
     * @param map
     * @param page
     * @param size
     * @return
     */
    public Page<Label> searchLabel(Map<String, Object> map, Integer page, Integer size) {
        EntityWrapper<Label> wrapper = new EntityWrapper<>();
        Set<String> keySet = map.keySet();
        for(String key: keySet){
            if(map.get(key)!=null){
                wrapper.eq(key,map.get(key));
            }
        }
        Page<Label> data = new Page<>(page,size);
        List<Label> list = this.labeldao.selectList(wrapper);
        data.setRecords(list);
        return data;
    }
}
