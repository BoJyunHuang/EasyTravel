package com.example.EasyTravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.EasyTravel.service.ifs.RentService;
import com.example.EasyTravel.vo.RentRequest;
import com.example.EasyTravel.vo.RentResponse;

@RestController
public class RentController {

	@Autowired
	private RentService rentService;

	@PostMapping(value = "rent")
	public RentResponse rent(@RequestBody RentRequest request) {
		return rentService.rent(request.getAccount(), request.getLicensePlate());
	}

	@PostMapping(value = "drop_off")
	public RentResponse dropOff(@RequestBody RentRequest request) {
		return rentService.dropOff(request.getAccount(), request.getLicensePlate(), request.getCity(),
				request.getLocation(), request.getOdo());
	}

	@GetMapping(value = "show_details")
	public RentResponse showDetails(@RequestBody RentRequest request) {
		return rentService.showDetails(request.getAccount());
	}

	@GetMapping(value = "monly_rental_count")
	public RentResponse monlyRentalCount(@RequestBody RentRequest request) {
		return rentService.monlyRentalCount(request.getMonth());
	}

}
