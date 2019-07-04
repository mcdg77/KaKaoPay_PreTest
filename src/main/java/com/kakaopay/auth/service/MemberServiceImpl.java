package com.kakaopay.auth.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakaopay.auth.model.UserInfo;
import com.kakaopay.auth.model.TblJwtAuthVO;
import com.kakaopay.auth.model.TblUserVO;
import com.kakaopay.auth.service.jwt.JwtService;
import com.kakaopay.common.util.SHA256Util;
import com.kakaopay.repository.MemberRepository;


@Service("memberService")
public class MemberServiceImpl implements MemberService {

	public boolean isEqualPasswd;
	
	@Autowired
	private JwtService jwtService; 
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public String signup(UserInfo usrinfo) {			

		String token;
		
		try {
			String[] encdata = SHA256Util.getEncryptSHA256(usrinfo.getPasswd());			
			TblUserVO record = new TblUserVO(usrinfo.getUserid(), encdata[0], encdata[1]);			
			
			memberRepository.insertTblUser(record);						
			token = jwtService.jtwMake(usrinfo.getUserid());
			
			TblJwtAuthVO record2 = new TblJwtAuthVO(usrinfo.getUserid(),"","",token);			
			memberRepository.insertTblJwtAuth(record2);

		} catch (UnsupportedEncodingException e) {			
			token="";
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			token="";
		}

		return token;
	}

	@Override
	public String signin(UserInfo usrinfo) {
		String token=null;
		
		try {
			TblUserVO record = new TblUserVO(usrinfo.getUserid(), "", "");		
			List<TblUserVO> list =  memberRepository.selectTblUser(record);						
			TblUserVO result = list.get(0);
						
			if(SHA256Util.authenticate(usrinfo.getPasswd(), result.getPasswd(), result.getSalt())){				
				List<TblJwtAuthVO> list2 = memberRepository.selectTblJwtAuth(usrinfo.getUserid());
				if(list2.size()>0) {
					TblJwtAuthVO result2 = list2.get(0);
					token = result2.getJwtval();
				}
			}
		} catch (Exception e) {						
			e.printStackTrace();
			token="";
		}
		return token;
	}

	@Override
	public String reFresh(String oldtoken) {
		
		String token=null;
		oldtoken =  oldtoken.replace("Bearer ", "");
		try {
			if(jwtService.isUsable(oldtoken)) {
	
				List<TblJwtAuthVO> list = memberRepository.selectTblJwtAuthByJtw(oldtoken);
				if(list.size()>0) {
					TblJwtAuthVO record =   list.get(0);					
					token = jwtService.jtwMake(record.getUsrid());					
					record.setJwtval(token);
					
					memberRepository.updateTblJwtAuth(record);			
					memberRepository.insertTblJwtAuth(record);
					return token;
				}
			}		
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
		return token;
	}
	
}
