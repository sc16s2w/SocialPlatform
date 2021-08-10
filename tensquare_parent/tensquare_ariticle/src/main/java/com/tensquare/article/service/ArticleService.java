package com.tensquare.article.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.article.dao.ArticleDao;
import com.tensquare.article.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

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
    public void addArticle(Article article) {
        //使用分布式id生成器
        article.setId(idWorker.nextId()+"");
        //初始化一些数据
        article.setVisits(0);
        article.setThumbup(0);
        article.setComment(0);
        //新增
        this.articleDao.insert(article);

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
    public void thumbUp(String id) {
        Article article = this.articleDao.selectById(id);
        article.setThumbup(article.getThumbup()+1);
        this.articleDao.updateById(article);
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
}
