package com.example.EasyTravel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "stop")
@IdClass(value = StopId.class)
public class Stop {
	
	@Id
	@Column(name = "city")
	private String city;
	
	@Id
	@Column(name = "location")
	private String location;
	
	@Column(name = "bike_limit")
	private int bikeLimit;
	
	@Column(name = "motorcycle_limit")
	private int motorcycleLimit;
	
	@Column(name = "car_limit")
	private int carLimit;

	public Stop() {
		super();
	}

	public Stop(String city, String location, int bikeLimit, int motorcycleLimit, int carLimit) {
		super();
		this.city = city;
		this.location = location;
		this.bikeLimit = bikeLimit;
		this.motorcycleLimit = motorcycleLimit;
		this.carLimit = carLimit;
	}

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
