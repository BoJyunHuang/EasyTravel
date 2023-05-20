package com.example.EasyTravel.service.ifs;

import java.time.LocalDate;

import com.example.EasyTravel.vo.FinanceResponse;

public interface FinanceService {

	// 建立明細
	public FinanceResponse addReport(String title, String detail, int price, LocalDate buildDate);
	
	// 查詢同title的資訊
	public FinanceResponse findTitle(String title, int month);
	
	// 查詢同detail的資訊
	public FinanceResponse findDetail(String detail, int month);
	
	// 月收益支出統計
	public FinanceResponse monthlyReport(int month);
	
	// 月支出收入比例
	public FinanceResponse monthlyRatio(int month);
	
}
