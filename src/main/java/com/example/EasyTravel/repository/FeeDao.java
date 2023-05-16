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
	@Query("insert into Fee (project, cc, rate, interval) select :project, :cc, :rate, :interval "
			+ "where not exists (select 1 from Fee where project = :project and cc = :cc)")
	public int insertProject(@Param("project") String project, @Param("cc") int cc, @Param("rate") double rate,
			@Param("interval") int interval);

	@Transactional
	@Modifying
	@Query("update Fee f set f.rate = :rate where f.project = :project and f.cc = :cc")
	public int updateRate(@Param("project") String project, @Param("cc") int cc, @Param("rate") double rate);

	@Transactional
	@Modifying
	@Query("update Fee f set f.interval = :interval where f.project = :project and f.cc = :cc")
	public int updateTime(@Param("project") String project, @Param("cc") int cc, @Param("interval") int interval);

	@Transactional
	@Modifying
	@Query("delete Fee f where f.project = :project and f.cc = :cc and f.rate = :rate")
	public int deleteProject(@Param("project") String project, @Param("cc") int cc, @Param("rate") double rate);

	public List<Fee> findByProjectOrderByInterval(String project);
}
