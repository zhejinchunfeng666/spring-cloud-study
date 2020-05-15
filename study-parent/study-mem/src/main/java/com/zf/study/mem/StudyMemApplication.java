package com.zf.study.mem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zf.study.mem.mapper")
public class StudyMemApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudyMemApplication.class,args);
    }
}
