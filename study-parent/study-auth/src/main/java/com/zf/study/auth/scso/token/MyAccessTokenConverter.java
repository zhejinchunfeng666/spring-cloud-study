package com.zf.study.auth.scso.token;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.LinkedHashMap;
import java.util.Map;

public class MyAccessTokenConverter extends DefaultAccessTokenConverter {
    public MyAccessTokenConverter() {
        super.setUserTokenConverter(new MyUserAuthenticationConverter());
    }

    private class MyUserAuthenticationConverter extends DefaultUserAuthenticationConverter {
        @Override
        @SuppressWarnings({ "rawtypes", "unchecked" })
        public Map<String, ?> convertUserAuthentication(Authentication authentication) {
            LinkedHashMap response = new LinkedHashMap();
            response.put("user_name", authentication.getName());
            if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
                response.put("authorities", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
            }
            return response;
        }
    }
}
