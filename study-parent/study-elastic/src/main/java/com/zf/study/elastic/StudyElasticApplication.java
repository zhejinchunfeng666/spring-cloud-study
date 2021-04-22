package com.zf.study.elastic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.zf.study.*.mapper")
public class StudyElasticApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudyElasticApplication.class,args);
    }
}
