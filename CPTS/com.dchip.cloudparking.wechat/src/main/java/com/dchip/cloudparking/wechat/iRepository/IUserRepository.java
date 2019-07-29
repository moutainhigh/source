package com.dchip.cloudparking.wechat.iRepository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.wechat.model.po.User;


@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
	
	/**
	 * 查询openid是否存在，不存在返回null
	 * @param openid
	 * @return
	 */
	@Query("select openid from User where openid = ?1")
	String findByOpenid(String openid);
	
	/**
	 * 根据openid，返回user对象
	 * @param openid
	 * @return
	 */
	@Query("select u from User u where u.openid = ?1")
	User findUserByOpenid(String openid);
	
	/**
	 * 根据车牌查询是否会员
	 * @param licensePlat
	 * @return
	 */
	@Query(value="SELECT u.* from `user` u where u.license_plat = :lincensePlat and u.member_id > 0",nativeQuery=true)
	User findUserBylicensePlat(@Param("lincensePlat") String licensePlat);
	
}
