package com.zf.study.mem.controller;

import com.zf.study.mem.service.impl.WxMpService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/wechat")
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    /**
     * 拼接授权链接
     * @param url
     * @return
     */
    @RequestMapping("/getCode")
    public Object getCode(@RequestParam String url){
        String s = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
        return s;
    }

    /**
     * code获取AccessToken
     * @param code
     * @return
     * @throws WxErrorException
     */
    public Object getAccessToken(@RequestParam String code) throws WxErrorException {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        return wxMpOAuth2AccessToken;
    }

    /**
     * 获取用户信息
     * @param code
     * @return
     * @throws WxErrorException
     */
    public Object getUserInfo(@RequestParam String code) throws WxErrorException {
        WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(this.wxMpService.oauth2getAccessToken(code), null);
        return  wxMpUser;
    }
}
