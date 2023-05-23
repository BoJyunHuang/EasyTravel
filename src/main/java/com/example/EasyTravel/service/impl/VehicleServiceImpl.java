package com.example.EasyTravel.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.EasyTravel.entity.Vehicle;
import com.example.EasyTravel.repository.VehicleDao;
import com.example.EasyTravel.service.ifs.VehicleService;
import com.example.EasyTravel.vo.VehicleRequest;
import com.example.EasyTravel.vo.VehicleResponse;

@Service
public class VehicleServiceImpl implements VehicleService {

	@Autowired
	private VehicleDao vehicleDao;

	@Override
	public VehicleResponse addCar(VehicleRequest vehicleRequest) {
//		判斷 > 車牌號碼
//		是否輸入車牌號碼
		if (!StringUtils.hasText(vehicleRequest.getLicensePlate())) {
			return new VehicleResponse("error : 請輸入車牌號碼");
		}
//		車輛是否存在
		Optional<Vehicle> op = vehicleDao.findById(vehicleRequest.getLicensePlate());
		if (op.isPresent()) {
			return new VehicleResponse("error : 此車輛已存在");
		}
//		判斷 > 車輛類別 & cc引擎汽缸大小
		if (!StringUtils.hasText(vehicleRequest.getCategory())) {
			return new VehicleResponse("error : 請輸入車輛類別");
		}
		switch (vehicleRequest.getCategory()) {
		// bike > 0
		case "bike":
			if (vehicleRequest.getCc() != 0) {
				return new VehicleResponse("error : cc數設定錯誤");
			}
//			如果沒有break, 判斷false會一直進到下一個case
			break;
		// scooter > 1~250
		case "scooter":
			if (vehicleRequest.getCc() < 1 || vehicleRequest.getCc() >= 250) {
				return new VehicleResponse("error : cc數設定錯誤");
			}
			break;
		// motorcycle > 251~550
		case "motorcycle":
			if (vehicleRequest.getCc() < 251 || vehicleRequest.getCc() >= 550) {
				return new VehicleResponse("error : cc數設定錯誤");
			}
			break;
		// heavy motorcycle > 550~
		case "heavy motorcycle":
			if (vehicleRequest.getCc() < 550) {
				return new VehicleResponse("error : cc數設定錯誤");
			}
			break;
		// sedan > 自定義(1200~4200)
		case "sedan":
			if (vehicleRequest.getCc() <= 1200 || vehicleRequest.getCc() >= 4200) {
				return new VehicleResponse("error : cc數設定錯誤");
			}
			break;
		// ven, SUV > 自定義(2000~6600)
		case "ven":
		case "suv":
			if (vehicleRequest.getCc() <= 2000 || vehicleRequest.getCc() >= 6600) {
				return new VehicleResponse("error : cc數設定錯誤");
			}
			break;
//			若不符合以上case, 表輸入之車輛類別錯誤
		default:
			return new VehicleResponse("error : 請輸入有效車輛類別");
		}

//		起始服役日
		vehicleRequest.setStartServingDate(LocalDate.now());
//		最新檢查日(=起始服役日)
		vehicleRequest.setLatestCheckDate(LocalDate.now());
//		可租借狀態
		vehicleRequest.setAvailable(false);
//		初始總里程=0
		vehicleRequest.setOdo(0);
//		價格
		if (vehicleRequest.getPrice() < 1) {
			return new VehicleResponse("error : 價格設定錯誤");
		}

		Vehicle saveVehicle = new Vehicle(vehicleRequest.getLicensePlate(), vehicleRequest.getCategory(),
				vehicleRequest.getCc(), vehicleRequest.getStartServingDate(), vehicleRequest.getLatestCheckDate(),
				vehicleRequest.isAvailable(), vehicleRequest.getOdo(), vehicleRequest.getPrice());
		vehicleDao.save(saveVehicle);
		return new VehicleResponse(saveVehicle, "成功 : 新增車輛成功");
	}

	@Override
	public VehicleResponse updateCarInfo(VehicleRequest vehicleRequest) {
//		判斷 > 車牌號碼
//		是否輸入車牌號碼
		if (!StringUtils.hasText(vehicleRequest.getLicensePlate())) {
			return new VehicleResponse("error : 請輸入車牌號碼");
		}
//		車輛是否存在
		Optional<Vehicle> op = vehicleDao.findById(vehicleRequest.getLicensePlate());
		if (!op.isPresent()) {
			return new VehicleResponse("error : 此車輛不存在");
		}

//		加總新總里程數
		if(vehicleRequest.getOdo() < 0) {
			return new VehicleResponse("error : 總里程數錯誤");
		}
		double oldOdo = op.get().getOdo();
		double newOdo = 0.0;
		newOdo = oldOdo + vehicleRequest.getOdo();
		
//		設定新vehicle接op內的東西 > 新可使用狀態&新總里程數蓋過舊資料 > 存進資料庫
		Vehicle updateVehicle = op.get();
		updateVehicle.updateVehicleEntity(vehicleRequest.isAvailable(), newOdo);
		vehicleDao.save(updateVehicle);
		return new VehicleResponse(updateVehicle, "修改資料成功");
	}

	@Override
	public VehicleResponse maintenanceCar(VehicleRequest vehicleRequest) {
//		判斷 > 車牌號碼
//		是否輸入車牌號碼
		if (!StringUtils.hasText(vehicleRequest.getLicensePlate())) {
			return new VehicleResponse("error : 請輸入車牌號碼");
		}
//		車輛是否存在
		Optional<Vehicle> op = vehicleDao.findById(vehicleRequest.getLicensePlate());
		if (!op.isPresent()) {
			return new VehicleResponse("error : 此車輛不存在");
		}
		
//		欲檢修車輛之可租用狀態 > 設定為false
		
		return null;
	}

//	腳踏車7年、機車10年或12W公里、汽車15年或60W公里
	@Override
	public VehicleResponse scrapCar(VehicleRequest vehicleRequest) {
//		判斷 > 車牌號碼
//		是否輸入車牌號碼
		if (!StringUtils.hasText(vehicleRequest.getLicensePlate())) {
			return new VehicleResponse("error : 請輸入車牌號碼");
		}
//		車輛是否存在
		Optional<Vehicle> op = vehicleDao.findById(vehicleRequest.getLicensePlate());
		if (!op.isPresent()) {
			return new VehicleResponse("error : 此車輛不存在");
		}
		
		LocalDate today = LocalDate.now();
		LocalDate startServingDate = op.get().getStartServingDate();
//		判斷車種
		switch(op.get().getCategory()) {
		case "bike":
			if(today.getYear() - startServingDate.getYear() >= 7) {
//				可租用狀態改false
				op.get().setAvailable(false);
			}else {
				return new VehicleResponse("此車輛尚無須報廢");
			}
			break;
		case "scooter":
		case "motorcycle":
		case "heavy motorcycle":
			if(today.getYear() - startServingDate.getYear() >= 10 || op.get().getOdo() >= 120_000 ) {
				op.get().setAvailable(false);
			}else {
				return new VehicleResponse("此車輛尚無須報廢");
			}
			break;
		case "sedan":
		case "ven":
		case "suv":
			if(today.getYear() - startServingDate.getYear() >= 15 || op.get().getOdo() >= 600_000 ) {
				op.get().setAvailable(false);
			}else {
				return new VehicleResponse("此車輛尚無須報廢");
			}
			break;
		default:
			return new VehicleResponse("error : 請輸入有效車輛類別");
		}
		Vehicle scrapCar = op.get();
		vehicleDao.save(scrapCar);
		return new VehicleResponse(scrapCar, "已報廢此車輛");
	}

	
	@Override
	public VehicleResponse findCarByCategory(VehicleRequest vehicleRequest) {
		if(!StringUtils.hasText(vehicleRequest.getCategory())) {
			return new VehicleResponse("請輸入欲查詢車種");
		}
		List<Vehicle> carList = new ArrayList<Vehicle>();
		List<String> category = Arrays.asList("bike", "scooter", "motorcycle", "heavy motorcycle",
				"sedan", "ven", "suv");
		if(!category.contains(vehicleRequest.getCategory())) {
			return new VehicleResponse("無此車種");
		}
		carList.addAll(vehicleDao.findAllByCategoryOrderByAvailableDesc(vehicleRequest.getCategory()));
		return new VehicleResponse(carList, "success");
	}

}
