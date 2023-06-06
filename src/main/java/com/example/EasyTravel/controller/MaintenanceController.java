package com.example.EasyTravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.EasyTravel.service.ifs.MaintenanceService;
import com.example.EasyTravel.vo.MaintenanceRequest;
import com.example.EasyTravel.vo.MaintenanceResponse;

@RestController
public class MaintenanceController {

	@Autowired
	public MaintenanceService maintenanceService;

	@PostMapping(value = "Add_abnormal") 
	public MaintenanceResponse AddAbnormal(@RequestBody MaintenanceRequest request) {
		return maintenanceService.AddAbnormal(request.getLicensePlate());
	}

	@PostMapping(value = "finish_abnormal") 
	public MaintenanceResponse finishAbnormal(@RequestBody MaintenanceRequest request) {
		return maintenanceService.finishAbnormal(request.getLicensePlate(), request.getPrice(), request.getNote());
	}

	@PostMapping(value = "delete_abnormal") 
	public MaintenanceResponse deleteAbnormal(@RequestBody MaintenanceRequest request) {
		return maintenanceService.deleteAbnormal(request.getLicensePlate(), request.getStartTime());
	}

	@PostMapping(value = "searchBy_abnormal") 
	public MaintenanceResponse searchByAbnormal(@RequestBody MaintenanceRequest request) {
		return maintenanceService.searchByAbnormal(request.getLicensePlate());
	}

	@PostMapping(value = "search_by_start_time_and_end_time") 
	public MaintenanceResponse searchByStartTimeAndEndTime(@RequestBody MaintenanceRequest request) {
		return maintenanceService.searchByStartTimeAndEndTime(request.getStartTime(), request.getEndTime());
	}
}
