package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import entity.PageResult;
import entity.Result;
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
}
