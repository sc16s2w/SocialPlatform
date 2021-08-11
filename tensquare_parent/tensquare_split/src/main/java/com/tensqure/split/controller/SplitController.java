package com.tensqure.split.controller;

import com.tensqure.split.pojo.Split;
import com.tensqure.split.service.SplitService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("split")
public class SplitController {
    @Autowired
    private SplitService splitService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询所有评论
     * @return
     */
    @GetMapping
    public Result getAllSplit(){
        List<Split> result = this.splitService.getAllSplit();
        return new Result(true, StatusCode.OK,"查询所有评论成功",result);
    }

    /**
     * 查找某个特定评论
     * @param splitId
     * @return
     */
    @GetMapping("/{splitId}")
    public Result getSplitByid(@PathVariable("splitId") String splitId){
        Split split = this.splitService.getSplitByid(splitId);
        return new Result(true, StatusCode.OK,"查询单条评论成功",split);
    }

    /**
     * 增加评论
     * @param split
     * @return
     */
    @PostMapping
    public Result addSplit(@RequestBody Split split){
        this.splitService.addSplit(split);
        return new Result(true, StatusCode.OK,"增加评论成功");
    }

    /**
     * 修改评论
     * @param splitId
     * @param split
     * @return
     */
    @PutMapping("{splitId}")
    public Result updateSplit(@PathVariable("splitId")String splitId,
                              @RequestBody Split split){
        this.splitService.updateSplit(splitId,split);
        return new Result(true,StatusCode.OK,"修改评论成功");
    }

    /**
     * 删除评论
     * @param splitId
     * @return
     */
    @DeleteMapping("/{splitId}")
    public Result deleteSplit(@PathVariable("splitId") String splitId){
        this.splitService.deleteSplit(splitId);
        return new Result(true,StatusCode.OK,"删除评论成功");
    }

    /**
     * 查询某篇文章下的全部评论
     * @param articleId
     * @return
     */
    @GetMapping("/article/{articleId}")
    public Result findByArticleId(@PathVariable("articleId")String articleId){
        List<Split> splits = this.splitService.findByAriticleId(articleId);
        return new Result(true,StatusCode.OK,"查询该文章下全部评论成功",splits);
    }

    /**
     * 评论点赞
     * @param splitId
     * @return
     */
    @PutMapping("/thumbup/{splitId}")
    public Result thumbupSplit(@PathVariable("splitId")String splitId){
        String userId ="123";
        //查询用户点赞信息，根据用户和评论id
        Object flag = redisTemplate.opsForValue().get("thumbup_"+userId+"_"+splitId);
        if(flag == null){
            this.splitService.thumbupSplit(splitId);
            redisTemplate.opsForValue().set("thumbup_"+userId+"_"+splitId,1);
            return new Result(true, StatusCode.OK,"评论点赞成功");
        }
        return new Result(false,StatusCode.REMOTEERROR,"该用户已经为该条评论点过赞了");
    }
}
