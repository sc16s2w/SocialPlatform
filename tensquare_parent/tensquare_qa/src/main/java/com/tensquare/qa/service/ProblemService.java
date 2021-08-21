package com.tensquare.qa.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.qa.dao.ProblemDao;
import com.tensquare.qa.pojo.Problem;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ProblemService {
    @Autowired
    private ProblemDao problemDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有问题
     * @return
     */
    public List<Problem> getProblems() {
        return this.problemDao.selectList(null);
    }

    /**
     * 查询问题成功
     * @param problem
     */
    public void addProblem(Problem problem) {
        problem.setId(idWorker.nextId()+"");
        problem.setCreatetime(new Date());
        problem.setUpdatetime(new Date());
        problem.setVisits(0L);
        problem.setThumbup(0L);
        problem.setReply(0L);
        problem.setSolve("0");
        problem.setReplytime(new Date());
        this.problemDao.insert(problem);
    }

    /**
     * 更新问题
     * @param problem
     * @param problemId
     */
    public void updateProblem(Problem problem, String problemId) {
        problem.setId(problemId);
        problem.setUpdatetime(new Date());
        this.problemDao.updateById(problem);
    }

    /**
     * 根据id查询问题
     * @param problemId
     */
    public Problem selectProblem(String problemId) {
        return this.problemDao.selectById(problemId);
    }

    /**
     * 删除问题
     * @param problemId
     */
    public void deleteProblem(String problemId) {
        this.problemDao.deleteById(problemId);
    }

    /**
     * 查询某个问题
     * @param map
     * @return
     */
    public List<Problem> searchProblem(Map<String, Object> map) {
        EntityWrapper<Problem> wrapper = new EntityWrapper<>();
        Set<String> temp = map.keySet();
        for(String s:temp){
            if(map.get(s)!=null) wrapper.eq(s,map.get(s));
        }
        List<Problem> result = this.problemDao.selectList(wrapper);
        return result;
    }

    /**
     * 分页查询某个问题
     * @param map
     * @param page
     * @param size
     * @return
     */
    public Page<Problem> searchProblemByPage(Map<String, Object> map, Integer page, Integer size) {
        EntityWrapper<Problem> wrapper = new EntityWrapper<>();
        Set<String> temp = map.keySet();
        for(String s:temp){
            if(map.get(s)!=null) wrapper.eq(s,map.get(s));
        }
        Page<Problem> result = new Page<>(page,size);
        result.setRecords(this.problemDao.selectPage(result,wrapper));
        return result;
    }
}
