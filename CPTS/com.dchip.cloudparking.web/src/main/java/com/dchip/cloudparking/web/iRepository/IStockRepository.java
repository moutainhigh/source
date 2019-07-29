package com.dchip.cloudparking.web.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.Stock;

@Repository
public interface IStockRepository extends JpaRepository<Stock, Integer> {
	
	@Query(value="SELECT count(*) FROM stock WHERE parking_id =:parkingId",nativeQuery=true)
	Integer findByParkingId(@Param("parkingId") Integer parkingId);

	@Query(value="SELECT * FROM stock WHERE parking_id =:parkingId",nativeQuery=true)
	Stock findAllByParkingId(@Param("parkingId") Integer parkingId);
}
