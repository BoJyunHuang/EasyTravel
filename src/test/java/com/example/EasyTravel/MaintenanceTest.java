package com.example.EasyTravel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.EasyTravel.constants.Abnormal;
import com.example.EasyTravel.constants.RtnCode;
import com.example.EasyTravel.entity.Maintenance;
import com.example.EasyTravel.repository.MaintenanceDao;
import com.example.EasyTravel.service.ifs.MaintenanceService;

@SpringBootTest(classes = EasyTravelApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MaintenanceTest {

	@Autowired
	private MaintenanceDao mDao;
	
//	@Autowired 
//	private MaintenanceService mSer;

//	@BeforeEach
//	private void BeforEach() { 
//		List<Maintenance>list = new ArrayList<>();
//		Maintenance m1 = new Maintenance("123",LocalDateTime.of(2022, 12, 8, 12, 1));
//		list.add(m1);
//		mDao.saveAll(list);
//		
//	
//	}
//
//	@AfterAll
//	private void AfterAll() {
//		mDao.deleteInfo("123", LocalDateTime.of(2022, 12, 8, 12, 1));
//
//	}

//	@Test
//	void insertInfoTest() {
//		// 已存在
//		int res = mDao.insertInfo("111", LocalDateTime.of(2022, 12, 8, 12, 1));
//		Assert.isTrue(res == 1, RtnCode.TEST1_ERROR.getMessage());
//		// 新增成功
//	}
	@Test
	void insertInfoTest() {
		// 已存在
		int res = mDao.insertInfo("111", LocalDateTime.of(2022, 12, 8, 12, 1));
		Assert.isTrue(res == 0, RtnCode.TEST1_ERROR.getMessage());
		// 新增成功
	}

	@Test
	void updateInfoTest() {
		// 新增成功
		int res = mDao.updateInfo("123", 1000, LocalDateTime.of(2022, 12, 8, 12, 1), LocalDateTime.of(2022, 12, 8, 12, 3), Abnormal.A01.getMessage());
		Assert.isTrue(res == 1, RtnCode.TEST1_ERROR.getMessage());
		
	}
	@Test
	void searchInfoTest() {
		// 查詢成功
		List<Maintenance> res = mDao.searchInfo("123");
		Assert.notNull(res, RtnCode.TEST1_ERROR.getMessage());  
		 
	}
	@Test
	void searchByStartTimeAndEndTimeInfoTest() {
//	    LocalDateTime startTime = LocalDateTime.of(2022, 12, 8, 12, 1, 0);
//	    LocalDateTime endTime = LocalDateTime.of(2022, 12, 8, 12, 2, 0);
	    
		List<Maintenance> res = mDao.searchByStartTimeAndEndTimeInfo(LocalDateTime.of(2022, 12, 8, 12, 1, 0), LocalDateTime.of(2022, 12, 8, 12, 4, 0));
	    Assert.notNull(res, RtnCode.TEST1_ERROR.getMessage());
	}
	@Test
	void deleteInfoTest() {
		// 新增成功
	}
}
