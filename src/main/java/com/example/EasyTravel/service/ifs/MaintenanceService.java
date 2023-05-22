package com.example.EasyTravel.service.ifs;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.EasyTravel.vo.MaintenanceResponse;


public interface MaintenanceService {

	// 新增單號
	public MaintenanceResponse AddAbnormal(String licensePlate ,LocalDateTime startTime );

	// 完成單號
	public MaintenanceResponse finishAbnormal(String licensePlate ,LocalDateTime startTime,LocalDateTime endTime,int price,String note );

	// 註銷單號
	public MaintenanceResponse deleteAbnormal(String licensePlate,LocalDateTime startTime );

	// 查詢單號
	public MaintenanceResponse searchByAbnormal(String licensePlate);

	// 藉由保養開始日到結束日查詢所有單號
	public MaintenanceResponse searchByStartTimeAndEndTime(LocalDateTime startTime,LocalDateTime endTime);

	// 月支出統計
	public MaintenanceResponse MonthlyTotalExpense();

}
