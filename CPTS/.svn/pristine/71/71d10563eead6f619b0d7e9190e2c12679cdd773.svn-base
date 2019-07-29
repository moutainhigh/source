package com.dchip.cloudparking.web.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.ParkingConcession;

@Repository
public interface IParkingConcessionRepository extends JpaRepository<ParkingConcession, Integer>{

    @Query(value = "update parking_concession set status=:status where id=:id",nativeQuery = true)
    @Modifying
    Integer changeStatus(@Param("status")Integer status,@Param("id") Integer id);

//	@Query(value = "select * from p where card_id =:cardId",nativeQuery=true)
//	List<ParkingConcession> findByCardId(@Param("cardId") Integer cardId);
	
	

}
