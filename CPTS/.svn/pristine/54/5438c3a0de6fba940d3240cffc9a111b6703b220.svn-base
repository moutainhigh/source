package com.dchip.cloudparking.api.iRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.api.model.po.Card;

@Repository
public interface IMonthlyCardRepository extends JpaRepository<Card, Integer>{
	
	@Query(value=" SELECT if(datediff(mc.expiry_time,now()) > 0, datediff(mc.expiry_time,now()), 0) as surplusDay,p.park_name,p.province_name,p.city_name,"
			   + " p.area_name,p.location,mc.id FROM monthly_card mc " + 
			     " LEFT JOIN parking p ON (p.id=mc.parking_id) " + 
			     " WHERE mc.license_plate =:licensePlat ", nativeQuery = true )
	List<Map<String, Object>> findMonthlyCardAndParkingInfo(@Param("licensePlat") String licensePlat);

	
	@Query(value=" SELECT * from card where c.id = :cardId ", nativeQuery = true )
	Optional<Card> findIsFixedSpaceById(@Param("cardId")String cardId);

}
