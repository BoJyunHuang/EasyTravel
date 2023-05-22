package com.example.EasyTravel.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.EasyTravel.constants.Abnormal;
import com.example.EasyTravel.constants.RtnCode;
import com.example.EasyTravel.entity.Maintenance;
import com.example.EasyTravel.repository.MaintenanceDao;
import com.example.EasyTravel.service.ifs.MaintenanceService;
import com.example.EasyTravel.vo.MaintenanceResponse;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

	@Autowired
	private MaintenanceDao maintenanceDao;

	@Override
	public MaintenanceResponse AddAbnormal(String licensePlate, LocalDateTime startTime) {
		if (!StringUtils.hasText(licensePlate) || Objects.isNull(startTime)) {
			MaintenanceResponse response = new MaintenanceResponse();
			response.setMessage(RtnCode.INCORRECT.getMessage());
			return response;
		}

		// 检查数据是否已存在
		int rowsAffected = maintenanceDao.insertInfo(licensePlate, startTime);

		MaintenanceResponse response = new MaintenanceResponse();
		if (rowsAffected > 0) {
			response.setMessage(RtnCode.SUCCESS.getMessage());
		} else {
			response.setMessage(RtnCode.ALREADY_EXISTED.getMessage());
		}

		return response;
	}

	@Override
	public MaintenanceResponse finishAbnormal(String licensePlate, LocalDateTime startTime, LocalDateTime endTime,
			int price, String note) {
		if (!StringUtils.hasText(licensePlate) || Objects.isNull(startTime) || Objects.isNull(endTime)) {
			MaintenanceResponse response = new MaintenanceResponse();
			response.setMessage(RtnCode.INCORRECT.getMessage());
			return response;
		}

		// 根据传入的note值获取枚举对应的消息
		Abnormal abnormal = Abnormal.valueOf(note);
		String noteMessage = abnormal.getMessage();

		int updatedRows = maintenanceDao.updateInfo(licensePlate, price, startTime, endTime, noteMessage);

//		if (updatedRows == 0) {
//			return new MaintenanceResponse(RtnCode.NOT_FOUND.getMessage());
//		}
//		return new MaintenanceResponse(RtnCode.SUCCESS.getMessage());

		return updatedRows == 0 ? new MaintenanceResponse(RtnCode.NOT_FOUND.getMessage())
				: new MaintenanceResponse(RtnCode.SUCCESS.getMessage());
	}

	@Override
	public MaintenanceResponse deleteAbnormal(String licensePlate, LocalDateTime startTime) {
		int deletedRows = maintenanceDao.deleteInfo(licensePlate, startTime);
		MaintenanceResponse response = new MaintenanceResponse();
		if (deletedRows > 0) {
			response.setMessage(RtnCode.SUCCESS.getMessage());
		} else {
			response.setMessage(RtnCode.NOT_FOUND.getMessage());
		}
		return response;
	}

	@Override
	public MaintenanceResponse searchByAbnormal(String licensePlate) {
		List<Maintenance> maintenanceList = maintenanceDao.searchInfo(licensePlate);
		MaintenanceResponse response = new MaintenanceResponse();
		if (maintenanceList != null && !maintenanceList.isEmpty()) {
			response.setMaintenanceList(maintenanceList);
			response.setMessage(RtnCode.SUCCESS.getMessage());
		} else {
			response.setMessage(RtnCode.NOT_FOUND.getMessage());
		}
		return response;
	}

	@Override
	public MaintenanceResponse searchByStartTimeAndEndTime(LocalDateTime startTime, LocalDateTime endTime) {
		List<Maintenance> maintenanceList = maintenanceDao.searchByStartTimeAndEndTimeInfo(startTime, endTime);
		MaintenanceResponse response = new MaintenanceResponse();

		if (maintenanceList == null || maintenanceList.isEmpty()) {
			response.setMessage(RtnCode.NOT_FOUND.getMessage());
		} else {
			response.setMaintenanceList(maintenanceList);
			response.setMessage(RtnCode.SUCCESS.getMessage());
		}

		return response;
	}

	@Override
	public MaintenanceResponse MonthlyTotalExpense() {
		// TODO Auto-generated method stub
		return null;
	}

}
