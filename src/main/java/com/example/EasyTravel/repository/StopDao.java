package com.example.EasyTravel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.EasyTravel.entity.Stop;
import com.example.EasyTravel.entity.StopId;

@Repository
public interface StopDao extends JpaRepository<Stop, StopId> {

	// JPQL 新增資料
	@Transactional
	@Modifying
	@Query(value = "insert into stop (city, location, bike_limit, motorcycle_limit, car_limit) select "
			+ ":city, :location, :bikeLimit, :motorcycleLimit, :carLimit where not exists "
			+ "(select 1 from stop where city = :city and location = :location)", nativeQuery = true)
	public int insertStop(@Param("city") String city, @Param("location") String location,
			@Param("bikeLimit") int bikeLimit, @Param("motorcycleLimit") int motorcycleLimit,
			@Param("carLimit") int carLimit);

	// 尋找站點
	@Query(value = "select * from stop where city = :city", nativeQuery = true)
	public List<Stop> searchStops(@Param("city") String city);

	// 更新站點車載限制-腳踏車
	@Transactional
	@Modifying
	@Query("update Stop s set s.bikeLimit = :bikeLimit where s.city = :city and s.location = :location")
	public int updateBikeLimit(@Param("city") String city, @Param("location") String location,
			@Param("bikeLimit") int bikeLimit);

	// 更新站點車載限制-機車
	@Transactional
	@Modifying
	@Query("update Stop s set s.motorcycleLimit = :motorcycleLimit where s.city = :city and s.location = :location")
	public int updateMotorcycleLimit(@Param("city") String city, @Param("location") String location,
			 @Param("motorcycleLimit") int motorcycleLimit);

	// 更新站點車載限制-車
	@Transactional
	@Modifying
	@Query("update Stop s set s.carLimit = :carLimit where s.city = :city and s.location = :location")
	public int updateCarLimit(@Param("city") String city, @Param("location") String location,
			@Param("carLimit") int carLimit);

}
