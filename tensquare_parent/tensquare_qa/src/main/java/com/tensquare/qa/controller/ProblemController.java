package com.tensquare.qa.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("problem")
public class ProblemController {
    @Autowired
    private ProblemService problemService;

    /**
     * 查询所有问题
     * @return
     */
    @GetMapping
    public Result getProblems(){
        List<Problem> result = this.problemService.getProblems();
        return new Result(true, StatusCode.OK,"查询所有问题成功",result);
    }

    /**
     * 查询问题成功
     * @param problem
     * @return
     */
    @PostMapping
    public Result addProblem(@RequestBody Problem problem){
        this.problemService.addProblem(problem);
        return new Result(true,StatusCode.OK,"增加问题成功");
    }

    /**
     * 更新问题
     * @param problem
     * @param problemId
     * @return
     */
    @PutMapping("/{problemId}")
    public Result updateProblem(@RequestBody Problem problem,
                                @PathVariable("problemId") String problemId){
        this.problemService.updateProblem(problem, problemId);
        return new Result(true,StatusCode.OK,"更新问题成功");
    }

    /**
     * 根据id查询问题
     * @param problemId
     * @return
     */
    @GetMapping("/{problemId}")
    public Result selectProblem(@PathVariable("problemId") String problemId){
        Problem problem = this.problemService.selectProblem(problemId);
        return new Result(true,StatusCode.OK,"根据id查询问题",problem);
    }

    /**
     * 删除问题
     * @param problemId
     * @return
     */
    @DeleteMapping("/{problemId}")
    public Result deleteProblem(@PathVariable("problemId") String problemId){
        this.problemService.deleteProblem(problemId);
        return new Result(true,StatusCode.OK,"删除问题成功");
    }

    /**
     * 查询某个问题
     * @param map
     * @return
     */
    @PostMapping("/search")
    public Result searchProblem(@RequestBody Map<String,Object> map){
        List<Problem> result = this.problemService.searchProblem(map);
        return new Result(true,StatusCode.OK,"查询某个问题成功",result);
    }

    /**
     * 分页查询某个问题
     * @param map
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result searchProblemByPage(@RequestBody Map<String,Object> map,
                                @PathVariable("page") Integer page,
                                @PathVariable("size") Integer size){
        Page<Problem> result = this.problemService.searchProblemByPage(map,page,size);
        PageResult<Problem> pageResult = new PageResult<>(result.getTotal(),result.getRecords());
        return new Result(true,StatusCode.OK,"查询某个问题成功",pageResult);
    }

}
