package com.zf.study.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StudyWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudyWebApplication.class,args);
    }
}
