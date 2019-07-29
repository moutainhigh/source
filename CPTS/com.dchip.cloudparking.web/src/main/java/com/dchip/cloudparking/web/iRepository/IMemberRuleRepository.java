package com.dchip.cloudparking.web.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.MemberRule;

@Repository
public interface IMemberRuleRepository extends JpaRepository<MemberRule, Integer>{

	/**
	 * 查找对应会员等级所需充值金额
	 * by 小梁
	 */
	@Query(value="SELECT * FROM member_rule WHERE grade = :grade", nativeQuery=true)
	MemberRule findMoneyByGrade(@Param("grade") Integer grade);

}
