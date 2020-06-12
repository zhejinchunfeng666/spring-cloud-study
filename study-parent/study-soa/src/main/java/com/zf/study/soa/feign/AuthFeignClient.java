package com.zf.study.soa.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "auth")
public interface AuthFeignClient {

    @RequestMapping(value = "/oauth/token",method = RequestMethod.POST)
    Object getToken(@RequestParam Map<String,String> params);
}
