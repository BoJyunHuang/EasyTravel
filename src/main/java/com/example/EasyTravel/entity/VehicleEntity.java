package com.example.EasyTravel.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle")
public class VehicleEntity {

//	PK���P���X
	@Id
	@Column(name = "license_plate")
	private String licensePlate;
	
//	����
	@Column(name = "category")
	private String category;
	
//	cc
	@Column(name = "cc")
	private int cc;
	
//	�_�l�A�Ф�
	@Column(name = "start_serving_date")
	private LocalDate startServingDate;
	
//	�̷s�ˬd��
	@Column(name = "latest_check_date")
	private LocalDate latestCheckDate;
	
//	�i���ɪ��A
	@Column(name = "available")
	private boolean available;
	
//	�Ҧb����
	@Column(name = "city")
	private String city;
	
//	�Ҧb���I
	@Column(name = "location")
	private String location;
	
//	�`���{
	@Column(name = "odo")
	private double odo;
	
//	����
	@Column(name = "price")
	private int price;

	
//	�غc��k -------------------------
	public VehicleEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VehicleEntity(String licensePlate, String category, int cc, LocalDate startServingDate,
			LocalDate latestCheckDate, boolean available, String city, String location, double odo, int price) {
		super();
		this.licensePlate = licensePlate;
		this.category = category;
		this.cc = cc;
		this.startServingDate = startServingDate;
		this.latestCheckDate = latestCheckDate;
		this.available = available;
		this.city = city;
		this.location = location;
		this.odo = odo;
		this.price = price;
	}

	public VehicleEntity(String licensePlate, String category, int cc, LocalDate startServingDate,
			LocalDate latestCheckDate, boolean available, double odo, int price) {
		this.licensePlate = licensePlate;
		this.category = category;
		this.cc = cc;
		this.startServingDate = startServingDate;
		this.latestCheckDate = latestCheckDate;
		this.available = available;
		this.odo = odo;
		this.price = price;
	}
	
	public void updateVehicleEntity(boolean available, double odo) {
		this.available = available;
		this.odo = odo;
	}
	
	
	
//	get / set -------------------------
	
	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getCc() {
		return cc;
	}

	public void setCc(int cc) {
		this.cc = cc;
	}

	public LocalDate getStartServingDate() {
		return startServingDate;
	}

	public void setStartServingDate(LocalDate startServingDate) {
		this.startServingDate = startServingDate;
	}

	public LocalDate getLatestCheckDate() {
		return latestCheckDate;
	}

	public void setLatestCheckDate(LocalDate latestCheckDate) {
		this.latestCheckDate = latestCheckDate;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
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

	public double getOdo() {
		return odo;
	}

	public void setOdo(double odo) {
		this.odo = odo;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
}
