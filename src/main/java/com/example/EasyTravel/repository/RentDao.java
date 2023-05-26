package com.example.EasyTravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EasyTravel.entity.Rent;

@Repository
public interface RentDao extends JpaRepository<Rent, Integer>{

}
