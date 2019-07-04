package com.kakaopay.auth.service.jwt;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakaopay.auth.model.TblJwtAuthVO;
import com.kakaopay.repository.MemberRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service("jwtService")
public class JwtServiceImpl implements JwtService {
	
	private static final String SECRET ="kakaoPay";

	@Autowired
	private MemberRepository memberRepository;

	@Override
	public boolean isUsable(String jwt) {		
		try{			
			/* DB 유효한 토큰인지 확인 해야함 */	
			//System.out.println("Auth jwt:[" + jwt + "]");	
			
			List<TblJwtAuthVO> list = memberRepository.selectTblJwtAuthByJtw(jwt);
			if(list.size()>0) {
				Jws<Claims> claims = Jwts.parser()
						  .setSigningKey(SECRET.getBytes("UTF-8"))
						  .parseClaimsJws(jwt);
				System.out.println("claims:[" +  claims + "]");
				return true;
			}else return false;
		}catch (Exception e) {
			 return false;			 
		}
	}

	@Override
	public String jtwMake(String usrid) throws UnsupportedEncodingException {
		String jwt = Jwts.builder()
				.setHeaderParam("typ","JWT")
				.setIssuer("stirmpath")
				.setSubject("KaKaoPay PreTest")
				.claim("UserId", usrid)
				.setIssuedAt(Date.from(Instant.now()))
				//.setExpiration(Date.from(Instant.now().plus(2, ChronoUnit.HOURS)))  //만료시간 
				.signWith(SignatureAlgorithm.HS256, SECRET.getBytes("UTF-8"))
				.compact();							
		return jwt;
	}

}
