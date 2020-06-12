package com.zf.study.soa.feign;

import org.springframework.stereotype.Component;

@Component
public class MemFeignClientHystrix implements MemFeignClient{

    @Override
    public String login(String username, String password) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"status\":\"false\",\"msg\":\"\"}");
        return sb.toString();
    }
}
