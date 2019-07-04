package com.kakaopay.auth.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.auth.model.UserInfo;
import com.kakaopay.auth.service.MemberService;

@RestController
@RequestMapping("/member")
public class MemberController {
	
	private static final String HEADER_AUTH = "Authorization";
	
	@Autowired
	private MemberService memberService;	
	
	@PostMapping(value="/signup")
	public ResponseEntity<HashMap<String,String>> signup(@RequestBody  UserInfo usrinfo ) {
		System.out.println( "PostMapping URL -[member/signup] Userid:["+ usrinfo.getUserid() +"]" );		
		String token = memberService.signup(usrinfo);				
		if (token.equals(null) || token.equals("")) {			
			return new ResponseEntity<HashMap<String,String>>(CommonRes("",false), HttpStatus.EXPECTATION_FAILED);
		}else {		
			return new ResponseEntity<HashMap<String,String>>(CommonRes(token,true), HttpStatus.OK);
		}		
	}
	
	@PostMapping(value="/signin")
	public ResponseEntity<HashMap<String,String>> signin(@RequestBody  UserInfo usrinfo ) {
		System.out.println( "PostMapping URL -[member/signin] Userid:["+ usrinfo.getUserid() +"]" );		
		String token = memberService.signin(usrinfo);		
		if (token.equals(null) || token.equals("")) {
			return new ResponseEntity<HashMap<String,String>>(CommonRes("",false), HttpStatus.EXPECTATION_FAILED);
		}else {	
			return new ResponseEntity<HashMap<String,String>>(CommonRes(token,true), HttpStatus.OK);
		}		
	}
	
	@GetMapping(value="/refresh")
	public ResponseEntity<HashMap<String,String>> refresh(HttpServletRequest request) {	
		System.out.println( "PostMapping URL -[member/refresh]" );
		System.out.println("Header Auth:["+ request.getHeader(HEADER_AUTH) + "]");	    
		String oldtoken = request.getHeader(HEADER_AUTH);		
		String token =memberService.reFresh(oldtoken);
		if (token.equals(null) || token.equals("")) {
			return new ResponseEntity<HashMap<String,String>>(CommonRes("",false), HttpStatus.EXPECTATION_FAILED);
		}else {		
			return new ResponseEntity<HashMap<String,String>>(CommonRes(token,true), HttpStatus.OK);
		}
	}
	
	private HashMap<String,String> CommonRes(String token , boolean Succes){
		HashMap<String,String> message = new HashMap<String,String>();		
		System.out.println("Result:[" + token + "]");
		if(Succes) {
			message.put("Message", "Success!!");
			message.put("Result", token);
		}else {
			message.put("Message", "Fail");
			message.put("Result", "");
		}
		return message;
	}
	
}
