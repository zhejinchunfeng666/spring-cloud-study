package com.zf.study.mem.security.handler;

import com.zf.study.mem.security.TokenUserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component("myAuthSuccessHander")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		response.setContentType("application/json;charset=utf-8");
		 Cookie cookie = new Cookie("user", ((TokenUserInfo)authentication.getPrincipal()).getUsername());
         cookie.setPath("/");
         cookie.setMaxAge(60 * 60 * 24);
         response.addCookie(cookie);
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		sb.append("{\"status\":\"true\",\"msg\":\"登录成功\"}");
		out.write(sb.toString());
		out.flush();
		out.close();
	}

}
