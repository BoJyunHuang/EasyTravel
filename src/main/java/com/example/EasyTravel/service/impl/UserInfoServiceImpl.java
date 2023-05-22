package com.example.EasyTravel.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.EasyTravel.constants.RtnCode;
import com.example.EasyTravel.entity.UserInfo;
import com.example.EasyTravel.repository.UserInfoDao;
import com.example.EasyTravel.service.ifs.UserInfoService;
import com.example.EasyTravel.vo.UserInfoRequest;
import com.example.EasyTravel.vo.UserInfoResponse;
import com.example.java_demo_test.entity.Login;
import com.example.java_demo_test.vo.LoginResponse;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoDao userInfoDao;
	private String patternAcc = "\\w{3,8}";
//	private String patternAcc = "";
	private String patternPwd = "[[\\w]&&[^\\s]]{8,12}";
//	private String patternPwd = "";

	@Override
	public UserInfoResponse userRegistration(UserInfoRequest request) {
		// 防呆:帳號、密碼、名稱、生日不得為空
		if (!StringUtils.hasText(request.getAccount()) || !StringUtils.hasText(request.getPassword())
				|| !StringUtils.hasText(request.getName()) || request.getBirthday() == null) {
			return new UserInfoResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		// 防呆:帳號 -- 帳號長度 3~8 碼，不能有任何空白
		if (!request.getAccount().matches(patternAcc)) {
			return new UserInfoResponse(RtnCode.INCORRECT.getMessage());
		}
		// 防呆:密碼為8-12碼
		if (!request.getPassword().matches(patternPwd)) {
			return new UserInfoResponse(RtnCode.INCORRECT.getMessage());
		}
		// 防呆:重複註冊帳號
		if (userInfoDao.existsById(request.getAccount())) {
			return new UserInfoResponse(RtnCode.ALREADY_EXISTED.getMessage());
		}
		// 防呆:帳號密碼不可以相同
		if (request.getAccount().equals(request.getPassword())) {
			return new UserInfoResponse(RtnCode.CONFLICT.getMessage());
		}

		// 防呆:生日不可以超過現在日期
		if (request.getBirthday().isAfter(LocalDate.now())) {
			return new UserInfoResponse(RtnCode.INCORRECT.getMessage());
		}

		UserInfo userInfo = new UserInfo(request.getAccount(), request.getPassword(), request.getName(),
				request.getBirthday(), false, LocalDateTime.now());
		userInfoDao.save(userInfo);
		return new UserInfoResponse(RtnCode.SUCCESSFUL.getMessage());
	}

	@Override
	public UserInfoResponse userActive(UserInfoRequest request) {
		// 防呆:不得為空或null
		if (!StringUtils.hasText(request.getAccount()) || !StringUtils.hasText(request.getPassword())) {
			return new UserInfoResponse(RtnCode.CANNOT_EMPTY.getMessage());
		}
		// 自定義:從資料庫找到未激活的帳戶
		UserInfo userInfo = userInfoDao.findByAccountAndPasswordAndActive(request.getAccount(), request.getPassword(),false);
		userInfo.setActive(true);
		userInfoDao.save(userInfo);
		return new UserInfoResponse(RtnCode.SUCCESSFUL.getMessage());
		
	}

	@Override
	public UserInfoResponse userLogin(String account, String password) {
		
		return new UserInfoResponse(RtnCode.SUCCESSFUL.getMessage());
	}
//	public LoginResponse login(String account, String password) {
//		// 防呆:不得為空或null
//		if (!StringUtils.hasText(account) || !StringUtils.hasText(password)) {
//			return new LoginResponse("登入資訊錯誤");
//		}
//		// 自定義方法:找到帳號密碼已生效
//		Login login = loginDao.findByAccountAndPasswordAndIsActive(account, password, true);
//		// optional才有isPresent&Empty
//		if (login == null) {
//			return new LoginResponse("登入資訊不得為空");
//		}
//		return new LoginResponse(RtnCode.SUCCESSFUL.getMessage());
//	}

}
