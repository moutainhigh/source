package com.dchip.cloudparking.web.iRepository;


import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.User;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
	
	/**
	 * 根据手机号码返回是否有这个用户名
	 * @param phone
	 * @return
	 */
	@Query(value="select count(*) from user u where u.type = 1 and u.phone = :phone", nativeQuery=true)
	Integer getUserLoginNameNum(@Param("phone") String phone);
	
	/**
	 * 根据手机号码和密码返回是否有这个用户
	 * @param phone
	 * @return
	 */
	@Query(value="select count(*) from user u where u.type = 1 and u.phone = :phone and u.pwd = :pwd", nativeQuery=true)
	Integer getUserLoginSuccessNum(@Param("phone") String phone, @Param("pwd") String pwd);
	
	@Query(value="SELECT count(*) as count ,date_format(create_time,'%Y/%c/%e') as date FROM user GROUP BY date ORDER BY create_time ASC", nativeQuery=true)
	List<Map<String, Object>> getChartData();
	
	@Query(value="SELECT   " + 
			"	a.id accountId,a.company_id as companyId, " + 
			"	a.user_name userName,   " + 
			"	a.role_id roleId,   " + 
			"	a.password,   " + 
			"	GROUP_CONCAT(r.name) role ,r.type as roleType   " + 
			"FROM account a    " + 
			"LEFT JOIN sys_role r ON (a.role_id = r.id)   " + 
			"WHERE   " + 
			"	a.user_name = :userName",nativeQuery=true)
	Map<String, Object> findUserAuthentic(@Param("userName")String userName);

	/**
	 * 根据车牌查询是否会员
	 * @param licensePlat
	 * @return
	 */
	@Query(value="SELECT u.* from `user` u where u.license_plat = :lincensePlat and u.member_id > 0",nativeQuery=true)
	User findUserBylicensePlat(@Param("lincensePlat") String licensePlat);
	
}
