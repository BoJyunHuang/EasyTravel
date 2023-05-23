package com.example.EasyTravel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EasyTravel.entity.Vehicle;

@Repository
public interface VehicleDao extends JpaRepository<Vehicle, String>{

	public List<Vehicle> findAllByCategoryOrderByAvailableDesc(String category);
}
