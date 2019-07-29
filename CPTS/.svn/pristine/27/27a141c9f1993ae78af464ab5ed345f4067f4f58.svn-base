package com.dchip.cloudparking.web.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.MonthlyCard;

@Repository
public interface IMonthlyCardRepository extends JpaRepository<MonthlyCard, Integer>{
	
	@Query(value="SELECT COUNT(*) FROM monthly_card A left join monthly_card_license_plate B on A.id = B.card_id WHERE B.license_plate =:licensePlate AND A.parking_id =:parkingId",nativeQuery=true)
	Integer queryRepetition(@Param("licensePlate") String licensePlate,@Param("parkingId") Integer parkingId);
}
