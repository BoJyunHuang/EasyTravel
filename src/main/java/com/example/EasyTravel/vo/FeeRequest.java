package com.example.EasyTravel.vo;

import java.time.Duration;

import com.example.EasyTravel.entity.Vehicle;

public class FeeRequest {

	private String project;
	private int cc;
	private double rate;
	private int threshold;
	private int total;
	private Vehicle vehicle;
	private boolean isVip;
	private Duration period;
	
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public int getCc() {
		return cc;
	}
	public void setCc(int cc) {
		this.cc = cc;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public int getThreshold() {
		return threshold;
	}
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public boolean isVip() {
		return isVip;
	}
	public void setVip(boolean isVip) {
		this.isVip = isVip;
	}
	public Duration getPeriod() {
		return period;
	}
	public void setPeriod(Duration period) {
		this.period = period;
	}
	
}
