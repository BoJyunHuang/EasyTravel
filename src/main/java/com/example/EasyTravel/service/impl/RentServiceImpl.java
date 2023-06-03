package com.example.EasyTravel.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EasyTravel.constants.RtnCode;
import com.example.EasyTravel.entity.Rent;
import com.example.EasyTravel.entity.UserInfo;
import com.example.EasyTravel.entity.Vehicle;
import com.example.EasyTravel.repository.RentDao;
import com.example.EasyTravel.repository.UserInfoDao;
import com.example.EasyTravel.repository.VehicleDao;
import com.example.EasyTravel.service.ifs.FeeService;
import com.example.EasyTravel.service.ifs.FinanceService;
import com.example.EasyTravel.service.ifs.RentService;
import com.example.EasyTravel.service.ifs.StopService;
import com.example.EasyTravel.vo.FeeResponse;
import com.example.EasyTravel.vo.RentResponse;
import com.example.EasyTravel.vo.StopResponse;
import com.example.EasyTravel.vo.VehicleCount;

@Service
public class RentServiceImpl implements RentService {

	@Autowired
	private VehicleDao vehicleDao;

	@Autowired
	private UserInfoDao userInfoDao;

	@Autowired
	private StopService stopService;

	@Autowired
	private FeeService feeService;

	@Autowired
	private FinanceService financeService;

	@Autowired
	private RentDao rentDao;

	@Override
	public RentResponse rent(String account, String licensePlate) {
		// 確認帳號狀態 與 確認車存在
		UserInfo user = userInfoDao.checkAccount(account);
		Optional<Vehicle> vehicle = vehicleDao.findById(licensePlate);
		// 帳號不存在或未開通，交通工具不存在
		if (user == null || !vehicle.isPresent()) {
			return new RentResponse(RtnCode.NOT_FOUND.getMessage());
		}
		// 租借機車沒有駕照狀況 或 租借汽車沒有駕照狀況 或 車輛目前為不可租用狀態
		if ((vehicle.get().getCategory().matches("scooter|motorcycle|heavy motorcycle") && !user.isMotorcycleLicense())
				|| (vehicle.get().getCategory().matches("vedan|ven|suv") && !user.isDrivingLicense())
				|| !vehicle.get().isAvailable()) {
			return new RentResponse(RtnCode.INCORRECT.getMessage());
		}
		// 租車-更新站點狀態
		StopResponse response = stopService.rentOrReturn(true, vehicle.get().getCategory(), vehicle.get().getCity(),
				vehicle.get().getLocation());
		if (response.getMessage().equals(RtnCode.INCORRECT.getMessage())) {
			return new RentResponse(RtnCode.INCORRECT.getMessage());
		}
		// 租車-更新交通工具狀態
		vehicleDao.updateRentInfo(licensePlate, false, null, null, 0);
		// 建立租借明細
		rentDao.save(new Rent(account, licensePlate, vehicle.get().getCity(), vehicle.get().getLocation()));
		return new RentResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public RentResponse dropOff(String account, String licensePlate, String city, String location, double odo) {
		// 確認是否有租車狀況 與 確認帳號狀態 與 查詢租借資料
		UserInfo user = userInfoDao.checkAccount(account);
		Optional<Vehicle> vehicle = vehicleDao.findById(licensePlate);
		Rent rent = rentDao.findTop1ByAccountAndLicensePlateOrderBySerialNumberDesc(account, licensePlate);
		// 帳號不存在或未開通 或 交通工具不存在 或 租借資料不存在
		if (user == null || !vehicle.isPresent() || rent == null) {
			return new RentResponse(RtnCode.NOT_FOUND.getMessage());
		}
		// 取出租車時間點並記錄當下時間點，依照車種進行經費計算
		FeeResponse calRes = feeService.calculate(vehicle.get(), user.isVip(),
				Duration.between(rent.getNowTime(), LocalDateTime.now()));
		if (!calRes.getMessage().equals(RtnCode.SUCCESS.getMessage())) {
			return new RentResponse(calRes.getMessage());
		}
		// 還車-更新站點狀態
		StopResponse response = stopService.rentOrReturn(false, vehicle.get().getCategory(), city, location);
		if (response.getMessage().equals(RtnCode.INCORRECT.getMessage())) {
			return new RentResponse(RtnCode.INCORRECT.getMessage());
		}
		// 租車-更新交通工具狀態
		vehicleDao.updateRentInfo(licensePlate, true, city, location, odo > 0 ? odo : 0);
		// 建立還車明細-還
		rentDao.save(new Rent(account, licensePlate, city, location, false, calRes.getTotal()));
		// 建立財務收入
		financeService.addReport("rent_income", vehicle.get().getCategory(), calRes.getTotal());
		return new RentResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public RentResponse showDetails(String account) {
		// 查詢該帳號所有的租借明細
		List<Rent> res = rentDao.findByAccount(account);
		return res.size() == 0 ? new RentResponse(RtnCode.NOT_FOUND.getMessage())
				: new RentResponse(res, RtnCode.SUCCESS.getMessage());
	}

	@Override
	public RentResponse monlyRentalCount(int month) {
		// 取出該月份所有資料並計算各租借人次
		List<Rent> res = rentDao.searchMonthData(month);
		if (res == null) {
			return new RentResponse(RtnCode.NOT_FOUND.getMessage());
		}
		// 進行統計分類
		Map<String, Integer> statics = new HashMap<>();
		// 記數，並至vehicle表中統計
		res.forEach(r -> statics.put(r.getLicensePlate(), statics.getOrDefault(r.getLicensePlate(), 0) + 1));
		// 找出車牌的對應車種
		List<Vehicle> vehicleCategories = vehicleDao.searchCategory(new ArrayList<>(statics.keySet()));
//TODO
		return null;
	}

}
