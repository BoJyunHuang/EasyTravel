package com.example.EasyTravel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.EasyTravel.service.ifs.VehicleService;
import com.example.EasyTravel.vo.VehicleRequest;
import com.example.EasyTravel.vo.VehicleResponse;

@SpringBootTest(classes = EasyTravelApplication.class)
public class VehicleTest {

	@Autowired
	private VehicleService vehicleService;
	
	@Test
	public void addCarTest() {
		VehicleRequest req1 = new VehicleRequest("AZ-1234", "", 0, 200);
		VehicleResponse res1 = vehicleService.addCar(req1);
		System.out.println(res1.getMessage());
	}
	
	@Test
	public void updateCarInfoTest() {
		VehicleRequest req1 = new VehicleRequest("AC-1234", false, -9.7);
		VehicleResponse res1 = vehicleService.updateCarInfo(req1);
		System.out.println(res1.getMessage());
	}
	
	@Test
	public void scrapCarTest() {
		VehicleRequest req1 = new VehicleRequest("AH-1234");
		VehicleResponse res1 = vehicleService.scrapCar(req1);
		System.out.println(res1.getMessage());
	}
	
	@Test
	public void findCarByCategoryTest() {
		VehicleRequest req1 = new VehicleRequest();
		req1.categoryVehicleRequest("bike");
		VehicleResponse res1 = vehicleService.findCarByCategory(req1);
		System.out.println(res1.getMessage());
	}
	
}
