package com.example.EasyTravel.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.EasyTravel.entity.Vehicle;

@Repository
public interface VehicleDao extends JpaRepository<Vehicle, String>{

	public List<Vehicle> findAllByCategoryOrderByAvailableDesc(String category);
	
	@Transactional
	@Modifying 
	@Query(value = "update vehicle set available = :available, latest_check_date = :latestCheckDate "			
			+ "where license_plate = :licensePlate", nativeQuery = true)
	public int UpdateAvailable(@Param("licensePlate") String licensePlate, @Param("latestCheckDate") LocalDateTime latestCheckDate,@Param("available") boolean available);
}
