package com.dchip.cloudparking.web.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.ParkingRuleRelation;

@Repository
public interface IParkingRuleRelationRepository extends JpaRepository<ParkingRuleRelation, Integer> {
	
	@Query(value="SELECT * FROM parking_rule_relation WHERE parking_id =:parkingId",nativeQuery=true)
	ParkingRuleRelation findByParkingId(@Param("parkingId")Integer parkingId);
	
	@Query(value="SELECT * FROM parking_rule_relation WHERE parking_rule_id =:parkingRuleId",nativeQuery=true)
	ParkingRuleRelation findByParkingRuleId(@Param("parkingRuleId")Integer parkingRuleId);

}
