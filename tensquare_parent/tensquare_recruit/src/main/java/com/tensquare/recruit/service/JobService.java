package com.tensquare.recruit.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.recruit.dao.JobDao;
import com.tensquare.recruit.pojo.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.*;

@Service
public class JobService {
    @Autowired
    private JobDao jobDao;

    @Autowired
    private IdWorker idWorker;



    /**
     * 查询所有工作
     * @return
     */
    public List<Job> getAllRecruit() {
        return this.jobDao.selectList(null);
    }

    /**
     * 增加职业
     * @param job
     */
    public void addRecruit(Job job) {
        job.setId(idWorker.nextId()+"");
        job.setCreatetime(new Date());
        job.setState("1");
        this.jobDao.insert(job);

    }

    /**
     * 查询单个工作
     * @param jobId
     * @return
     */
    public Job getRecruitById(String jobId) {
        return this.jobDao.selectById(jobId);
    }

    /**
     * 修改工作
     * @param jobId
     */
    public void updateRecruit(String jobId,Job job) {
        job.setId(jobId);
        this.jobDao.updateById(job);
    }

    /**
     * 删除工作
     * @param jobId
     */
    public void deleteRecruit(String jobId) {
        this.jobDao.deleteById(jobId);
    }

    /**
     * 查询工作
     * @param map
     * @return
     */
    public List<Job> searchRecruit(Map<String, Object> map) {
        Set<String> store = map.keySet();
        EntityWrapper<Job> wrapper = new EntityWrapper<>();
        for(String str:store){
            if(map.get(str)!=null){
                wrapper.eq(str,map.get(str));
            }
        }
        List<Job> result = this.jobDao.selectList(wrapper);
        return result;
    }

    /**
     * 分页查询工作
     * @param map
     * @param page
     * @param size
     * @return
     */
    public Page<Job> searchRecruitByPage(Map<String, Object> map, Integer page, Integer size) {
        Set<String> store = map.keySet();
        EntityWrapper<Job> wrapper = new EntityWrapper<>();
        for(String str:store){
            if(map.get(str)!=null){
                wrapper.eq(str,map.get(str));
            }
        }
        Page<Job> result = new Page<>(page,size);
        result.setRecords(this.jobDao.selectPage(result,wrapper));
        return result;
    }
}
