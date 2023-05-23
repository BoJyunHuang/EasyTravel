package com.example.EasyTravel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StopRequest {

	private String city;
	private String location;
	@JsonProperty("bike_limit")
	private int bikeLimit;
	@JsonProperty("motorcycle_limit")
	private int motorcycleLimit;
	@JsonProperty("car_limit")
	private int carLimit;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getBikeLimit() {
		return bikeLimit;
	}

	public void setBikeLimit(int bikeLimit) {
		this.bikeLimit = bikeLimit;
	}

	public int getMotorcycleLimit() {
		return motorcycleLimit;
	}

	public void setMotorcycleLimit(int motorcycleLimit) {
		this.motorcycleLimit = motorcycleLimit;
	}

	public int getCarLimit() {
		return carLimit;
	}

	public void setCarLimit(int carLimit) {
		this.carLimit = carLimit;
	}

}
