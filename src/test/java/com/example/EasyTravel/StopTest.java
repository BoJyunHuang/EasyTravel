package com.example.EasyTravel;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.EasyTravel.constants.RtnCode;
import com.example.EasyTravel.entity.Stop;
import com.example.EasyTravel.entity.StopId;
import com.example.EasyTravel.repository.StopDao;
import com.example.EasyTravel.service.ifs.StopService;

@SpringBootTest(classes = EasyTravelApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StopTest {

	@Autowired
	private StopDao sDao;

	@Autowired
	private StopService sSer;

	@BeforeEach
	private void BeforeEach() {
		// 建立假資料
		sDao.saveAll(new ArrayList<>(
				Arrays.asList(new Stop("city1", "first", 10, 0, 0), new Stop("city1", "Second", 10, 0, 0),
						new Stop("city2", "first", 10, 0, 0), new Stop("city2", "Second", 10, 0, 0))));
	}

	@AfterAll
	private void AfterAll() {
		// 刪除假資料
		sDao.deleteAllById(new ArrayList<>(Arrays.asList(new StopId("city1", "first"), new StopId("city1", "Second"),
				new StopId("city2", "first"), new StopId("city2", "Second"))));
	}

	@Test
	void insertStopTest() {
		// 新增資料已存在
		Assert.isTrue(sDao.insertStop("city1", "first", 0, 0, 0) == 0, RtnCode.TEST1_ERROR.getMessage());
		// 新增成功
		Assert.isTrue(sDao.insertStop("city3", "first", 0, 0, 0) == 1, RtnCode.TEST2_ERROR.getMessage());
		sDao.deleteById(new StopId("city3", "first"));
	}

	@Test
	void searchStopsTest() {
		// 尋找失敗
		Assert.isTrue(sDao.searchStops(null).size() == 0, RtnCode.TEST1_ERROR.getMessage());
		Assert.isTrue(sDao.searchStops("").size() == 0, RtnCode.TEST2_ERROR.getMessage());
		Assert.isTrue(sDao.searchStops("city3").size() == 0, RtnCode.TEST3_ERROR.getMessage());
		// 尋找成功
		Assert.isTrue(sDao.searchStops("city2").size() == 2, RtnCode.TEST4_ERROR.getMessage());
	}

	@Test
	void updateBikeLimitTest() {
		// 更新失敗
		Assert.isTrue(sDao.updateBikeLimit(null, "", 100) == 0, RtnCode.TEST1_ERROR.getMessage());
		Assert.isTrue(sDao.updateBikeLimit("city3", "first", 100) == 0, RtnCode.TEST2_ERROR.getMessage());
		// 更新成功
		Assert.isTrue(sDao.updateBikeLimit("city1", "first", 100) == 1, RtnCode.TEST3_ERROR.getMessage());
	}

	@Test
	void updateMotorcycleLimitTest() {
		// 更新失敗
		Assert.isTrue(sDao.updateMotorcycleLimit(null, "", 100) == 0, RtnCode.TEST1_ERROR.getMessage());
		Assert.isTrue(sDao.updateMotorcycleLimit("city3", "first", 100) == 0, RtnCode.TEST2_ERROR.getMessage());
		// 更新成功
		Assert.isTrue(sDao.updateMotorcycleLimit("city1", "first", 100) == 1, RtnCode.TEST3_ERROR.getMessage());
	}

	@Test
	void updateCarLimitTest() {
		// 更新失敗
		Assert.isTrue(sDao.updateCarLimit(null, "", 100) == 0, RtnCode.TEST1_ERROR.getMessage());
		Assert.isTrue(sDao.updateCarLimit("city3", "first", 100) == 0, RtnCode.TEST2_ERROR.getMessage());
		// 更新成功
		Assert.isTrue(sDao.updateCarLimit("city1", "first", 100) == 1, RtnCode.TEST3_ERROR.getMessage());
	}

	@Test
	void addStopTest() {
		// 輸入為空
		Assert.isTrue(sSer.addStop(null, "", 0, 0, 0).getMessage().equals(RtnCode.CANNOT_EMPTY.getMessage()),
				RtnCode.TEST1_ERROR.getMessage());
		Assert.isTrue(sSer.addStop("city1", "", 0, 0, 0).getMessage().equals(RtnCode.CANNOT_EMPTY.getMessage()),
				RtnCode.TEST2_ERROR.getMessage());
		Assert.isTrue(sSer.addStop("city1", "Third", -5, 0, 0).getMessage().equals(RtnCode.CANNOT_EMPTY.getMessage()),
				RtnCode.TEST3_ERROR.getMessage());
		// 資料已存在
		Assert.isTrue(
				sSer.addStop("city1", "Second", 1, 0, 0).getMessage().equals(RtnCode.ALREADY_EXISTED.getMessage()),
				RtnCode.TEST4_ERROR.getMessage());
		// 新增成功
		Assert.isTrue(sSer.addStop("city1", "Third", 10, 10, 10).getMessage().equals(RtnCode.SUCCESSFUL.getMessage()),
				RtnCode.TEST5_ERROR.getMessage());
		sDao.deleteById(new StopId("city1", "Third"));
	}

	@Test
	void findCityStopsTest() {
		// 尋找失敗
		Assert.isTrue(sSer.findCityStops(null).getMessage().equals(RtnCode.NOT_FOUND.getMessage()),
				RtnCode.TEST1_ERROR.getMessage());
		Assert.isTrue(sSer.findCityStops("").getMessage().equals(RtnCode.NOT_FOUND.getMessage()),
				RtnCode.TEST2_ERROR.getMessage());
		Assert.isTrue(sSer.findCityStops("city3").getMessage().equals(RtnCode.NOT_FOUND.getMessage()),
				RtnCode.TEST3_ERROR.getMessage());
		// 尋找成功
		Assert.isTrue(sSer.findCityStops("city2").getMessage().equals(RtnCode.SUCCESS.getMessage()),
				RtnCode.TEST4_ERROR.getMessage());
	}

	@Test
	void renewLimitTest() {
		// 更新失敗
		Assert.isTrue(sSer.renewLimit(null, null, 0, 0, 0).getMessage().equals(RtnCode.INCORRECT.getMessage()),
				RtnCode.TEST1_ERROR.getMessage());
		Assert.isTrue(sSer.renewLimit("city2", "First", -5, -7, -9).getMessage().equals(RtnCode.INCORRECT.getMessage()),
				RtnCode.TEST2_ERROR.getMessage());
		Assert.isTrue(sSer.renewLimit("city3", "First", 20, 30, 5).getMessage().equals(RtnCode.INCORRECT.getMessage()),
				RtnCode.TEST3_ERROR.getMessage());
		// 更新成功
		Assert.isTrue(sSer.renewLimit("city1", "First", 20, 30, 5).getMessage().equals(RtnCode.SUCCESSFUL.getMessage()),
				RtnCode.TEST4_ERROR.getMessage());
	}

}
