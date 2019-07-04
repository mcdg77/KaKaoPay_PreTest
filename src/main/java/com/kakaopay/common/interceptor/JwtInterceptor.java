package com.kakaopay.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kakaopay.auth.service.jwt.JwtService;
import com.kakaopay.common.error.UnauthorizedException;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {
	
	private static final String HEADER_AUTH = "Authorization";
	
	@Autowired
	private JwtService jswService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler )
	  throws Exception{
		
		System.out.println("JWT Interceptor Proc...");		
		String token="";		
		if (request.getHeader(HEADER_AUTH) !=null ) token = request.getHeader(HEADER_AUTH);
		/* 이값을 지우고 해야 되더라 */
		token =  token.replace("Bearer ", "");
		if(token != null && jswService.isUsable(token)){
			return true;
		}else{
			throw new UnauthorizedException();			
		}		
	}

}
