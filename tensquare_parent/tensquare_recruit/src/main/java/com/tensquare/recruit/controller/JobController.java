package com.tensquare.recruit.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.recruit.pojo.Job;
import com.tensquare.recruit.service.JobService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("recruit")
public class JobController {
    @Autowired
    private JobService jobService;

    /**
     * 增加职业
     * @param job
     * @return
     */
    @PostMapping
    public Result addRecruit(@RequestBody Job job){
        this.jobService.addRecruit(job);
        return new Result(true, StatusCode.OK,"增加工作成功");
    }

    /**
     * 查询所有工作
     * @return
     */
    @GetMapping
    public Result getAllRecruit(){
        List<Job> result = this.jobService.getAllRecruit();
        return new Result(true, StatusCode.OK,"查询所有工作成功",result);
    }

    /**
     * 查询单个工作
     * @param jobId
     * @return
     */
    @GetMapping("/{jobId}")
    public Result getRecruitById(@PathVariable("jobId")String jobId){
        Job job = this.jobService.getRecruitById(jobId);
        return new Result(true, StatusCode.OK,"查询单个工作成功",job);
    }

    /**
     * 修改工作
     * @param jobId
     * @return
     */
    @PutMapping("/jobId")
    public Result updateRecruit(@PathVariable("jobId")String jobId,
                                @RequestBody Job job){
        this.jobService.updateRecruit(jobId,job);
        return new Result(true, StatusCode.OK,"修改工作成功");
    }

    /**
     * 删除工作
     * @param jobId
     * @return
     */
    @DeleteMapping("/jobId")
    public Result deleteRecruit(@PathVariable("jobId")String jobId){
        this.jobService.deleteRecruit(jobId);
        return new Result(true,StatusCode.OK,"删除工作成功");
    }

    /**
     * 查询工作
     * @param map
     * @return
     */
    @PostMapping("/search")
    public Result searchRecruit(@RequestBody Map<String,Object> map){
        List<Job> result = this.jobService.searchRecruit(map);
        return new Result(true,StatusCode.OK,"查询工作成功",result);
    }

    /**
     * 分页查询工作
     * @param map
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/search/{page]/{size}")
    public Result searchRecruitByPage(@RequestBody Map<String,Object> map,
                                      @PathVariable("page") Integer page,
                                      @PathVariable("size") Integer size){
        Page<Job> result = this.jobService.searchRecruitByPage(map,page,size);
        PageResult<Job> pageResult = new PageResult<>(
                result.getTotal(),result.getRecords());
        return new Result(true,StatusCode.OK,"查询工作成功",result);
    }

}
