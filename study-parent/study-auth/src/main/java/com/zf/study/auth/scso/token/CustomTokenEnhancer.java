package com.zf.study.auth.scso.token;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        final Map<String,Object> additionalInfo = new HashMap<>();
        //获取认证对象的user信息
        Authentication userAuthenticationtemp = oAuth2Authentication.getUserAuthentication();
        //获取认证请求
        OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
        if(userAuthenticationtemp != null){
            String userName = userAuthenticationtemp.getName();
            additionalInfo.put("username",userName);
            if(oAuth2Request != null){
                additionalInfo.put("authmode",oAuth2Request.getGrantType());
                additionalInfo.put("clientid",oAuth2Request.getClientId());
            }else{
                additionalInfo.put("authmode","zf");
                additionalInfo.put("clientid","zf");
            }
        }else{
            if(oAuth2Request != null){
                additionalInfo.put("authmode",oAuth2Request.getGrantType());
                additionalInfo.put("clientid",oAuth2Request.getClientId());
            }else{
                additionalInfo.put("authmode","zf");
                additionalInfo.put("clientid","zf");
            }
        }
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        return oAuth2AccessToken;
    }
}
