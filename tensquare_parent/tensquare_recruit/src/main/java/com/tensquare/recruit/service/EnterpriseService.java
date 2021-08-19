package com.tensquare.recruit.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.recruit.dao.EnterpriseDao;
import com.tensquare.recruit.pojo.Enterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class EnterpriseService {
    @Autowired
    private EnterpriseDao enterpriseDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有企业成功
     * @return
     */
    public List<Enterprise> getAllEnterprises() {
        return this.enterpriseDao.selectList(null);
    }

    /**
     * 增加企业
     * @param enterprise
     */
    public void addEnterprise(Enterprise enterprise) {
        enterprise.setId(idWorker.nextId()+"");
        enterprise.setIshot("0");
        this.enterpriseDao.insert(enterprise);
    }

    /**
     * 查询单个企业
     * @param enterpriseId
     * @return
     */
    public Enterprise getEnterpriseById(String enterpriseId) {
        return this.enterpriseDao.selectById(enterpriseId);
    }

    /**
     * 修改企业
     * @param enterprise
     */
    public void updateEntreprise(Enterprise enterprise) {
        this.enterpriseDao.update(enterprise,new EntityWrapper<>(enterprise));
    }

    /**
     * 删除企业
     * @param enterpriseId
     */
    public void deleteEnterprise(String enterpriseId) {
        this.enterpriseDao.deleteById(enterpriseId);
    }

    /**
     * 根据用户输入的条件查找企业
     * @return
     * @param map
     */
    public List<Enterprise> searchEnterprise(Map<String, Object> map) {
        EntityWrapper<Enterprise> wrapper = new EntityWrapper<>();
        Set<String> enterprises = map.keySet();
        for(String str:enterprises){
            if(map.get(str)!=null) wrapper.eq(str, map.get(str));
        }
        List<Enterprise> result = this.enterpriseDao.selectList(wrapper);
        return result;
    }

    /**
     * 查询热门企业
     * @return
     */
    public List<Enterprise> getHotlist() {
        EntityWrapper<Enterprise> wrapper = new EntityWrapper<>();
        wrapper.eq("ishot","1");
        List<Enterprise> result = this.enterpriseDao.selectList(wrapper);
        return result;
    }


    /**
     * 分页查询企业
     * @param map
     * @param page
     * @param size
     * @return
     */
    public Page<Enterprise> searchByPage(Map<String, Object> map, Integer page, Integer size) {
        EntityWrapper<Enterprise> wrapper = new EntityWrapper<>();
        Set<String> enterprises = map.keySet();
        for(String str:enterprises){
            if(map.get(str)!=null) wrapper.eq(str, map.get(str));
        }
        Page<Enterprise> result = new Page<>(page,size);
        result.setRecords(this.enterpriseDao.selectPage(result,wrapper));
        return result;
    }
}
