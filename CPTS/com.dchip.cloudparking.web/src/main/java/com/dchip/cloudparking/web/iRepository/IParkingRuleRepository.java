package com.dchip.cloudparking.web.iRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.ParkingRule;

@Repository
public interface IParkingRuleRepository extends JpaRepository<ParkingRule, Integer>{
	
	@Query(value="SELECT pr.* FROM parking_rule pr " + 
			"LEFT JOIN parking_rule_relation prr ON (prr.parking_rule_id = pr.id) " + 
			"WHERE prr.parking_id =:parkingId",nativeQuery=true)
	List<ParkingRule> findParkingRuleDataByPId(@Param("parkingId")Integer parkingId);

}
