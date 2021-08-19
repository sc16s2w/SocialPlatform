//package com.tensquare.article.controller;
//
//import entity.Result;
//import entity.StatusCode;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@ControllerAdvice
//public class BaseExceptionHandler {
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public Result handler(Exception e){
//        System.out.println("异常处理");
//        return new Result(false, StatusCode.ERROR,"有异常出现");
//    }
//}
