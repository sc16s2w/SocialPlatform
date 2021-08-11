package com.tensquare.base.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.tensquare.base.pojo.Label;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LabelDao extends BaseMapper<Label> {
}
