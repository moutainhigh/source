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
public interface ICardRepository extends JpaRepository<Card, Integer> {
	
	/**
	 * 根据车牌显示月卡的停车场信息
	 * @param licensePlat
	 * @return
	 */
	@Query(value="select if(datediff(c.expiry_time,now()) > 0, datediff(c.expiry_time,now()), 0) as surplusDay,"
			+ "p.park_name,p.province_name,p.city_name,  " + 
			" p.area_name,p.month_free,p.location,c.id,c.parking_id,clp.more_car_lisence_plate,c.is_fixed_space from card c "
			+ " LEFT JOIN parking p on c.parking_id = p.id "
			+ " LEFT JOIN card_license_plate clp on clp.card_id = c.id where clp.lisence_plate=:licensePlat GROUP BY p.id", nativeQuery = true )
	List<Map<String, Object>> findCardAndParkingInfoByLicensePlat(@Param("licensePlat") String licensePlat);
	
	@Query(value="select if(datediff(c.expiry_time,now()) > 0, datediff(c.expiry_time,now()), 0) as surplusDay,"
			+ "p.park_name,p.province_name,p.city_name,  "  
			+ " p.area_name,p.month_free,p.location,c.id,c.parking_id,c.is_fixed_space,clp.more_car_lisence_plate from card c "
			+ " LEFT JOIN parking p on c.parking_id = p.id "
			+ " LEFT JOIN card_license_plate clp on clp.card_id = c.id where clp.lisence_plate=:licensePlat and c.is_fixed_space=1 GROUP BY p.id", nativeQuery = true )
	List<Map<String, Object>> findCardAndParkingInfoAndisFixedByLicensePlat(@Param("licensePlat") String licensePlat);
	
	/**
	 * 根据车牌显示月卡的停车场信息
	 * @param parkingId 
	 * @param
	 * @return
	 */
	@Query(value="select count(*) from card c\n" +
			     "LEFT JOIN card_license_plate clp on clp.card_id = c.id\n" +
			     "where clp.lisence_plate=:licensePlate and c.parking_id=:parkingId and c.is_fixed_space = 1", nativeQuery = true )
	Integer isFixedByLicensePlate(@Param("licensePlate")String licensePlate, @Param("parkingId")Integer parkingId);


	
}
