package com.kakaopay.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class SHA256Util {
	private final static int ITERATION_NUMBER = 1000;

	public static boolean authenticate(String reqPassword, String encPassword, String salt)
	{
		boolean authenticated = false;
		
		try{		
			byte[] bDigest = StrToHex(encPassword);
			byte[] bSalt = StrToHex(salt);						
			byte[] proposeDigest = getHash(ITERATION_NUMBER, reqPassword,bSalt );
			authenticated = Arrays.equals(proposeDigest, bDigest);				
			
			System.out.println("encPassword :[" + byteToString(bDigest) +"]");
			System.out.println("reqPassword :[" + byteToString(proposeDigest) +"]");						
			
		} catch(Exception ex){
			 ex.toString();
		}		
		 return authenticated;		 
	}
	
	public static String[] getEncryptSHA256(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		
		if(password == null || "".endsWith(password)){
			return null;
		}
		
		String[] result = new String[2];
		
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		// Salt generation 64 bits long
		byte[] bSalt =  new byte[8];
		random.nextBytes(bSalt);
		byte[] bDigest = getHash(ITERATION_NUMBER, password, bSalt);
		String sDigest = byteToString(bDigest);
		String sSalt = byteToString(bSalt);
		                         
		result[0] = sDigest;
		result[1] = sSalt; 

		return result;
	}


	public static byte[] getHash(int iterationNb, String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		digest.update(salt);
		byte[] input = digest.digest(password.getBytes("UTF-8"));
		for(int i=0; i< iterationNb; i++){
			digest.reset();
			input = digest.digest(input);
		}
		return input;
	}


	private static byte[] StrToHex(String pdata ){		
		 int len = pdata.length();
	     byte[] cStr = new byte[len/2]; 	
	     
	     for( int i = 0; i < len; i+=2 ) {
	          cStr[i/2] = (byte)Integer.parseInt( pdata.substring( i, i+2 ), 16 );
	       }	
	     
		return cStr;
	}


	private static String byteToString(byte[] hash){
		
		StringBuffer hexString = new StringBuffer();
		
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) hexString.append('0');
	        hexString.append(hex);
	    }

	    return hexString.toString();
	}

}
