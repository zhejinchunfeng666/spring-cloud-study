package com.zf.study.soa.controller;

import com.zf.study.core.constant.SoaAPIConstants;
import com.zf.study.soa.feign.MemFeignClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TestController {

    @Autowired
    private MemFeignClient memFeignClient;

    /**
     *
     * 不需要token就能访问
     * @return
     */
    @RequestMapping(value = "/open/test",method = RequestMethod.POST)
    public Object feignTest(@RequestParam("name") String name) throws Exception{
        if(StringUtils.isBlank(name)){
            throw new IOException();
        }
        return "gateway success";
    }

    /**
     * 不测试，接口需要token才能访问
     * @return
     */

    @RequestMapping(value = "/zf/test",method = RequestMethod.POST)
    public Object getMemInfo(){
        return "success";
    }
}
