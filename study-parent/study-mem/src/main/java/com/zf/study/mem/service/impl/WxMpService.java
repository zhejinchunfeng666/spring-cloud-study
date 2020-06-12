package com.zf.study.mem.service.impl;

import com.zf.study.mem.config.WechatConfig;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class WxMpService extends WxMpServiceImpl {

    @Autowired
    private WechatConfig wechatConfig;

    @PostConstruct
    public void init(){
        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
        configStorage.setAppId(wechatConfig.getAppId());
        configStorage.setSecret(wechatConfig.getAppSecret());
        super.setWxMpConfigStorage(configStorage);
    }

}
