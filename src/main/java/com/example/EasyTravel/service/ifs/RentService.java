package com.example.EasyTravel.service.ifs;

import java.time.LocalDate;

import com.example.EasyTravel.vo.RentResponse;

public interface RentService {

	// 租借車
	public RentResponse rent(String account, String licensePlate);
	
	// 租還車
	public RentResponse dropOff(String account, String licensePlate);
	
	// 查詢租借明細(帳號)
	public RentResponse showDetails(String account);
	
	// 計算月租人次(腳踏車、機車、汽車)
	public RentResponse MonlyRentalCount(LocalDate month);
	
	// 日營收匯入財務系統
	public RentResponse DailyRevenueImport(LocalDate month);
}
