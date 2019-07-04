package com.kakaopay.auth.model;

import java.io.Serializable;

public class TblUserVO implements Serializable  {

	private static final long serialVersionUID = 1L;
	private String usrid;
	private String passwd;
	private String salt;
	
	public TblUserVO() {}
	
	public TblUserVO(String usrid, String passwd, String salt) {
		this.usrid= usrid;
		this.passwd= passwd;
		this.salt= salt;		
	}	
	
	public String getUsrid() {
		return usrid;
	}
	public void setUsrid(String usrid) {
		this.usrid = usrid;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	

}
