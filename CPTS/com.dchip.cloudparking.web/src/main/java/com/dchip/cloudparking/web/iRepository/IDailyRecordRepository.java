package com.dchip.cloudparking.web.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.DailyRecord;

@Repository
public interface IDailyRecordRepository extends JpaRepository<DailyRecord, Integer> {

}
