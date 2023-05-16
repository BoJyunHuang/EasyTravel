package com.example.EasyTravel.vo;

import java.util.List;

import com.example.EasyTravel.entity.Fee;

public class FeeResponse {

	private String message;
	private Fee fee;
	private List<Fee> feeList;
	private int total;

	public FeeResponse() {
		super();
	}

	public FeeResponse(String message) {
		super();
		this.message = message;
	}

	public FeeResponse(Fee fee, String message) {
		super();
		this.fee = fee;
		this.message = message;
	}

	public FeeResponse(List<Fee> feeList, String message) {
		super();
		this.feeList = feeList;
		this.message = message;
	}

	public FeeResponse(int total, String message) {
		super();
		this.total = total;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Fee getFee() {
		return fee;
	}

	public void setFee(Fee fee) {
		this.fee = fee;
	}

	public List<Fee> getFeeList() {
		return feeList;
	}

	public void setFeeList(List<Fee> feeList) {
		this.feeList = feeList;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
