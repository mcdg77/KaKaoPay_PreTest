package com.kakaopay.auth.service.jwt;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;

@Service
public interface JwtService {
	
	boolean isUsable(String jwt);
	
	String jtwMake(String usrid) throws UnsupportedEncodingException;
}
