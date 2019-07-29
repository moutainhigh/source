package com.dchip.cloudparking.api.iRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.api.model.po.MemberRule;
import com.dchip.cloudparking.api.model.po.ParkingInfo;

@Repository
public interface IMemberRuleRepository extends JpaRepository<MemberRule, Integer>{

	/**
	 * 查找对应会员等级所需充值金额
	 * by 小梁
	 */
	@Query(value="SELECT * FROM member_rule WHERE grade = :grade", nativeQuery=true)
	MemberRule findMoneyByGrade(@Param("grade") Integer grade);

	
	/**
	 * 根据userId获取个人的会员等级信息
	 * sjh
	 */
	@Query(value = "SELECT mr.* from user u join member_rule mr on mr.id = u.member_id where u.id = :userId", nativeQuery = true)
	MemberRule findUserMemberRule(@Param("userId") Integer userId);
	
	/**
	 * 根据userId获取个人所有充值总额
	 * sjh
	 */
	@Query(value = "SELECT sum(in_money) from recharge_log where user_id = :userId and `status` = 1", nativeQuery = true)
	double findUserRechargeSum(@Param("userId") Integer userId);
	
	/**
	 * 获取所有充值优惠记录
	 */
	@Query(value ="select * from member_rule order by money asc",nativeQuery=true)
	List<MemberRule> findAllMemberRuleList();
}
