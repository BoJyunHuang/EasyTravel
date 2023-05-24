package com.example.EasyTravel.vo;

import java.util.List;
import java.util.Map;

import com.example.EasyTravel.entity.Rent;

public class RentResponse {

	private String message;
	private List<Rent> rentList;
	private Map<String, Integer> statics;

	public RentResponse() {
		super();
	}

	public RentResponse(String message) {
		super();
		this.message = message;
	}

	public RentResponse(List<Rent> rentList, String message) {
		super();
		this.rentList = rentList;
		this.message = message;
	}

	public RentResponse(Map<String, Integer> statics, String message) {
		super();
		this.statics = statics;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Rent> getRentList() {
		return rentList;
	}

	public void setRentList(List<Rent> rentList) {
		this.rentList = rentList;
	}

	public Map<String, Integer> getStatics() {
		return statics;
	}

	public void setStatics(Map<String, Integer> statics) {
		this.statics = statics;
	}

}
