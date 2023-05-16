package com.example.EasyTravel;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
//		Fee f1 = new Fee("test1",50,10,LocalTime.of(0, 0),LocalTime.of(10, 0));
	}
	
	@AfterAll
	private void AfterAll() {
		
	}

	@Test
	void insertProjectTest() {
		// 新增失敗
		// 已存在
		// 新增成功
	}

}
