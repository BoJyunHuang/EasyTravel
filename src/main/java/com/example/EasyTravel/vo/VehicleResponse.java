package com.example.EasyTravel.vo;

import java.util.List;

import com.example.EasyTravel.entity.VehicleEntity;

public class VehicleResponse {
	
	private VehicleEntity vehicleEntity;
	
	private List<VehicleEntity> vehicleList;
	
	private String message;

	public VehicleResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VehicleResponse(String message) {
		super();
		this.message = message;
	}

	public VehicleResponse(VehicleEntity vehicleEntity, String message) {
		super();
		this.vehicleEntity = vehicleEntity;
		this.message = message;
	}

	public VehicleResponse(List<VehicleEntity> vehicleList, String message) {
		super();
		this.vehicleList = vehicleList;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public VehicleEntity getVehicleEntity() {
		return vehicleEntity;
	}

	public void setVehicleEntity(VehicleEntity vehicleEntity) {
		this.vehicleEntity = vehicleEntity;
	}

	public List<VehicleEntity> getVehicleList() {
		return vehicleList;
	}

	public void setVehicleList(List<VehicleEntity> vehicleList) {
		this.vehicleList = vehicleList;
	}

	
}
