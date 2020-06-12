package com.zf.study.mem.elastic;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.elastic")
@Data
public class ElasticConfig {
    String[] hosts;
    int connectTimeoutMillis = 1000;
    int socketTimeoutMillis = 30000;
    int connectionRequestTimeoutMillis = 500;
    int maxConnPerRoute = 10;
    int maxConnTotal = 30;
    String username;
    String password;
}
