package com.example.EasyTravel.service.ifs;

import com.example.EasyTravel.vo.VehicleRequest;
import com.example.EasyTravel.vo.VehicleResponse;

public interface VehicleService {

//	新增車輛
	public VehicleResponse addCar(VehicleRequest vehicleRequest);
	
//	修改資訊
	public VehicleResponse updateCarInfo(VehicleRequest vehicleRequest);
	
//	調度
//	public VehicleResponse dispatchCar(VehicleRequest vehicleRequest);
	
//	檢修保養
	public VehicleResponse maintenanceCar(VehicleRequest vehicleRequest);
	
//	報廢車輛
	public VehicleResponse scrapCar(VehicleRequest vehicleRequest);
}
