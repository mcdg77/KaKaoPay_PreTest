package com.kakaopay.common.model;

public class Result {
	private String name;
	private Object result;
	
	public Result() {}
	
	public Result(String name, Object result) {
		this.name = name;
		this.result = result;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	

}
