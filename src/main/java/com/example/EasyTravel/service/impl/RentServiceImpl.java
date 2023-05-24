package com.example.EasyTravel.service.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.EasyTravel.service.ifs.RentService;
import com.example.EasyTravel.vo.RentResponse;

@Service
public class RentServiceImpl implements RentService{

	@Override
	public RentResponse rent(String account, String licensePlate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RentResponse dropOff(String account, String licensePlate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RentResponse showDetails(String account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RentResponse MonlyRentalCount(LocalDate month) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RentResponse DailyRevenueImport(LocalDate month) {
		// TODO Auto-generated method stub
		return null;
	}

}
