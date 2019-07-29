package com.dchip.cloudparking.wechat.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.wechat.model.po.Card;

@Repository
public interface ICardRepository extends JpaRepository<Card, Integer>{

	/**
	 * 根据车牌找到月卡信息
	 * @param lisencePlate
	 * @return
	 */
    @Query(value="select c.* from card c where c.id = "
    		+ "(select clp.card_id from card_license_plate clp where clp.lisence_plate = :lisencePlate)",nativeQuery=true)
    Card findCardByLisencePlate(@Param("lisencePlate") String lisencePlate);
    
    /**
	 * 根据车牌找到月卡信息
	 * @param lisencePlate
	 * @return
	 */
    @Query(value="select c.* from card c where c.id = "
    		+ "(select clp.card_id from card_license_plate clp "
    		+ "where clp.lisence_plate = :lisencePlate) and c.parking_id = :parkingId",nativeQuery=true)
    Card findCardByLisencePlateAndParkingId(@Param("lisencePlate") String lisencePlate, @Param("parkingId") String parkingId);
}
