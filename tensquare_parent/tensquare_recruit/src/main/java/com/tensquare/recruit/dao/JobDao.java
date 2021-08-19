package com.tensquare.recruit.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.tensquare.recruit.pojo.Enterprise;
import com.tensquare.recruit.pojo.Job;
import jdk.jfr.Name;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JobDao extends BaseMapper<Job> {
}
