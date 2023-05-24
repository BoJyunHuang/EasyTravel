package com.example.EasyTravel.service.ifs;

import java.util.List;

import com.example.EasyTravel.vo.StopResponse;

public interface StopService {

	// 新增站點
	public StopResponse addStop(String city, String location, int bike_limit, int motorcycle_limit, int car_limit);

	// 尋找目標城市所有站點
	public StopResponse findCityStops(String city);

	// 更新站點車載限制
	public StopResponse renewLimit(String city, String location, int bike_limit, int motorcycle_limit, int car_limit);

	// 站點變更
	public StopResponse RentOrReturn(boolean isRent, String city, String location);

	// 車輛調度
	public StopResponse dispatch(List<String> vehicleList, String city, String location);
}
