package com.zf.study.mem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {
    @Bean
    public WechatConfig wechatConfig(){
        WechatConfig wechatConfig = new WechatConfig();
        return wechatConfig;
    }
}
