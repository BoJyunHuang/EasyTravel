package com.example.EasyTravel.service.ifs;

import com.example.EasyTravel.vo.VehicleRequest;
import com.example.EasyTravel.vo.VehicleResponse;

public interface VehicleService {

//	�s�W����
	public VehicleResponse addCar(VehicleRequest vehicleRequest);
	
//	�ק��T
	public VehicleResponse updateCarInfo(VehicleRequest vehicleRequest);
	
//	�ի�
//	public VehicleResponse dispatchCar(VehicleRequest vehicleRequest);
	
//	�˭׫O�i
	public VehicleResponse maintenanceCar(VehicleRequest vehicleRequest);
	
//	���o����
	public VehicleResponse scrapCar(VehicleRequest vehicleRequest);
}
