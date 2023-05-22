package com.example.EasyTravel.service.ifs;

import com.example.EasyTravel.vo.UserInfoRequest;
import com.example.EasyTravel.vo.UserInfoResponse;

public interface UserInfoService {

	//方法1:會員註冊
	public UserInfoResponse userRegistration (UserInfoRequest request);
	
	//方法2:帳號啟用
	public UserInfoResponse userActive (UserInfoRequest request);
	
	public UserInfoResponse userLogin (String account, String password);
	
}
