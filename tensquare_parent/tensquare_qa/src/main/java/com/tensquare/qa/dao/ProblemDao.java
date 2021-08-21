package com.tensquare.qa.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.tensquare.qa.pojo.Problem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProblemDao extends BaseMapper<Problem> {
}
