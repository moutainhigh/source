package com.dchip.cloudparking.web.iRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.CashApplyRecord;

@Repository
public interface ICashApplyRecordRepository extends JpaRepository<CashApplyRecord, Integer>{
	
	@Query(value="SELECT c.`name` FROM cash_apply_record car  " + 
			"LEFT JOIN company c ON (c.id = car.company_id) GROUP BY c.`name` ",nativeQuery=true)
	List<String> findCompanyName();
}
