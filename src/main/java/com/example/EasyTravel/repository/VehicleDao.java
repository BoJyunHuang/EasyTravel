package com.example.EasyTravel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.EasyTravel.entity.Vehicle;
import com.example.EasyTravel.vo.VehicleCount;

@Repository
public interface VehicleDao extends JpaRepository<Vehicle, String> {

	public List<Vehicle> findAllByCategoryOrderByAvailableDesc(String category);

	// 依資料車種分類
	@Query("select new com.example.EasyTravel.vo.VehicleCount (v.category, count(v)) from Vehicle v "
			+ "where v.licensePlate in :vList group by v.category")
	public List<VehicleCount> sortCategory(@Param("vList") List<String> vehicleList);

	// 更改多車輛的位置
	@Transactional
	@Modifying
	@Query(value = "update vehicle set city = :city, location = :location where license_plate in :vList", nativeQuery = true)
	public int dispatch(@Param("vList") List<String> vehicleList, @Param("city") String city,
			@Param("location") String location);

	// 更改車輛租借狀態
	@Transactional
	@Modifying
	@Query(value = "update vehicle set available = :available, city = :city, location = :location, odo = odo + :odo "
			+ "where license_plate = :licensePlate", nativeQuery = true)
	public int updateRentInfo(@Param("licensePlate") String licensePlate, @Param("available") boolean available,
			@Param("city") String city, @Param("location") String location, @Param("odo") double odo);
}
