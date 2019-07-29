package com.dchip.cloudparking.web.iRepository;


import com.dchip.cloudparking.web.model.po.Card;
import com.dchip.cloudparking.web.model.vo.CardVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICardRepository extends JpaRepository<Card, Integer>{

    @Query(value = "select id from card where card_code =:cardCode limit 1",nativeQuery=true)
    Integer queryCardIdPlateByCardCode(@Param("cardCode") String cardCode);

    //原生SQL实现更新方法接口
    @Query(value = "insert into card(card_code) values(:cardCode)", nativeQuery = true)
    @Modifying
    void saveWithCode(@Param("cardCode") String cardCode);

    @Query(value = "select * from card where card_code =:cardCode limit 1",nativeQuery=true)
    Card findByCardCode(@Param("cardCode") String cardCode);

    @Query(value =  "select\n" +
                    "A.*,\n" +
                    "B.card_id AS cardId,\n" +
                    "B.lisence_plate AS licensePlate,\n" +
                    "B.is_main AS isMain\n" +
                    "from card A\n" +
                    "left join card_license_plate B\n" +
                    "on A.id = B.card_id\n" +
                    "where A.card_code = :cardCode limit 1",nativeQuery=true)
    CardVo findByVoCardCode(@Param("cardCode") String cardCode);

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
