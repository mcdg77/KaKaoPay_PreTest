package com.kakaopay.housingfinance.model;

import java.io.Serializable;

public class maxInsVO implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	public maxInsVO() {}
	
	private int year;
	private String bank;

	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	
	

}
