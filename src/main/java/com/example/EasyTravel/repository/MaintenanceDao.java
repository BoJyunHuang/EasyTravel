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

	// 新增單號
	@Transactional
	@Modifying
	@Query(value = "insert into maintenance (license_plate, start_time) select "
			+ ":licensePlate, :startTime where not exists (select 1 from maintenance "
			+ "where license_plate = :licensePlate and start_time = :startTime)", nativeQuery = true)
	public int insertInfo(@Param("licensePlate") String licensePlate, @Param("startTime") LocalDateTime startTime);

	// 完成單號
	@Transactional
	@Modifying
	@Query(value = "update maintenance set price = :price, start_time = :startTime, end_time = :endTime, note = :note "
			+ "where license_plate = :licensePlate and start_time = :startTime", nativeQuery = true)
	public int updateInfo(@Param("licensePlate") String licensePlate, @Param("price") int price,
			@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime,
			@Param("note") String note);

	// 註銷單號
	@Transactional
	@Modifying
	@Query("delete from Maintenance m where m.licensePlate = :licensePlate and m.startTime = :startTime")
	public int deleteInfo(@Param("licensePlate") String licensePlate, @Param("startTime") LocalDateTime startTime);

	// 查詢單號
	@Query(value = "select * from Maintenance m where m.license_plate = :licensePlate", nativeQuery = true)
	public List<Maintenance> searchInfo(@Param("licensePlate") String licensePlate);

	// 藉由保養開始日到結束日查詢所有單號
	// 查詢單號
//	@Query(value = "select * from Maintenance m where m.start_time = :startTime and m.end_time = :endTime", nativeQuery = true)
//	public Maintenance searchByStartTimeAndEndTimeInfo(@Param("startTime") LocalDateTime startTime,
//			@Param("endTime") LocalDateTime endTime);
	// 藉由保養開始日到結束日查詢所有單號
	// 查詢單號
	@Query(value = "select * from Maintenance m where m.start_time <= :endTime and m.end_time >= :startTime", nativeQuery = true)
	public List<Maintenance> searchByStartTimeAndEndTimeInfo(@Param("startTime") LocalDateTime startTime,
			@Param("endTime") LocalDateTime endTime);

}
