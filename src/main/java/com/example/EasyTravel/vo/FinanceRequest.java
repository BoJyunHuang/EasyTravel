package com.example.EasyTravel.vo;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FinanceRequest {

	private String title;
	private String detail;
	private int price;
	@JsonProperty("build_date")
	private LocalDate buildDate;
	private int month;
	@JsonProperty("compared_month")
	private int comparedMonth;

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

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getComparedMonth() {
		return comparedMonth;
	}

	public void setComparedMonth(int comparedMonth) {
		this.comparedMonth = comparedMonth;
	}

}
