package com.kakaopay.auth.service;

import org.springframework.stereotype.Service;

import com.kakaopay.auth.model.UserInfo;

@Service
public interface MemberService {
	
	String signup(UserInfo usrinfo);

	String signin(UserInfo usrinfo);
	
	String reFresh(String token);
}
