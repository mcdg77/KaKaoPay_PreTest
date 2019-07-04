package com.kakaopay.housingfinance.model;

public class TblFund {
	private String hfyear;
	private String hfmonth;
	private String institutecode;
	private int hfamount;
	
	private String year;
	private int total_amount;
	private String detail_amount;


	private String hf_year;

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



	public TblFund() {}
	
	public TblFund(String hfyear, String hfmonth, String institutecode, int hfamount) {
		this.hfyear= hfyear;
		this.hfmonth = hfmonth;
		this.institutecode = institutecode;
		this.setHfamount(hfamount);		
	}
	
	
	public String getHfyear() {
		return hfyear;
	}
	public void setHfyear(String hfyear) {
		this.hfyear = hfyear;
	}
	public String getHfmonth() {
		return hfmonth;
	}
	public void setHfmonth(String hfmonth) {
		this.hfmonth = hfmonth;
	}
	public String getInstitutecode() {
		return institutecode;
	}
	public void setInstitutecode(String institutecode) {
		this.institutecode = institutecode;
	}
	public int getHfamount() {
		return hfamount;
	}

	public void setHfamount(int hfamount) {
		this.hfamount = hfamount;
	}

	public String getHf_year() {
		return hf_year;
	}

	public void setHf_year(String hf_year) {
		this.hf_year = hf_year;
	}

	public String getDetail_amount() {
		return detail_amount;
	}

	public void setDetail_amount(String detail_amount) {
		this.detail_amount = detail_amount;
	}

	
}
