package com.example.EasyTravel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.EasyTravel.entity.Fee;

@Repository
public interface FeeDao extends JpaRepository<Fee, Integer> {

	@Transactional
	@Modifying
	@Query(value = "insert into fee (project, cc, rate, threshold) select :project, :cc, :rate, :threshold where not "
			+ "exists (select 1 from fee where project = :project and cc = :cc and rate = :rate)", nativeQuery = true)
	public int insertProject(@Param("project") String project, @Param("cc") int cc, @Param("rate") double rate,
			@Param("threshold") int threshold);

	//TODO
	@Transactional
	@Modifying
	@Query("update Fee f set f.rate = :rate where f.project = :project and f.cc = :cc")
	public int updateRate(@Param("project") String project, @Param("cc") int cc, @Param("rate") double rate);

	@Transactional
	@Modifying
	@Query("update Fee f set f.threshold = :threshold where f.project = :project and f.cc = :cc")
	public int updateThreshold(@Param("project") String project, @Param("cc") int cc, @Param("threshold") int threshold);

	@Transactional
	@Modifying
	@Query("delete Fee f where f.project = :project and f.cc = :cc and f.rate = :rate")
	public int deleteProject(@Param("project") String project, @Param("cc") int cc, @Param("rate") double rate);

	public List<Fee> findByProjectOrderByThresholdDesc(String project);
}
