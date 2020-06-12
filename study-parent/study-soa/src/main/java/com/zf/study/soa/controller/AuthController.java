package com.zf.study.soa.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zf.study.core.exception.StudyErrorCode;
import com.zf.study.core.exception.StudyException;
import com.zf.study.soa.feign.AuthFeignClient;
import com.zf.study.soa.feign.MemFeignClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/open")
public class AuthController {

    @Value("${study.oauth2.soa.client_id}")
    private String clientId;

    @Value("${study.oauth2.soa.client_secret}")
    private String clientSecret;

    @Value("${study.oauth2.soa.grant_type}")
    private String grantType;

    @Value("${study.oauth2.soa.scope}")
    private String scope;

    @Autowired
    private AuthFeignClient authFeignClient;

    @Autowired
    private MemFeignClient memFeignClient;

    @RequestMapping(value = "/getToken",method = RequestMethod.POST)
    public Object getToken(@RequestBody Map<String,String> params){
        Map<String,String> requestParams = new HashMap<>();
        String userName = params.get("username");
        String passWord = params.get("password");
        if(StringUtils.isAnyBlank(userName,passWord)){
            throw new StudyException(StudyErrorCode.PARAM_MISSING);
        }
        //调用会员服务，实现用户的认证
        String resultStr = memFeignClient.login(userName,passWord);
        JSONObject jsonOBject = JSON.parseObject(resultStr);
        String status = (String) jsonOBject.get("status");
        //用户登录成功，获取token
        if(StringUtils.equals(status,"true")){
            requestParams.put("client_id",clientId);
            requestParams.put("client_secret",clientSecret);
            requestParams.put("grant_type",grantType);
            requestParams.put("scope",scope);
            requestParams.put("username",userName);
            requestParams.put("password",passWord);
            Object result = authFeignClient.getToken(requestParams);
            return result;
        }
        return resultStr;
    }
}
