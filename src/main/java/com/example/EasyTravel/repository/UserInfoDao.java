package com.example.EasyTravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EasyTravel.entity.UserInfo;
@Repository
public interface UserInfoDao extends JpaRepository <UserInfo,String>{
	
	public UserInfo findByAccountAndPasswordAndActive(String account, String password,boolean active);

	public UserInfo findByAccountAndVip(String account,boolean VIP);
}
