package com.zf.study.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Slf4j
@Component
public class TokenAuthenticationFilter extends ZuulFilter {

    private static final String WEB_URL = "api/web/zf";

    private static final String SOA_URL = "api/soa/zf";


    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        String uri = currentContext.getRequest().getRequestURI();
        log.debug("uri", uri);
        //需要执行的拦截请求，返回true时候执行run方法
        if (uri.contains(WEB_URL) || uri.contains(SOA_URL)) {
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String authorization = request.getHeader("Authorization");
        if(StringUtils.isBlank(authorization)){
            //false:对请求不路由；true：对请求路由
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(504);
            requestContext.setResponseBody("ivalible request");
            return null;
        }
        return null;
    }


    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 5;
    }

}
