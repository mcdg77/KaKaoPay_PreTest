package com.kakaopay.common.model;

public class YearlySumResult {

	private String year;
	private int total_amount;
	private Object detail_amount;
	
	public YearlySumResult(){}
	
	public YearlySumResult(String year, int total_amount, Object detail_amount ) {
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
	public Object getDetail_amount() {
		return detail_amount;
	}
	public void setDetail_amount(Object detail_amount) {
		this.detail_amount = detail_amount;
	}
	
	
}
