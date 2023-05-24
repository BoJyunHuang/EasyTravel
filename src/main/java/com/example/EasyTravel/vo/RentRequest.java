package com.example.EasyTravel.vo;

import java.time.LocalDateTime;

public class RentRequest {

	private String account;
	private String licensePlate;
	private LocalDateTime nowTime;
	private int price;

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

	public LocalDateTime getNowTime() {
		return nowTime;
	}

	public void setNowTime(LocalDateTime nowTime) {
		this.nowTime = nowTime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
}
