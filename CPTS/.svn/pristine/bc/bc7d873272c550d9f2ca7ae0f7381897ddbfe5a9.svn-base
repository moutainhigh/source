package com.dchip.cloudparking.api.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.api.model.po.Stock;

@Repository
public interface IStockRepository extends JpaRepository<Stock, Integer> {

	@Query(value="SELECT * from stock WHERE parking_id = :parkingId limit 1",nativeQuery=true)
	Stock findByParkingId(@Param("parkingId")Integer parkingId);
}
