package com.example.EasyTravel.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.EasyTravel.constants.RtnCode;
import com.example.EasyTravel.entity.Stop;
import com.example.EasyTravel.entity.StopId;
import com.example.EasyTravel.entity.Vehicle;
import com.example.EasyTravel.repository.StopDao;
import com.example.EasyTravel.repository.VehicleDao;
import com.example.EasyTravel.service.ifs.StopService;
import com.example.EasyTravel.vo.StopResponse;
import com.example.EasyTravel.vo.VehicleCount;

@Service
public class StopServiceImpl implements StopService {

	@Autowired
	private StopDao stopDao;

	@Autowired
	private VehicleDao vehicleDao;

	@Override
	public StopResponse addStop(String city, String location, int bikeLimit, int motorcycleLimit, int carLimit) {
		// 防空輸入
		if (!StringUtils.hasText(city) || !StringUtils.hasText(location) || bikeLimit < 0 || motorcycleLimit < 0
				|| carLimit < 0) {
			return new StopResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		// 新增站點
		return stopDao.insertStop(city, location, bikeLimit, motorcycleLimit, carLimit) == 0
				? new StopResponse(RtnCode.ALREADY_EXISTED.getMessage())
				: new StopResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public StopResponse findCityStops(String city) {
		// 尋找站點
		List<Stop> resList = stopDao.searchStops(city);
		return resList.size() == 0 ? new StopResponse(RtnCode.NOT_FOUND.getMessage())
				: new StopResponse(resList, RtnCode.SUCCESS.getMessage());
	}

	@Override
	public StopResponse renewLimit(String city, String location, int bikeLimit, int motorcycleLimit, int carLimit) {
		// 確認站點存在
		Optional<Stop> res = stopDao.findById(new StopId(city, location));
		// 判斷輸入並更改數量
		return res.map(stop -> {
			boolean flag = false;
			// 更新腳踏車數量，可以為負，但數量不能大於存量
			if ((bikeLimit < 0 & stop.getBikeLimit() > Math.abs(bikeLimit)) || bikeLimit > 0) {
				stop.setBikeLimit(stop.getBikeLimit() + bikeLimit);
				flag = true;
			}
			// 更新機車數量，可以為負，但數量不能大於存量
			if ((motorcycleLimit < 0 & stop.getMotorcycleLimit() > Math.abs(motorcycleLimit)) || motorcycleLimit > 0) {
				stop.setMotorcycleLimit(stop.getMotorcycleLimit() + motorcycleLimit);
				flag = true;
			}
			// 更新汽車數量，可以為負，但數量不能大於存量
			if ((carLimit < 0 & stop.getCarLimit() > Math.abs(carLimit)) || carLimit > 0) {
				stop.setCarLimit(stop.getCarLimit() + carLimit);
				flag = true;
			}
			// 有可能只成功更新幾項，還是會回傳成功
			return flag ? new StopResponse(stopDao.save(stop), RtnCode.SUCCESS.getMessage())
					: new StopResponse(RtnCode.INCORRECT.getMessage());
		}).orElse(new StopResponse(RtnCode.NOT_FOUND.getMessage()));

	}

	@SuppressWarnings("serial")
	@Override
	public StopResponse rentOrReturn(int type, String city, String location) {
		// 建立租借映射表
		Map<Integer, BiFunction<String, String, Integer>> operationMap = new HashMap<>() {
			{
				put(10, (inputC, inputL) -> stopDao.minusBike(city, location)); // 租腳踏車
				put(11, (inputC, inputL) -> stopDao.plusBike(city, location)); // 還腳踏車
				put(20, (inputC, inputL) -> stopDao.minusMotorcycle(city, location)); // 租機車
				put(21, (inputC, inputL) -> stopDao.plusMotorcycle(city, location)); // 還機車
				put(30, (inputC, inputL) -> stopDao.minusCar(city, location)); // 租車
				put(31, (inputC, inputL) -> stopDao.plusCar(city, location)); // 還車
			}
		};
		// 返回租還结果
		return operationMap.getOrDefault(type, (inputC, inputL) -> 0).apply(city, location) == 0
				? new StopResponse(RtnCode.INCORRECT.getMessage())
				: new StopResponse(RtnCode.SUCCESSFUL.getMessage());

	}

	@Override
	public StopResponse dispatch(List<String> vehicleList, String city, String location) {
		// 尋找並分類
		List<VehicleCount> sortRes = vehicleDao.sortCategory(vehicleList);
		// 確認數量無誤
		if (sortRes.stream().mapToLong(c -> c.getCount()).sum() != vehicleList.size()) {
			return new StopResponse(RtnCode.INCORRECT.getMessage());
		}
		// 變更站點，同時要對站點存量進行修正
		List<Vehicle> vehicles = vehicleDao.findAllById(vehicleList);
		for (Vehicle v : vehicles) {
			if (v.getCategory().contains("bike")) {
				stopDao.minusBike(v.getCity(), v.getLocation());
			} else if (v.getCategory().matches("scooter|motorcycle|heavy motorcycle")) {
				stopDao.minusMotorcycle(v.getCity(), v.getLocation());
			} else {
				stopDao.minusCar(v.getCity(), v.getLocation());
			}
		}
		// 修改車輛位置
		int bikeCount = 0, motorcycleCount = 0, carCount = 0;
		for (VehicleCount s : sortRes) {
			bikeCount += s.getCategory().contains("bike") ? (int) s.getCount() : 0;
			motorcycleCount += s.getCategory().matches("scooter|motorcycle|heavy motorcycle") ? (int) s.getCount() : 0;
			carCount += s.getCategory().matches("vedan|ven|suv") ? (int) s.getCount() : 0;
		}
		renewLimit(city, location, bikeCount, motorcycleCount, carCount);
		vehicleDao.dispatch(vehicleList, city, location);
		return new StopResponse(RtnCode.SUCCESSFUL.getMessage());
	}

}
