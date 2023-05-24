package com.example.EasyTravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EasyTravel.entity.Rent;

public interface RentDao extends JpaRepository<Rent, Integer>{

}
