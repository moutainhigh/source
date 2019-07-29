package com.dchip.cloudparking.web.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.CompanyBalanceWay;

@Repository
public interface ICompanyBalanceWayRepository extends JpaRepository<CompanyBalanceWay, Integer>{
	
	/**
	 * 寻找默认的账号
	 * by 小梁
	 */
	@Query(value = "select * from company_balance_way where company_id = :companyId and is_first = 1 limit 1", nativeQuery = true)
	CompanyBalanceWay findIsFirst(@Param("companyId") Integer companyId);
	
	/**
	 * 寻找最新创建
	 * by 小梁
	 */
	@Query(value = "select * from company_balance_way where company_id = :companyId order by create_time desc limit 1", nativeQuery = true)
	CompanyBalanceWay findNewWay(@Param("companyId") Integer companyId);

}
