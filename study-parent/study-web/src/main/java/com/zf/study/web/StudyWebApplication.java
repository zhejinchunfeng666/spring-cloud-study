package com.zf.study.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.zf.study"})
//@EnableEurekaClient
public class StudyWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudyWebApplication.class,args);
    }
}
