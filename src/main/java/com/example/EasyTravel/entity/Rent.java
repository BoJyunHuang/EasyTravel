package com.example.EasyTravel.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rent")
public class Rent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "serial_number")
	private Integer serialNumber;

	@Column(name = "account")
	private String account;

	@Column(name = "license_plate")
	private String licensePlate;

	@Column(name = "city")
	private String city;

	@Column(name = "location")
	private String location;

	@Column(name = "now_time")
	private LocalDateTime nowTime = LocalDateTime.now();

	@Column(name = "rent")
	private boolean isRent = true;

	@Column(name = "price")
	private int price;

	public Rent() {
		super();
	}

	public Rent(String account, String licensePlate, String city, String location) {
		super();
		this.account = account;
		this.licensePlate = licensePlate;
		this.city = city;
		this.location = location;
	}

	public Rent(String account, String licensePlate, String city, String location, boolean isRent, int price) {
		super();
		this.account = account;
		this.licensePlate = licensePlate;
		this.city = city;
		this.location = location;
		this.isRent = isRent;
		this.price = price;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
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

	public LocalDateTime getNowTime() {
		return nowTime;
	}

	public void setNowTime(LocalDateTime nowTime) {
		this.nowTime = nowTime;
	}

	public boolean isRent() {
		return isRent;
	}

	public void setRent(boolean isRent) {
		this.isRent = isRent;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
