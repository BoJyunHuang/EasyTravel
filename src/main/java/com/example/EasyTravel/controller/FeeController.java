package com.example.EasyTravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.EasyTravel.service.ifs.FeeService;
import com.example.EasyTravel.vo.FeeRequest;
import com.example.EasyTravel.vo.FeeResponse;

@RestController
public class FeeController {

	@Autowired
	private FeeService feeService;

	@PostMapping(value = "add_case")
	public FeeResponse addProject(@RequestBody FeeRequest request) {
		return feeService.addProject(request.getProject(), request.getCc(), request.getRate(), request.getInterval());
	}

	@GetMapping(value = "revise_case")
	public FeeResponse reviseProject(@RequestBody FeeRequest request) {
		return feeService.reviseProject(request.getProject(), request.getCc(), request.getRate(),
				request.getInterval());
	}

	@DeleteMapping(value = "delete_case")
	public FeeResponse deleteProject(@RequestBody FeeRequest request) {
		return feeService.deleteProject(request.getProject(), request.getCc(), request.getRate());
	}

	@GetMapping(value = "find_case")
	public FeeResponse findProject(@RequestBody FeeRequest request) {
		return feeService.findProject(request.getProject());
	}

	@GetMapping(value = "calculate")
	public FeeResponse calculate(@RequestBody FeeRequest request) {
		return feeService.calculate(request.getProject(), request.getInterval());
	}
}
