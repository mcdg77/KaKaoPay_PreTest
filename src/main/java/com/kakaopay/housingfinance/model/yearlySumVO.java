package com.kakaopay.housingfinance.model;

import java.io.Serializable;

public class yearlySumVO implements Serializable  {

	private static final long serialVersionUID = 1L;

	private String year;
	private int total_amount;
	private String detail_amount;
	
	public yearlySumVO(){}
	
	
	public yearlySumVO(String year, int total_amount, String detail_amount ){
		this.year = year;
		this.total_amount = total_amount;
		this.detail_amount = detail_amount;		
	}

	
	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public int getTotal_amount() {
		return total_amount;
	}


	public void setTotal_amount(int total_amount) {
		this.total_amount = total_amount;
	}


	public String getDetail_amount() {
		return detail_amount;
	}


	public void setDetail_amount(String detail_amount) {
		this.detail_amount = detail_amount;
	}







}
