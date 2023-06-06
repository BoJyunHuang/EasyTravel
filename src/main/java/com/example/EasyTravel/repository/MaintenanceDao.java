package com.example.EasyTravel.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.EasyTravel.entity.Maintenance;

@Repository
public interface MaintenanceDao extends JpaRepository<Maintenance, Integer> {

//	新增單號
	@Transactional
	@Modifying
	@Query(value = "insert into maintenance (license_plate, start_time,note) select "
			+ ":licensePlate, :startTime, :note where not exists (select 1 from maintenance "
			+ "where license_plate = :licensePlate and start_time = :startTime)", nativeQuery = true)
	public int insertInfo(@Param("licensePlate") String licensePlate, @Param("startTime") LocalDateTime startTime,
			@Param("note") String note);

//	完成單號
	@Transactional
	@Modifying
	@Query(value = "update maintenance set price = :price, end_time = :endTime, note = :note "
			+ "where license_plate = :licensePlate", nativeQuery = true)
	public int updateInfo(@Param("licensePlate") String licensePlate, @Param("price") int price,
			@Param("endTime") LocalDateTime endTime, @Param("note") String note);

// 註銷單號
	@Transactional
	@Modifying
	@Query("delete from Maintenance m where m.licensePlate = :licensePlate and DATE(m.startTime) = DATE(:startTime)")
	public int deleteInfo(@Param("licensePlate") String licensePlate, @Param("startTime") LocalDateTime startTime);


// 查詢單號
	@Query(value = "select * from Maintenance m where m.license_plate = :licensePlate", nativeQuery = true)
	public List<Maintenance> searchInfoByLicensePlate(@Param("licensePlate") String licensePlate);
 
// 藉由保養開始日到結束日查詢期間所有單號

	@Query(value = "SELECT * FROM Maintenance m WHERE DATE(DATE_FORMAT(m.start_time, '%Y-%m-%d')) <= DATE(DATE_FORMAT(:endTime, '%Y-%m-%d')) AND DATE(DATE_FORMAT(m.end_time, '%Y-%m-%d')) >= DATE(DATE_FORMAT(:startTime, '%Y-%m-%d'))", nativeQuery = true)
	public List<Maintenance> searchByStartTimeAndEndTimeInfo(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);


// 藉由車牌尋找近5次的維修紀錄
	@Query(value = "SELECT * FROM Maintenance m WHERE m.license_plate = :licensePlate ORDER BY m.start_time DESC LIMIT 5", nativeQuery = true)
	public List<Maintenance> findRecentMaintenanceByLicensePlate(@Param("licensePlate") String licensePlate);

}
