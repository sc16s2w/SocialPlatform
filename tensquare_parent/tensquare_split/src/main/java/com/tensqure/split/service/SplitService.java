package com.tensqure.split.service;

import com.tensqure.split.pojo.Split;
import com.tensqure.split.repository.SplitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import util.IdWorker;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SplitService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SplitRepository splitRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询所有评论
     * @return
     */
    public List<Split> getAllSplit() {
        List<Split> result = this.splitRepository.findAll();
        return result;
    }

    /**
     * 查找某个特定评论
     * @param splitId
     * @return
     */
    public Split getSplitByid(String splitId) {
        return this.splitRepository.findById(splitId).get();
    }

    /**
     * 增加评论
     * @param split
     */
    public void addSplit(Split split) {
        String id = idWorker.nextId()+"";
        split.set_id(id);
        split.setThumbup(0);
        split.setPublishdate(new Date());
        this.splitRepository.insert(split);
    }

    /**
     * 修改评论
     * @param splitId
     * @param split
     */
    public void updateSplit(String splitId, Split split) {
        split.set_id(splitId);
        this.splitRepository.save(split);
    }

    /**
     * 删除评论
     * @param splitId
     */
    public void deleteSplit(String splitId) {
        this.splitRepository.deleteById(splitId);
    }


    /**
     * 查询某篇文章下的全部评论
     * @param articleId
     * @return
     */
    public List<Split> findByAriticleId(String articleId) {
        List<Split> list = this.splitRepository.findByArticleid(articleId);
        return list;
    }


    /**
     * 给评论点赞
     * @param splitId
     */
    public void thumbupSplit(String splitId) {
        //用了两次操作，有可能查询和写入的不同导致了赞数不统一
//        Split split = this.splitRepository.findById(splitId).get();
//        split.setThumbup(split.getThumbup()+1);
//        this.splitRepository.save(split);
        //用一种优化的办法
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(splitId));
        Update update = new Update();
        //直接修改数据
        update.inc("thumbup",1);
        //第一个参数是修改的条件
        //第二个参数是修改的数值
        //第三个参数是mongodb的集合名称
        mongoTemplate.updateFirst(query,update,"split");

    }
}
