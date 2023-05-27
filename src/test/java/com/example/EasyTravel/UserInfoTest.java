package com.example.EasyTravel;

import java.time.LocalDate;
//import java.time.LocalTime;

import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.EasyTravel.controller.UserInfoController;
import com.example.EasyTravel.service.ifs.UserInfoService;
import com.example.EasyTravel.vo.UserInfoRequest;
import com.example.EasyTravel.vo.UserInfoResponse;

//@SpringBootTest(classes = EasyTravelApplication.class)
//@SpringBootTest(classes = EasyTravelApplicationTests.class)
@SpringBootTest(classes = EasyTravelApplication.class)
public class UserInfoTest {

	@Autowired
	public UserInfoService userInfoService;

	@Test
	public void userRegistrationTest1() {
		// 防呆:帳號、密碼、名稱、生日不得為空
		UserInfoRequest request = new UserInfoRequest();
		request.setAccount("12345678");
		request.setPassword("1234565789");
		request.setName("路招搖");
		LocalDate birthday = null;
		request.setBirthday(birthday);

		UserInfoResponse res = userInfoService.userRegistration(request);
		System.out.println(res.getMessage());

	}

	@Test
	public void userRegistrationTest2() {
		UserInfoRequest request = new UserInfoRequest();
		// 防呆:帳號 -- 帳號長度 3~8 碼，不能有任何空白
		request.setAccount("12");
		request.setPassword("12345678");
		request.setName("路招搖");
		request.setBirthday(LocalDate.of(1994, 9, 23));

		UserInfoResponse res = userInfoService.userRegistration(request);
		System.out.println(res.getMessage());

	}

	@Test
	public void userRegistrationTest3() {
		UserInfoRequest request = new UserInfoRequest();
		// 防呆:密碼為8-12碼
		request.setAccount("12345678");
		request.setPassword("12");
		request.setName("路招搖");
		request.setBirthday(LocalDate.of(1994, 9, 23));

		UserInfoResponse res = userInfoService.userRegistration(request);
		System.out.println(res.getMessage());

	}

	@Test
	public void userRegistrationTest4() {
		UserInfoRequest request = new UserInfoRequest();
		// 防呆:重複註冊帳號
		request.setAccount("01234567");
		request.setPassword("0123456789");
		request.setName("路招搖");
		request.setBirthday(LocalDate.of(1994, 9, 23));

		UserInfoResponse res = userInfoService.userRegistration(request);
		System.out.println(res.getMessage());

	}

	@Test
	public void userRegistrationTest5() {
		UserInfoRequest request = new UserInfoRequest();
		// 防呆:帳號密碼不可以相同
		request.setAccount("12345678");
		request.setPassword("12345678");
		request.setName("路招搖");
		request.setBirthday(LocalDate.of(1994, 9, 23));

		UserInfoResponse res = userInfoService.userRegistration(request);
		System.out.println(res.getMessage());

	}

	@Test
	public void userRegistrationTest6() {
		UserInfoRequest request = new UserInfoRequest();
		// 防呆:生日不可以超過現在日期
		request.setAccount("12345678");
		request.setPassword("123456789");
		request.setName("路招搖");
		request.setBirthday(LocalDate.of(1994, 9, 23));

		UserInfoResponse res = userInfoService.userRegistration(request);
		System.out.println(res.getMessage());

	}

	@Test
	public void userActiveTest1() {
		UserInfoRequest request = new UserInfoRequest();
		// 防呆:帳密不得為空或null
		request.setAccount("");
		request.setPassword("123456789");
		request.setName("路招搖");
		request.setBirthday(LocalDate.of(1994, 9, 23));

		UserInfoResponse res = userInfoService.userActive(request);
		System.out.println(res.getMessage());

	}

	@Test
	public void userActiveTest2() {
		UserInfoRequest request = new UserInfoRequest();
		// 成功激活
		request.setAccount("12345678");
		request.setPassword("123456789");
		request.setName("路招搖");
		request.setBirthday(LocalDate.of(1994, 9, 23));

		UserInfoResponse res = userInfoService.userActive(request);
		System.out.println(res.getMessage());

	}

	@Test
	void userLoginTest1() {
		UserInfoRequest request = new UserInfoRequest();
		// 防呆:帳密不得為空或null
		request.setAccount("");
		request.setPassword("123456789");

		UserInfoResponse res = userInfoService.userLogin(request);
		System.out.println(res.getMessage());

	}

	@Test
	public void userLoginTest2() {
		UserInfoRequest request = new UserInfoRequest();
		// 成功登入
		request.setAccount("12345678");
		request.setPassword("123456789");

		UserInfoResponse res = userInfoService.userLogin(request);
		System.out.println(res.getMessage());

	}

	@Test
	public void userInfoSearchTest() {
		UserInfoRequest request = new UserInfoRequest();
//		UserInfoController controller = new UserInfoController();
//		controller.userLogin(request, null);
		// 成功查詢
//		request.setAccount("12345678");
//		request.setPassword("123456789");

		UserInfoResponse res = userInfoService.userInfoSearch(request);
		System.out.println(res.getMessage());

	}

	@Test
	public void userInfoUpdateTest() {
		UserInfoRequest request = new UserInfoRequest();
		request.setAccount("12345678");
		request.setPassword("123456789");

		request.setBirthday(LocalDate.of(1999, 9, 23));
		request.setDrivingLicense(true);
		request.setMotorcycleLicense(true);
		UserInfoResponse res = userInfoService.userInfoUpdate(request);
		System.out.println(res.getMessage());
	}
	@Test
	public void userInfoUpgradeVIPTest() {
		UserInfoRequest request = new UserInfoRequest();
		request.setAccount("12345678");

		UserInfoResponse res = userInfoService.userInfoUpgradeVIP(request);
		System.out.println(res.getMessage());
	}
	@Test
	public void userInfoQuitVIPTest() {
		UserInfoRequest request = new UserInfoRequest();
		request.setAccount("12345678");

		UserInfoResponse res = userInfoService.userInfoQuitVIP(request);
		System.out.println(res.getMessage());
	}

}
