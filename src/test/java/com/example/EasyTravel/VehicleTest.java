package com.example.EasyTravel;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.EasyTravel.constants.RtnCode;
import com.example.EasyTravel.repository.VehicleDao;
import com.example.EasyTravel.service.ifs.VehicleService;
import com.example.EasyTravel.vo.VehicleRequest;
import com.example.EasyTravel.vo.VehicleResponse;

@SpringBootTest(classes = EasyTravelApplication.class)
public class VehicleTest {

	@Autowired
	private VehicleService vehicleService;

	@Autowired
	private VehicleDao vehicleDao;

	@Test
	public void addCarTest() {
		VehicleRequest req1 = new VehicleRequest("AZ-1234", "", 0, 200);
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

	@Test
	public void availableTest() {
		String licensePlate = "AZ-1234";
		LocalDateTime latestCheckDate = LocalDateTime.of(2023, 6, 5, 15, 37, 3);
		boolean available = true;

		int res = vehicleDao.UpdateAvailable(licensePlate, latestCheckDate, available);
		Assert.isTrue(res == 1, RtnCode.TEST1_ERROR.getMessage());
	}

}
