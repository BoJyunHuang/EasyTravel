package com.example.EasyTravel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fee")
public class Fee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "serial_number")
	private int serialNumber;
	
	@Column(name = "project")
	private String project;
	
	@Column(name = "cc")
	private int cc;
	
	@Column(name = "rate")
	private double rate;
	
	@Column(name = "interval")
	private int interval;

	public Fee() {
		super();
	}

	public Fee(String project, int cc, double rate, int interval) {
		super();
		this.project = project;
		this.cc = cc;
		this.rate = rate;
		this.interval = interval;
	}

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

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

}
