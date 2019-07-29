package com.dchip.cloudparking.api.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.api.model.po.Finance;

@Repository
public interface IFinanceRepository extends JpaRepository<Finance, Integer>{

}
