package com.tensqure.split.repository;

import com.tensqure.split.pojo.Split;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface SplitRepository extends MongoRepository<Split,String> {
    //通过查询方法进行查询
    //根据发布时间查询
    List<Split> findByPublishdateAndThumbup(Date date, Integer thumbup);
    //根据用户id查询
    List<Split> findByUseridOrderByPublishdateDesc(String userid);
    //根据文章id查询评论
    List<Split> findByArticleid(String articleId);
}
