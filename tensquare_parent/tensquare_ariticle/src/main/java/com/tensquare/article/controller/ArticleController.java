package com.tensquare.article.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.article.pojo.Article;
import com.tensquare.article.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("article")
@CrossOrigin
//解决跨域问题
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 测试异常类
     * @return
     */
    @GetMapping("exception")
    public Result error(){
        int a= 1/0;
        return null;
    }

    /**
     * 查询所有的文章
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        List list = articleService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", list);     }

    /**
     * 查询单个文章
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable("id")String id){
        Article article = articleService.findById(id);
        return new Result(true,StatusCode.OK,"查询成功",article);
    }

    /**
     * 增加单个文章
     * @param article
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result addArticle(@RequestBody Article article){
        this.articleService.addArticle(article);
        return new Result(true,StatusCode.OK,"增加成功");
    }

    /**
     * 修改指定文章
     * @param article
     * @return
     */
    @PutMapping("/{id}")
    public Result updateArticle(@PathVariable("id") String id, @RequestBody Article article){
        this.articleService.updateArticle(id,article);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 删除指定文章
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteArticle(@PathVariable("id") String id){
        this.articleService.deleteArticle(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 根据条件查询，所有的条件都需要进行判断，但是遍历需要使用反射的方式
     * @param page
     * @param size
     * @param map
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result findByPage(@PathVariable("page") Integer page,
                             @PathVariable("size") Integer size,
                             @RequestBody Map<String,Object> map){
        //根据条件分页查询
        Page<Article> pageData = articleService.findByPage(map,page,size);
        //封装分页返回对象
        PageResult<Article> pageResult = new PageResult<>(
                pageData.getTotal(),pageData.getRecords()
        );
        //返回数据
        return new Result(true,StatusCode.OK,"查询成功",pageResult);

    }

    /**
     * 点赞数+1
     * @param id
     * @return
     */
    @PutMapping("/thumbup/{id}")
    public Result thumbUp(@PathVariable("id") String id){
        this.articleService.thumbUp(id);
        return new Result(true,StatusCode.OK,"点赞成功");
    }

    /**
     * 根据频道id查询数据
     * @param channelId
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/channel/{channelId}/{page}/{size}")
    public Result queryArticleByChannelId(@PathVariable("channelId") String channelId,
                                          @PathVariable("page") Integer page,
                                          @PathVariable("size") Integer size){
        //根据条件分页查询
        Page<Article> pageData = articleService.queryArticleByChannelId(channelId,page,size);
        PageResult<Article> pageResult = new PageResult<>(
                pageData.getTotal(),pageData.getRecords());
        //返回数据
        return new Result(true,StatusCode.OK,"查询成功",pageResult);


    }

    /**
     * 根据专栏id查询文章
     * @param columnId
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/column/{columnId}/{page}/{size}")
    public Result queryArticleByColumnId(@PathVariable("columnId") String columnId,
                                          @PathVariable("page") Integer page,
                                          @PathVariable("size") Integer size){
        //根据条件分页查询
        Page<Article> pageData = articleService.queryArticleByColumnId(columnId,page,size);
        PageResult<Article> pageResult = new PageResult<>(
                pageData.getTotal(),pageData.getRecords());
        //返回数据
        return new Result(true,StatusCode.OK,"查询成功",pageResult);


    }

    /**
     * 文章审核，将文章的状态更新到1
     * @param id
     * @return
     */
    @PutMapping("/examine/{id}")
    public Result examineArticle(@PathVariable("id")String id){
        this.articleService.examineArticle(id);
        return new Result(true,StatusCode.OK,"文章审核成功");

    }


    /**
     * 查询头条文章
     * @return
     */
    @GetMapping("/top")
    public Result topArticle(){
        List<Article> result = this.articleService.topArticle();
        return new Result(true,StatusCode.OK,"查询成功",result);
    }

}
