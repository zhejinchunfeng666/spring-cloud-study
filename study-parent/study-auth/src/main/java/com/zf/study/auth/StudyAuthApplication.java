package com.zf.study.auth;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StudyAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudyAuthApplication.class,args);
    }
}
