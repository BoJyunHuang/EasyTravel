package com.example.EasyTravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.EasyTravel.service.ifs.FinanceService;
import com.example.EasyTravel.vo.FinanceRequest;
import com.example.EasyTravel.vo.FinanceResponse;

@RestController
public class FinanceController {

	@Autowired
	private FinanceService financeService;

	@PostMapping(value = "add_report")
	public FinanceResponse addReport(@RequestBody FinanceRequest request) {
		return financeService.addReport(request.getTitle(), request.getDetail(), request.getPrice(),
				request.getBuildDate());
	}

	@GetMapping(value = "find_title")
	public FinanceResponse findTitle(@RequestBody FinanceRequest request) {
		return financeService.findTitle(request.getTitle(), request.getMonth());
	}

	@GetMapping(value = "find_detail")
	public FinanceResponse findDetail(@RequestBody FinanceRequest request) {
		return financeService.findDetail(request.getDetail(), request.getMonth());
	}

	@GetMapping(value = "monthly_report")
	public FinanceResponse monthlyReport(@RequestBody FinanceRequest request) {
		return financeService.monthlyReport(request.getMonth());
	}

	@GetMapping(value = "monthly_ratio")
	public FinanceResponse monthlyRatio(@RequestBody FinanceRequest request) {
		return financeService.monthlyRatio(request.getMonth());
	}

}
