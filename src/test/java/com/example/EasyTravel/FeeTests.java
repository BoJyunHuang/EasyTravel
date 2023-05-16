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
import com.example.EasyTravel.entity.Fee;
import com.example.EasyTravel.repository.FeeDao;
import com.example.EasyTravel.service.ifs.FeeService;

@SpringBootTest(classes = EasyTravelApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FeeTests {

	@Autowired
	private FeeDao fDao;

	@Autowired
	private FeeService fSer;

	@BeforeEach
	private void BeforeEach() {
		fDao.saveAll(new ArrayList<>(Arrays.asList(new Fee("test1", 0, 0.0, 30), new Fee("test1", 0, 0.33, 240),
				new Fee("test1", 0, 0.66, 480), new Fee("test1", 0, 1, 1440))));
	}

	@AfterAll
	private void AfterAll() {
		// 自定義方法直接使用
		fDao.deleteProject("test1", 0, 0.0);
		fDao.deleteProject("test1", 0, 0.33);
		fDao.deleteProject("test1", 0, 0.66);
		fDao.deleteProject("test1", 0, 1);
	}

	@Test
	void insertProjectTest() {
		// 已存在
		Assert.isTrue(fDao.insertProject("test1", 0, 0.0, 30) == 0, RtnCode.TEST1_ERROR.getMessage());
		// 新增成功
		Assert.isTrue(fDao.insertProject("test2", 0, 0.0, 30) == 1, RtnCode.TEST2_ERROR.getMessage());
		fDao.deleteProject("test2", 0, 0);
	}
	
	@Test
	void updateRateTest() {
		//
	}

}
