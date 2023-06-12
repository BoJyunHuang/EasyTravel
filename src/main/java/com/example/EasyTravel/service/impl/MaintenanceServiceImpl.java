package com.example.EasyTravel.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.EasyTravel.constants.Abnormal;
import com.example.EasyTravel.constants.RtnCode;
import com.example.EasyTravel.entity.Maintenance;
import com.example.EasyTravel.repository.MaintenanceDao;
import com.example.EasyTravel.repository.VehicleDao;
import com.example.EasyTravel.service.ifs.MaintenanceService;
import com.example.EasyTravel.vo.MaintenanceResponse;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

	@Autowired
	private MaintenanceDao maintenanceDao;
	@Autowired
	private VehicleDao vehicleDao;

	/*
	 * 1.防呆:避免車牌輸入空產生空指針 2.要維修的車輛先在vehicle表內設定為false使其不能租借
	 * 3.note內容值間選取enum內代碼做選擇產生訊息 4.將車牌/開始維修時間/note插入到maintenance表內
	 * 5.若新增成功回傳成功message/反之
	 */
	@Override
	public MaintenanceResponse AddAbnormal(String licensePlate) {
		if (!StringUtils.hasText(licensePlate)) {
			return new MaintenanceResponse(RtnCode.INCORRECT.getMessage());
		}
		LocalDateTime startTime = LocalDateTime.now();
		boolean available = false;
		LocalDateTime latestCheckDate = null;
		int updatedVehicleRows = vehicleDao.UpdateAvailable(licensePlate, latestCheckDate, available);

		String note = Abnormal.E.getMessage(); // 使用枚举常量 E 的消息作为注释内容

		int rowsAffected = maintenanceDao.insertInfo(licensePlate, startTime, note);

		return (rowsAffected > 0 && updatedVehicleRows > 0) ? new MaintenanceResponse(RtnCode.SUCCESS.getMessage())
				: new MaintenanceResponse(RtnCode.ALREADY_EXISTED.getMessage());
	}

	/*
	 * 1.防呆:避免車牌輸入空產生空指針,並且規定輸入價格要大於0符合邏輯 2.note內容值間選取enum內代碼做選擇產生訊息
	 * 3.用車牌尋找近5筆維修資料,如果end_time欄位為空，則進行完成單號的更新 4.更新內容為價格/完成時間/維修內容
	 * 5.並且在vehicle該維修車輛使用狀況改回true使其可以被租借 6.若更新成功回傳成功message/反之
	 */

	@Override
	public MaintenanceResponse finishAbnormal(String licensePlate, int price, String note) {
		if (!StringUtils.hasText(licensePlate) || price <= 0) {
			MaintenanceResponse response = new MaintenanceResponse();
			response.setMessage(RtnCode.INCORRECT.getMessage());
			return response;
		}

		LocalDateTime endTime = LocalDateTime.now();

		String noteMessage = Abnormal.valueOf(note).getMessage();

		List<Maintenance> recentMaintenanceList = maintenanceDao.findRecentMaintenanceByLicensePlate(licensePlate);

		for (Maintenance maintenance : recentMaintenanceList) {
			if (maintenance.getEndTime() == null) {
				int updatedRows = maintenanceDao.updateInfo(licensePlate, price, endTime, noteMessage);

				// 更新车辆信息
				boolean available = true;
				LocalDateTime latestCheckDate = endTime; // 假设修理完成时间即为最新检查日期
				int updatedVehicleRows = vehicleDao.UpdateAvailable(licensePlate, latestCheckDate, available);

				return (updatedRows == 0 || updatedVehicleRows == 0)
						? new MaintenanceResponse(RtnCode.NOT_FOUND.getMessage())
						: new MaintenanceResponse(RtnCode.SUCCESS.getMessage());
			}
		}

		// 如果沒有找到需要更新的維修資料，則返回相應的響應
		return new MaintenanceResponse(RtnCode.NOT_FOUND.getMessage());
	}

	/*
	 * 1.依照車牌跟開始維修時間找到該維修紀錄並且刪除 2.若刪除成功回傳成功message/反之
	 */

	@Override
	public MaintenanceResponse deleteAbnormal(String licensePlate, LocalDateTime startTime) {
		int deletedRows = maintenanceDao.deleteInfo(licensePlate, startTime);
		return deletedRows > 0 ? new MaintenanceResponse(RtnCode.SUCCESS.getMessage())
				: new MaintenanceResponse(RtnCode.NOT_FOUND.getMessage());

	}

	/*
	 * 1.依照車牌找該車輛所有的維修紀錄並且回傳List 2.若查詢成功回傳成功message/反之
	 */

	@Override
	public MaintenanceResponse searchByAbnormal(String licensePlate) {
		List<Maintenance> maintenanceList = maintenanceDao.searchInfoByLicensePlate(licensePlate);
		MaintenanceResponse response = new MaintenanceResponse();

		response.setMaintenanceList(maintenanceList);
		response.setMessage((maintenanceList != null && !maintenanceList.isEmpty()) ? RtnCode.SUCCESS.getMessage()
				: RtnCode.NOT_FOUND.getMessage());

		return response;
	}

	/*
	 * 1.依照開始時間跟結束時間尋找期間所有車輛的維修紀錄並且回傳List 2.若查詢成功回傳成功message/反之
	 */

	@Override
	public MaintenanceResponse searchByStartTimeAndEndTime(LocalDateTime startTime, LocalDateTime endTime) {
		List<Maintenance> maintenanceList = maintenanceDao.searchByStartTimeAndEndTimeInfo(startTime, endTime);
		MaintenanceResponse response = new MaintenanceResponse();

		response.setMaintenanceList(maintenanceList);
		response.setMessage((maintenanceList == null || maintenanceList.isEmpty()) ? RtnCode.NOT_FOUND.getMessage()
				: RtnCode.SUCCESS.getMessage());

		return response;
	}

	@Override
	public MaintenanceResponse findLatestTenUnfinishedAbnormal() {
		List<Maintenance> maintenanceList = maintenanceDao.findLatestTenByNote();
		MaintenanceResponse response = new MaintenanceResponse();
		response.setMaintenanceList(maintenanceList);
		response.setMessage((maintenanceList != null && !maintenanceList.isEmpty()) ? RtnCode.SUCCESS.getMessage()
				: RtnCode.NOT_FOUND.getMessage());

		return response;
	}

	@Override
	public MaintenanceResponse findAllFinishedAbnormal() {
	    List<Maintenance> maintenanceList = maintenanceDao.findAllFinishedCasesByPrice();
	    MaintenanceResponse response = new MaintenanceResponse();
	    response.setMaintenanceList(maintenanceList);
	    response.setMessage((maintenanceList != null && !maintenanceList.isEmpty()) ? RtnCode.SUCCESS.getMessage()
	            : RtnCode.NOT_FOUND.getMessage());

	    return response;
	}

}
