package com.example.EasyTravel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EasyTravel.entity.VehicleEntity;

@Repository
public interface VehicleDao extends JpaRepository<VehicleEntity, String>{

	public List<VehicleEntity> findAllByCategoryOrderByAvailableDesc(String category);
}
