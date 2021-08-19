package com.tensquare.recruit.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.recruit.pojo.Enterprise;
import com.tensquare.recruit.service.EnterpriseService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("enterprise")
public class EnterpriseController {
    @Autowired
    private EnterpriseService enterpriseService;

    /**
     * 查询所有企业成功
     * @return
     */
    @GetMapping
    public Result getAllEnterprises(){
        List<Enterprise> result = this.enterpriseService.getAllEnterprises();
        return new Result(true, StatusCode.OK,"查询所有企业成功",result);

    }

    /**
     * 增加企业
     * @param enterprise
     * @return
     */
    @PostMapping
    public Result addEnterprise(@RequestBody Enterprise enterprise){
        this.enterpriseService.addEnterprise(enterprise);
        return new Result(true,StatusCode.OK,"增加企业成功");
    }

    /**
     * 查询单个企业
     * @param enterpriseId
     * @return
     */
    @GetMapping("/{enterpriseId}")
    public Result getEnterpriseById(@PathVariable("enterpriseId") String enterpriseId){
        Enterprise enterprise = this.enterpriseService.getEnterpriseById(enterpriseId);
        return new Result(true,StatusCode.OK,"查询单个企业成功",enterprise);
    }

    /**
     * 修改企业
     * @param enterprise
     * @param enterpriseId
     * @return
     */
    @PutMapping("/{enterpriseId}")
    public Result updateEntreprise(@RequestBody Enterprise enterprise,
                                   @PathVariable("enterpriseId") String enterpriseId){
        enterprise.setId(enterpriseId);
        this.enterpriseService.updateEntreprise(enterprise);
        return new Result(true,StatusCode.OK,"修改企业成功");
    }

    /**
     * 删除企业
     * @param enterpriseId
     * @return
     */
    @DeleteMapping("/{enterpriseId}")
    public Result deleteEnterprise(@PathVariable("enterpriseId") String enterpriseId){
        this.enterpriseService.deleteEnterprise(enterpriseId);
        return new Result(true,StatusCode.OK,"删除企业成功");
    }

    /**
     * 根据用户输入的条件查找企业
     * @param map
     * @return
     */
    @PostMapping("/search")
    public Result searchEnterprise(@RequestBody Map<String,Object> map){
        List<Enterprise> Enterprise = this.enterpriseService.searchEnterprise(map);
        return new Result(true,StatusCode.OK,"查找企业成功",Enterprise);
    }

    /**
     * 查询热门企业
     * @return
     */
    @GetMapping("/hotlist")
    public Result getHotlist(){
        List<Enterprise> hotList = this.enterpriseService.getHotlist();
        return new Result(true,StatusCode.OK,"查找热门企业",hotList);
    }

    /**
     * 查询企业分页
     * @param map
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result searchByPage(@RequestBody Map<String,Object> map,
                               @PathVariable("page") Integer page,
                               @PathVariable("size") Integer size){
        Page<Enterprise> result = this.enterpriseService.searchByPage(map,page,size);
        PageResult<Enterprise> pageResult = new PageResult<>(result.getTotal(),
                result.getRecords());
        return new Result(true,StatusCode.OK,"查找企业成功",pageResult);
    }


}
