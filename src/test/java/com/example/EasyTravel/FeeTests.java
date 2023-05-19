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
		// 建立假資料
		fDao.saveAll(new ArrayList<>(Arrays.asList(new Fee("test1", 0, 0.0, 30), new Fee("test1", 0, 0.33, 240),
				new Fee("test1", 0, 0.66, 480), new Fee("test1", 0, 1, 1440))));
		fDao.saveAll(
				new ArrayList<>(Arrays.asList(new Fee("test2", 1200, 2000, 1), new Fee("test2", 1200, 45000, 30))));
		fDao.saveAll(
				new ArrayList<>(Arrays.asList(new Fee("test2", 2000, 3500, 1), new Fee("test2", 2000, 95000, 30))));
	}

	@AfterAll
	private void AfterAll() {
		// 自定義方法直接使用
		fDao.deleteProjects("test1", 0);
		fDao.deleteProjects("test2");
	}

	@Test
	void insertProjectTest() {
		// 已存在
		Assert.isTrue(fDao.insertProject("test1", 0, 0.0, 30) == 0, RtnCode.TEST1_ERROR.getMessage());
		// 新增成功
		Assert.isTrue(fDao.insertProject("test2", 0, 0.0, 30) == 1, RtnCode.TEST2_ERROR.getMessage());
	}

	@Test
	void deleteProjectDaoTest() {
		fDao.save(new Fee("test3", 0, 0.0, 30));
		// 輸入空白
		Assert.isTrue(fDao.deleteProject(null, 0, 0.0) == 0, RtnCode.TEST1_ERROR.getMessage());
		Assert.isTrue(fDao.deleteProject("", 0, 0.0) == 0, RtnCode.TEST2_ERROR.getMessage());
		// 新增成功
		Assert.isTrue(fDao.deleteProject("test3", 0, 0.0) == 1, RtnCode.TEST3_ERROR.getMessage());
	}

	@Test
	void searchProjectsByCcUpTest() {
		// 尋找失敗
		Assert.isTrue(fDao.searchProjectsByCcUp(null).size() == 0, RtnCode.TEST1_ERROR.getMessage());
		Assert.isTrue(fDao.searchProjectsByCcUp("").size() == 8, RtnCode.TEST2_ERROR.getMessage());
		Assert.isTrue(fDao.searchProjectsByCcUp("testX").size() == 0, RtnCode.TEST3_ERROR.getMessage());
		// 尋找成功
		Assert.isTrue(fDao.searchProjectsByCcUp("test2").size() == 4, RtnCode.TEST4_ERROR.getMessage());
	}

	@Test
	void searchProjectsByThresholdDownTest() {
		// 尋找失敗
		Assert.isTrue(fDao.searchProjectsByThresholdDown(null, 0).size() == 0, RtnCode.TEST1_ERROR.getMessage());
		Assert.isTrue(fDao.searchProjectsByThresholdDown("", 0).size() == 4, RtnCode.TEST2_ERROR.getMessage());
		Assert.isTrue(fDao.searchProjectsByThresholdDown("test1", -20).size() == 0, RtnCode.TEST3_ERROR.getMessage());
		// 尋找成功
		Assert.isTrue(fDao.searchProjectsByThresholdDown("test2", 1200).size() == 2, RtnCode.TEST4_ERROR.getMessage());
	}

	@Test
	void addProjectTest() {
		// 參數空白
		Assert.isTrue(fSer.addProject(null, 10, 10, 10).getMessage().equals(RtnCode.CANNOT_EMPTY.getMessage()),
				RtnCode.TEST1_ERROR.getMessage());
		Assert.isTrue(fSer.addProject("test3", -1, 0, 0).getMessage().equals(RtnCode.CANNOT_EMPTY.getMessage()),
				RtnCode.TEST2_ERROR.getMessage());
		// 已存在
		Assert.isTrue(fSer.addProject("test1", 0, 0.0, 30).getMessage().equals(RtnCode.ALREADY_EXISTED.getMessage()),
				RtnCode.TEST3_ERROR.getMessage());
		// 新增成功
		Assert.isTrue(fSer.addProject("test3", 0, 0.0, 30).getMessage().equals(RtnCode.SUCCESSFUL.getMessage()),
				RtnCode.TEST3_ERROR.getMessage());
		fDao.deleteProject("test3", 0, 0.0);
	}

	@Test
	void deleteProjectTest() {
		// 參數空白
		Assert.isTrue(fSer.deleteProject(null, 10, 10).getMessage().equals(RtnCode.INCORRECT.getMessage()),
				RtnCode.TEST1_ERROR.getMessage());
		Assert.isTrue(fSer.deleteProject(null, 10, -1).getMessage().equals(RtnCode.INCORRECT.getMessage()),
				RtnCode.TEST2_ERROR.getMessage());
		Assert.isTrue(fSer.deleteProject(null, -1, -1).getMessage().equals(RtnCode.INCORRECT.getMessage()),
				RtnCode.TEST3_ERROR.getMessage());
		// 成功刪除
		fDao.save(new Fee("test3", 0, 0.0, 30));
		Assert.isTrue(fSer.deleteProject("test3", -1, -1).getMessage().equals(RtnCode.SUCCESSFUL.getMessage()),
				RtnCode.TEST4_ERROR.getMessage());
	}

	@Test
	void findProjectsTest() {
		// 尋找失敗
		Assert.isTrue(fSer.findProjects(null, 0).getMessage().equals(RtnCode.NOT_FOUND.getMessage()),
				RtnCode.TEST1_ERROR.getMessage());
		Assert.isTrue(fSer.findProjects(null, -1).getMessage().equals(RtnCode.NOT_FOUND.getMessage()),
				RtnCode.TEST2_ERROR.getMessage());
		// 尋找成功
		Assert.isTrue(fSer.findProjects("test2", -1).getMessage().equals(RtnCode.SUCCESS.getMessage()),
				RtnCode.TEST3_ERROR.getMessage());
		Assert.isTrue(fSer.findProjects("test2", 2000).getMessage().equals(RtnCode.SUCCESS.getMessage()),
				RtnCode.TEST4_ERROR.getMessage());
	}

}
