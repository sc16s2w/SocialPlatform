package com.tensquare.article;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
=======
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
>>>>>>> eureka and rsa service finish
import org.springframework.context.annotation.Bean;
import util.IdWorker;

@SpringBootApplication
@MapperScan("com.tensquare.article.dao")
<<<<<<< HEAD
=======
@EnableEurekaClient
>>>>>>> eureka and rsa service finish
public class ArticleApplication {
    public static void main(String[] args){
        SpringApplication.run(ArticleApplication.class,args);
    }
    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }
}
