package com.kakaopay.housingfinance.model;

import java.io.Serializable;

public class instituteVO implements Serializable  {

	private static final long serialVersionUID = 1L;
	private String institute_name;
	private String institute_code;
	
	public instituteVO(){		
	}
	
	public instituteVO(String institute_name, String institute_code) {
		this.institute_code= institute_code;
		this.institute_name= institute_name;
	}

	public String getIntitute_name() {
		return institute_name;
	}

	public void setIntitute_name(String institute_name) {
		this.institute_name = institute_name;
	}

	public String getIntitute_code() {
		return institute_code;
	}

	public void setIntitute_code(String institute_code) {
		this.institute_code = institute_code;
	}

}
