package com.kakaopay.common.model;

public class minMaxResult {
	private String bank;
	private Object support_amount;
	
	public minMaxResult(){}
	
	public minMaxResult(String bank, Object support_amount) {
		this.bank= bank;
		this.support_amount= support_amount;
	}	

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public Object getSupport_amount() {
		return support_amount;
	}

	public void setSupport_amount(Object support_amount) {
		this.support_amount = support_amount;
	}

}
