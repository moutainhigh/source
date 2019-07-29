package com.dchip.cloudparking.web.iRepository;

import com.dchip.cloudparking.web.model.po.CardLicensePlate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ICardLicensePlateRepository extends JpaRepository<CardLicensePlate, Integer> {

    @Query(value = "select * from card_license_plate where card_id =:cardId", nativeQuery = true)
    List<CardLicensePlate> findByCardId(@Param("cardId") Integer cardId);

    @Query(value = "delete from card_license_plate where card_id =:cardId and lisence_plate=:lisencePlate", nativeQuery = true)
    @Modifying
    Integer deleteCardLicensePlate(@Param("cardId") Integer cardId, @Param("lisencePlate") String lisencePlate);

    @Query(value = "select c.id,c.parking_id AS parkingId,c.card_code AS cardCode,c.car_owner_name AS carOwnerName, " +
    		" c.expiry_time AS expiryTime,c.max,c.type,clp.card_id AS cardId,  " +
            " clp.lisence_plate AS licensePlate,clp.is_main AS isMain  " +
            " from card c  " +
            " left join card_license_plate clp  " +
            " on (c.id = clp.card_id) " +
            " where c.parking_id =:parkingId and c.card_code =:cardCode limit 1", nativeQuery = true)
    Map<String, Object> findCardVoMap(@Param("parkingId") Integer parkingId, @Param("cardCode") String cardCode);

    @Query(value = "select card_code cardCode,id from card where card.parking_id=:parkingId and card.card_code in (:cardCodes)", nativeQuery = true)
    List<Map<String, Integer>> findCardCodeIdMaps(@Param("parkingId") Integer parkingId, @Param("cardCodes") List<Object> cardCodes);
    
    /**
     * 检查同一停车场下是否有相同的车牌号码存在
     * @author ZYY
     * @param parkingId
     * @param licensePlate
     * @return
     */
    @Query(value="SELECT COUNT(*) FROM card_license_plate clp  " + 
    		"LEFT JOIN card c ON(c.id=clp.card_id) " + 
    		"WHERE c.parking_id =:parkingId AND clp.lisence_plate =:licensePlate",nativeQuery=true)
    Integer checkCardLicensePlate(@Param("parkingId") String parkingId, @Param("licensePlate") String licensePlate);

    @Query(value="select id from card_license_plate  where lisence_plate =:licensePlate limit 1",nativeQuery=true)
    Integer findIdByLicensePlate(@Param("licensePlate") String licensePlate);
}
