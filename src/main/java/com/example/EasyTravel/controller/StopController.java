package com.example.EasyTravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.EasyTravel.service.ifs.StopService;
import com.example.EasyTravel.vo.StopRequest;
import com.example.EasyTravel.vo.StopResponse;

@CrossOrigin
@RestController
public class StopController {

	@Autowired
	private StopService stopService;

	@PostMapping(value = "add_stop")
	public StopResponse addStop(@RequestBody StopRequest request) {
		return stopService.addStop(request.getCity(), request.getLocation(), request.getBikeAmount(),
				request.getMotorcycleAmount(), request.getCarAmount());
	}

	@GetMapping(value = "find_city_stops")
	public StopResponse findCityStops(@RequestBody StopRequest request) {
		return stopService.findCityStops(request.getCity());
	}

	@GetMapping(value = "renew_limit")
	public StopResponse renewLimit(@RequestBody StopRequest request) {
		return stopService.renewLimit(request.getCity(), request.getLocation(), request.getBikeAmount(),
				request.getMotorcycleAmount(), request.getCarAmount());
	}

	@GetMapping(value = "rent_or_return")
	public StopResponse rentOrReturn(@RequestBody StopRequest request) {
		return stopService.rentOrReturn(request.isRent(), request.getCategory(), request.getCity(),
				request.getLocation());
	}

	@GetMapping(value = "dispatch")
	public StopResponse dispatch(@RequestBody StopRequest request) {
		return stopService.dispatch(request.getVehicleList(), request.getCity(), request.getLocation());
	}

}
