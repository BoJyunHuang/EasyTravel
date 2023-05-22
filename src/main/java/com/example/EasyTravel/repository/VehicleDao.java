package com.example.EasyTravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EasyTravel.entity.VehicleEntity;

@Repository
public interface VehicleDao extends JpaRepository<VehicleEntity, String>{

}
