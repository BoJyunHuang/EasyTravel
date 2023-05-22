package com.example.EasyTravel.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


	@Entity
	@Table(name = "maintenance")
	public class Maintenance {

		@Id // Key
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "serial_number")
		private Integer serialNumber;
		@Column(name = "license_plate")
		private String licensePlate;
		@Column(name = "price")
		private int price;
		@Column(name = "start_time") 
		private LocalDateTime startTime;
		@Column(name = "end_time")
		private LocalDateTime endTime;
		@Column(name = "note")
		private String note;
		
		
		
		public Maintenance() {
			super();
			// TODO Auto-generated constructor stub
		}
		public void setSerialNumber(Integer serialNumber) {
			this.serialNumber = serialNumber;
		}
		public Integer getSerialNumber() {
			return serialNumber;
		}
		
		public String getLicensePlate() {
			return licensePlate;
		}
		public void setLicensePlate(String licensePlate) {
			this.licensePlate = licensePlate;
		}
		public int getPrice() {
			return price;
		}
		public void setPrice(int price) {
			this.price = price;
		}
		public LocalDateTime getStartTime() {
			return startTime;
		}
		public void setStartTime(LocalDateTime startTime) {
			this.startTime = startTime;
		}
		public LocalDateTime getEndTime() {
			return endTime;
		}
		public void setEndTime(LocalDateTime endTime) {
			this.endTime = endTime;
		}
		public String getNote() {
			return note;
		}
		public void setNote(String note) {
			this.note = note;
		}
		public Maintenance(String licensePlate, LocalDateTime startTime) {
			super();
			this.licensePlate = licensePlate;
			this.startTime = startTime;
		}
		
		
		
	}
		
