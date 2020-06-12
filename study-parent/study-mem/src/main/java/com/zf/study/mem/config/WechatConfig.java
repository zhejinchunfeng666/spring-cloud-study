package com.zf.study.mem.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("wechat")
@Data
public class WechatConfig {
    private String AppId;
    private String AppSecret;
}
