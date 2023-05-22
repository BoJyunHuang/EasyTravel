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
	public void updateCarInfo() {
		VehicleRequest req1 = new VehicleRequest("AC-1234", false, -9.7);
		VehicleResponse res1 = vehicleService.updateCarInfo(req1);
		System.out.println(res1.getMessage());
	}
	
	@Test
	public void scrapCar() {
		VehicleRequest req1 = new VehicleRequest("AA-1234");
		VehicleResponse res1 = vehicleService.scrapCar(req1);
		System.out.println(res1.getMessage());
	}
	
}
