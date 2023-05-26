package com.example.EasyTravel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.EasyTravel.constants.RtnCode;
import com.example.EasyTravel.entity.Vehicle;
import com.example.EasyTravel.repository.VehicleDao;
import com.example.EasyTravel.service.ifs.VehicleService;
import com.example.EasyTravel.vo.VehicleCount;
import com.example.EasyTravel.vo.VehicleRequest;
import com.example.EasyTravel.vo.VehicleResponse;

@SpringBootTest(classes = EasyTravelApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VehicleTest {

	@Autowired
	private VehicleDao vDao;

	@Autowired
	private VehicleService vehicleService;

	@BeforeEach
	private void BeforeEach() {
		// 建立假資料
		// 汽車
		vDao.saveAll(new ArrayList<>(
				Arrays.asList(new Vehicle("AA-001", "sedan", 2000, 50000), new Vehicle("AA-002", "sedan", 2000, 50000),
						new Vehicle("AA-003", "suv", 2000, 50000), new Vehicle("AA-004", "ven", 2000, 50000))));
		// 機車
		vDao.saveAll(new ArrayList<>(Arrays.asList(new Vehicle("MX-01", "scooter", 50, 100),
				new Vehicle("MX-02", "motorcycle", 150, 300), new Vehicle("MX-03", "scooter", 100, 200),
				new Vehicle("MX-04", "heavy motorcycle", 550, 2000))));
		// 腳踏車
		vDao.saveAll(new ArrayList<>(
				Arrays.asList(new Vehicle("CB0001", "bike", 0, 50), new Vehicle("CB0002", "bike", 0, 50))));
	}

	@AfterAll
	private void AfterAll() {
		// 刪除假資料
		vDao.deleteAllById(new ArrayList<>(Arrays.asList("AA-001", "AA-002", "AA-003", "AA-004", "MX-01", "MX-02",
				"MX-03", "MX-04", "CB0001", "CB0002")));
	}

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

	@Test
	public void sortCategoryTest() {
		// 操作失敗
		Assert.isTrue(vDao.sortCategory(new ArrayList<>(Arrays.asList("test1", "test2", "", null))).size() == 0,
				RtnCode.TEST1_ERROR.getMessage());
		// 操作成功
		Assert.isTrue(vDao
				.sortCategory(new ArrayList<>(
						Arrays.asList("AA-001", "AA-002", "MX-01", "MX-02", "MX-03", "MX-04", "CB0001", "CB0002")))
				.size() == 5, RtnCode.TEST2_ERROR.getMessage());
	}

	@Test
	public void dispatchTest() {
		// 操作失敗
		Assert.isTrue(vDao.dispatch(new ArrayList<>(Arrays.asList("test1", "test2", "", null)), "city1", "first") == 0,
				RtnCode.TEST1_ERROR.getMessage());
		// 操作成功 六筆成功六次
		Assert.isTrue(
				vDao.dispatch(new ArrayList<>(Arrays.asList("AA-001", "AA-002", "MX-01", "MX-02", "CB0001", "CB0002")),
						"city1", "first") == 6,
				RtnCode.TEST2_ERROR.getMessage());
	}

}
