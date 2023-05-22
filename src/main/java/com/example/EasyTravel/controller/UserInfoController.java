package com.example.EasyTravel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.EasyTravel.service.ifs.UserInfoService;
import com.example.EasyTravel.vo.UserInfoRequest;
import com.example.EasyTravel.vo.UserInfoResponse;

@RestController
public class UserInfoController {

	@Autowired // 指定託管類別
	private UserInfoService userInfoService;

	// 會員註冊:連結外部
	@PostMapping(value = "user_registration")
	public UserInfoResponse userRegistration(@RequestBody UserInfoRequest request) {
		// 連結內部
		return userInfoService.userRegistration(request);
	}

	// 會員激活帳號:連結外部
	@PostMapping(value = "user_active")
	public UserInfoResponse userActive(@RequestBody UserInfoRequest request) {
		// 連結內部
		return userInfoService.userActive(request);

	}
}
