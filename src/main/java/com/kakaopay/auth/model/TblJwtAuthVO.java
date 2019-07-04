package com.kakaopay.auth.model;

import java.io.Serializable;

public class TblJwtAuthVO implements Serializable  {

	private static final long serialVersionUID = 1L;
	private String usrid;
	private String strtdt;
	private String enddt;
	private String jwtval;
	
	public TblJwtAuthVO() {}
		
	public TblJwtAuthVO(String usrid, String strtdt, String enddt, String jwtval) {
		this.usrid = usrid;
		this.strtdt = strtdt;
		this.enddt = enddt;
		this.jwtval = jwtval;			
	}	
	
	public String getUsrid() {
		return usrid;
	}
	public void setUsrid(String usrid) {
		this.usrid = usrid;
	}
	public String getStrtdt() {
		return strtdt;
	}
	public void setStrtdt(String strtdt) {
		this.strtdt = strtdt;
	}
	public String getEnddt() {
		return enddt;
	}
	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}
	public String getJwtval() {
		return jwtval;
	}
	public void setJwtval(String jwtval) {
		this.jwtval = jwtval;
	}
	
}
