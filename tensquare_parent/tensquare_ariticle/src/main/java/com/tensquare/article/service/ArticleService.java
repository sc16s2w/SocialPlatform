package com.tensquare.article.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.article.client.NoticeClient;
import com.tensquare.article.dao.ArticleDao;
import com.tensquare.article.pojo.Article;
import com.tensquare.notice.pojo.Notice;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private NoticeClient noticeClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 查询所有的文章
     * @return
     */
    public List findAll() {

        return articleDao.selectList(null);
    }

    /**
     * 查询单个文章
     * @param id
     * @return
     */
    public Article findById(String id) {
        return articleDao.selectById(id);
    }

    /**
     * 增加单个文章
     * @param article
     * @return
     */
    @Transactional
    public void addArticle(Article article) {
        //使用分布式id生成器
        article.setId(idWorker.nextId()+"");
        //初始化一些数据
        article.setVisits(0);
        article.setThumbup(0);
        article.setComment(0);
        //新增
        this.articleDao.insert(article);
        //找到所有的订阅者
        String authorKey = "article_author_" + article.getUserid();
        Set<String> set = redisTemplate.boundSetOps(authorKey).members();
        Notice notice = null;//增强代码的复用度
        if(set!=null||set.size()>0){
            for(String followers:set){
                //生成所有的消息
                notice = new Notice();
                notice.setId(idWorker.nextId()+"");
                notice.setOperatorId(article.getUserid());
                notice.setReceiverId(followers);
                notice.setAction("publish");
                notice.setTargetType("article");
                notice.setTargetId(article.getId());
                notice.setCreatetime(new Date());
                notice.setType("sys");
                notice.setState("0");
                //给订阅者发送消息
                this.noticeClient.addNotice(notice);
            }
        }
        rabbitTemplate.convertAndSend("article_subscribe", article.getUserid(), article.getId());
    }

    /**
     * 修改指定文章
     * @param article
     * @return
     */
    public void updateArticle(String id, Article article) {
        article.setId(id);
        this.articleDao.updateById(article);
    }


    /**
     * 删除指定文章
     * @param id
     * @return
     */
    public void deleteArticle(String id) {
        this.articleDao.deleteById(id);
    }

    /**
     * 根据条件查询，所有的条件都需要进行判断，但是遍历需要使用反射的方式
     * @param map
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findByPage(Map<String, Object> map, Integer page, Integer size) {
        //设置查询条件
        EntityWrapper<Article> wrapper = new EntityWrapper<>();
        Set<String> keyset = map.keySet();
        for(String key:keyset){
            if(map.get(key)!=null){
                wrapper.eq(key,map.get(key));
            }
        }
        //设置分页参数
        Page<Article> pageData = new Page<>(page,size);
        //执行查询
        //第一个是分页参数。第二个是查询条件
        List list = articleDao.selectPage(pageData, wrapper);
        pageData.setRecords(list);
        //返回
        return pageData;
    }

    /**
     * 点赞数+1
     * @param id
     */
    @Transactional
    public void thumbUp(String id) {
        Article article = this.articleDao.selectById(id);
        article.setThumbup(article.getThumbup()+1);
        this.articleDao.updateById(article);
        String authorKey = "article_author_" + article.getUserid();
        String userId = "2";
        Notice notice = new Notice();
        notice.setId(idWorker.nextId()+"");
        notice.setReceiverId(article.getUserid());
        notice.setOperatorId(userId);
        notice.setAction("thumbup");
        notice.setTargetType("Article");
        notice.setTargetId(article.getId());
        notice.setCreatetime(new Date());
        notice.setType("sys");
        notice.setState("0");
        this.noticeClient.addNotice(notice);
    }

    /**
     * 根据频道id查询数据
     * @param channelId
     * @param page
     * @param size
     * @return
     */
    public Page<Article> queryArticleByChannelId(String channelId, Integer page, Integer size) {
        EntityWrapper<Article> wrapper = new EntityWrapper<>();
        wrapper.eq("channelid",channelId);
        Page<Article> pageData = new Page<>(page,size);
        List list = articleDao.selectPage(pageData, wrapper);
        pageData.setRecords(list);         //返回
        return pageData;

    }

    /**
     *  根据专栏ID获取文章列表
     * @param columnId
     * @param page
     * @param size
     * @return
     */
    public Page<Article> queryArticleByColumnId(String columnId, Integer page, Integer size) {
        EntityWrapper<Article> wrapper = new EntityWrapper<>();
        wrapper.eq("columnid",columnId);
        Page<Article> pageData = new Page<>(page,size);
        List list = articleDao.selectPage(pageData,wrapper);
        pageData.setRecords(list);
        return pageData;
    }


    /**
     * 文章审核，将文章的状态更新到1
     * @param id
     */
    public void examineArticle(String id) {
        Article article = this.articleDao.selectById(id);
        article.setState("1");
        this.articleDao.updateById(article);
    }


    /**
     * 查询头条文章
     * @return
     */
    public List<Article> topArticle() {
        EntityWrapper<Article> wrapper = new EntityWrapper<>();
        wrapper.eq("istop","1");
        List<Article> result = this.articleDao.selectList(wrapper);
        return result;
    }

    /**
     *  订阅或者取消订阅文章作者
     * @param userId
     * @param articleId
     * @return
     */
    public Boolean subscribe(String userId, String articleId) {
        //传统方法
//        //根据文章id查询文章作者id
//        String authorId = articleDao.selectById(articleId).getUserid();
//        String userKey = "article_subscribe_" + userId;
//        String authorKey = "article_author_" + authorId;
//        //查询该用户是否已经订阅作者
//        Boolean flag = this.redisTemplate.boundSetOps(userKey).isMember(authorId);
//        if (flag) {
//            //如果为flag为true，已经订阅,则取消订阅
//            this.redisTemplate.boundSetOps(userKey).remove(authorId);
//            this.redisTemplate.boundSetOps(authorKey).remove(userId);
//            return false;
//        } else {
//            // 如果为flag为false，没有订阅，则进行订阅
//            this.redisTemplate.boundSetOps(userKey).add(authorId);
//            this.redisTemplate.boundSetOps(authorKey).add(userId);
//            return true;
//        }
        //改造方法,利用rabbitMQ
        //查询文章id
        String authorId = articleDao.selectById(articleId).getUserid();
        String userKey = "article_subscribe_" + userId;
        String authorKey = "article_author_" + authorId;
        //创建rabbitmq管理器
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate.getConnectionFactory());
        //声明交换机，处理文章新增消息
        DirectExchange exchange = new DirectExchange("article_subscribe");
        rabbitAdmin.declareExchange(exchange);
        //声明队列，每个用户都有自己的队列。通过用户ID进行区分 true表示是否持久化存储
        Queue queue = new Queue("article_subscribe"+userId,true);
        //声明交换机和队列的绑定关系，需要确保队列只收到对应作者的新增文章消息
        //第一个是队列，第二个是交换机，第三个是队列
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(authorId);
        //如果取消订阅，删除队列绑定徐爱许
        //如果订阅，增加绑定关系
        Boolean flag = redisTemplate.boundSetOps(userKey).isMember(authorId);
        if (flag) {
            //如果为flag为true，已经订阅,则取消订阅
            redisTemplate.boundSetOps(userKey).remove(authorId);
            redisTemplate.boundSetOps(authorKey).remove(userId);

            //删除绑定的队列
            rabbitAdmin.removeBinding(binding);
            return false;
        } else {
            // 如果为flag为false，没有订阅，则进行订阅
            redisTemplate.boundSetOps(userKey).add(authorId);
            redisTemplate.boundSetOps(authorKey).add(userId);

            //声明队列和绑定队列
            rabbitAdmin.declareQueue(queue);
            rabbitAdmin.declareBinding(binding);

            return true;
        }

    }
}
