package com.example.EasyTravel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.EasyTravel.constants.RtnCode;
import com.example.EasyTravel.entity.Stop;
import com.example.EasyTravel.repository.StopDao;
import com.example.EasyTravel.service.ifs.StopService;
import com.example.EasyTravel.vo.StopResponse;

@Service
public class StopServiceImpl implements StopService {

	@Autowired
	private StopDao stopDao;

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
		// 更新車載限制
		int rn = 0;
		if (bikeLimit >= 0) {
			rn = stopDao.updateBikeLimit(city, location, bikeLimit);
		}
		if (bikeLimit >= 0) {
			rn = stopDao.updateMotorcycleLimit(city, location, motorcycleLimit);
		}
		if (carLimit >= 0) {
			rn = stopDao.updateCarLimit(city, location, carLimit);
		}
		return rn == 0 ? new StopResponse(RtnCode.INCORRECT.getMessage())
				: new StopResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public StopResponse RentOrReturn(boolean isRent, String city, String location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StopResponse dispatch(List<String> vehicleList, String city, String location) {
		// TODO Auto-generated method stub
		return null;
	}

}
