package com.example.EasyTravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.EasyTravel.entity.UserInfo;

@Repository
public interface UserInfoDao extends JpaRepository<UserInfo, String> {

	public UserInfo findByAccountAndPasswordAndActive(String account, String password, boolean active);

	/*
	 * 取得帳號、駕照及vip資訊
	 */
	@Query("select new UserInfo(u.account, u.motorcycleLicense, u.drivingLicense, u.vip) from UserInfo u "
			+ "where u.account = :account and u.active = true")
	public UserInfo checkAccount(
			@Param("account") String account);
}
