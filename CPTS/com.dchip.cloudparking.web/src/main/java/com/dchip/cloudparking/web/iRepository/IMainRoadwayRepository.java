package com.dchip.cloudparking.web.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.MainRoadway;

@Repository
public interface IMainRoadwayRepository extends JpaRepository<MainRoadway, Integer> {
	@Query(value="select * from main_roadway where roadway_id = :roadwayId limit 1",nativeQuery=true)
	MainRoadway findByRoadwayId(@Param("roadwayId")Integer roadwayId);
}
