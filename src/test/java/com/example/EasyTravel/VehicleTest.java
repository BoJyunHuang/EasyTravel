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
		VehicleResponse res1 = vehicleService.addCar("AZ-1234", "", 0, 200);
		System.out.println(res1.getMessage());
	}
	
	@Test
	public void updateCarInfoTest() {
		VehicleResponse res1 = vehicleService.updateCarInfo("AC-1234", -9.7, false);
		System.out.println(res1.getMessage());
	}
	
	@Test
	public void scrapCarTest() {
		VehicleResponse res1 = vehicleService.scrapCar("AH-1234");
		System.out.println(res1.getMessage());
	}
	
	@Test
	public void findCarByCategoryTest() {
		VehicleResponse res1 = vehicleService.findCarByCategory("bike");
		System.out.println(res1.getMessage());
	}
	
}
