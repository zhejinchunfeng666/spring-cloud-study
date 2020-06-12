package com.zf.study.soa.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "mem",fallback = MemFeignClientHystrix.class)
public interface MemFeignClient {

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    String login(@RequestParam String username, @RequestParam String password);
}
