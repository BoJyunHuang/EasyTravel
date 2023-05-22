package com.example.EasyTravel.vo;

import java.util.List;

import com.example.EasyTravel.entity.Maintenance;



public class MaintenanceResponse {
	
	private String message;
	private List<Maintenance>maintenanceList;
	private String note;
	
	public MaintenanceResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MaintenanceResponse(String message) {
		super();
		this.message = message;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Maintenance> getMaintenanceList() {
		return maintenanceList;
	}
	public void setMaintenanceList(List<Maintenance> maintenanceList) {
		this.maintenanceList = maintenanceList;
	}
	
	

}
