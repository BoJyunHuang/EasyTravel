package com.example.EasyTravel.vo;

import java.util.List;

import com.example.EasyTravel.entity.Stop;

public class StopResponse {

	private String message;
	private Stop stop;
	private List<Stop> stopList;

	public StopResponse() {
		super();
	}

	public StopResponse(String message) {
		super();
		this.message = message;
	}

	public StopResponse(Stop stop, String message) {
		super();
		this.stop = stop;
		this.message = message;
	}

	public StopResponse(List<Stop> stopList, String message) {
		super();
		this.stopList = stopList;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Stop getStop() {
		return stop;
	}

	public void setStop(Stop stop) {
		this.stop = stop;
	}

	public List<Stop> getStopList() {
		return stopList;
	}

	public void setStopList(List<Stop> stopList) {
		this.stopList = stopList;
	}

}
