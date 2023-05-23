package com.example.EasyTravel.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "finance")
public class Finance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "serial_number")
	private Integer serialNumber;

	@Column(name = "title")
	private String title;

	@Column(name = "detail")
	private String detail;

	@Column(name = "price")
	private int price;

	@Column(name = "build_date")
	private LocalDate buildDate;

	public Finance() {
		super();
	}

	public Finance(String title, String detail, int price, LocalDate buildDate) {
		super();
		this.title = title;
		this.detail = detail;
		this.price = price;
		this.buildDate = buildDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public LocalDate getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(LocalDate buildDate) {
		this.buildDate = buildDate;
	}

}
