package com.example.EasyTravel.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.EasyTravel.service.ifs.MaintenanceService;
import com.example.EasyTravel.vo.MaintenanceResponse;

@RestController
public class MaintenanceController {
	
	@Autowired
	public MaintenanceService maintenanceService;

	@PostMapping(value = "Add_abnormal") //
	public MaintenanceResponse AddAbnormal(@RequestBody String licensePlate, LocalDateTime startTime) {
		return maintenanceService.AddAbnormal(licensePlate, startTime);
	}

	@PostMapping(value = "finish_abnormal") //
	public MaintenanceResponse finishAbnormal(@RequestBody String licensePlate, LocalDateTime startTime,
			LocalDateTime endTime, int price, String note) {
		return maintenanceService.finishAbnormal(licensePlate, startTime, endTime, price, note);
	}

	@PostMapping(value = "delete_abnormal") //
	public MaintenanceResponse deleteAbnormal(@RequestBody String licensePlate, LocalDateTime startTime) {
		return maintenanceService.deleteAbnormal(licensePlate, startTime);
	}

	@PostMapping(value = "searchBy_abnormal") //
	public MaintenanceResponse searchByAbnormal(@RequestBody String licensePlate) {
		return maintenanceService.searchByAbnormal(licensePlate);
	}

	@PostMapping(value = "search_by_start_time_and_end_time") //
	public MaintenanceResponse searchByStartTimeAndEndTime(@RequestBody LocalDateTime startTime,
			LocalDateTime endTime) {
		return maintenanceService.searchByStartTimeAndEndTime(startTime, endTime);
	}
}
