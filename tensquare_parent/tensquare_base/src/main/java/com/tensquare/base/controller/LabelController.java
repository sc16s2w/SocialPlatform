package com.tensquare.base.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import entity.PageResult;
import entity.Result;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("label")
public class LabelController {
    @Autowired
    private LabelService labelService;

    /**
     * 增加标签
     * @param label
     * @return
     */
    @PostMapping
    public Result addLabel(@RequestBody Label label){
        this.labelService.addLabel(label);
        return new Result(true, StatusCode.OK,"增加标签成功");
    }

    /**
     * 查询所有标签
     * @return
     */
    @GetMapping
    public Result getAllLabel(){
        List<Label> result = this.labelService.getAllLabel();
        return new Result(true,StatusCode.OK,"查询所有标签成功",result);

    }

    /**
     * 查询推荐的标签
     * @return
     */
    @GetMapping("/toplist")
    public Result getRecommendLabel(){
        List<Label> result = this.labelService.getRecommendLabel();
        return new Result(true,StatusCode.OK,"查询推荐标签",result);
    }

    /**
     * 查询有效标签 state=1
     * @return
     */
    @GetMapping("/list")
    public Result getValidLabel(){
        List<Label> result = this.labelService.getValidLabel();
        return new Result(true,StatusCode.OK,"查询有效标签",result);
    }

    /**
     * 查询特定标签
     * @param labelId
     * @return
     */
    @GetMapping("/{labelId}")
    public Result getOneLabel(@PathVariable("labelId")String labelId){
        Label label = this.labelService.getOneLabel(labelId);
        return new Result(true,StatusCode.OK,"查询特定标签成功",label);
    }

    /**
     * 修改标签成功
     * @param labelId
     * @param label
     * @return
     */
    @PutMapping("/{labelId}")
    public Result updateLabel(@PathVariable("labelId")String labelId,
                              @RequestBody Label label){
        this.labelService.updateLabel(labelId,label);
        return new Result(true,StatusCode.OK,"修改标签成功");
    }

    /**
     * 删除标签
     * @param labelId
     * @return
     */
    @DeleteMapping("/{labelId}")
    public Result deleteLabel(@PathVariable("labelId")String labelId){
        this.labelService.deleteLabel(labelId);
        return new Result(true,StatusCode.OK,"删除标签成功" );
    }

    /**
     * 根据输入条件查询
     * @param map
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result searchLabel(@PathVariable("page") Integer page,
                              @PathVariable("size") Integer size,
                              @RequestBody Map<String,Object> map){
        Page<Label> data= this.labelService.searchLabel(map,page,size);
        PageResult<Label> pageResult = new PageResult<>(
                data.getTotal(), data.getRecords()
        );
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }
}
