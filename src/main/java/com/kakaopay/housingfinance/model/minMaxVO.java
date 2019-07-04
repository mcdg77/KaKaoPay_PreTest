package com.kakaopay.housingfinance.model;

import java.io.Serializable;

public class minMaxVO implements Serializable  {

	private static final long serialVersionUID = 1L;
	private int year;
	private int amount;
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
}
