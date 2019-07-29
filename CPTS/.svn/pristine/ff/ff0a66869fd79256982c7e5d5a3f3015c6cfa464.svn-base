package com.dchip.cloudparking.api.iRepository;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.dchip.cloudparking.api.model.po.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

	/**
	 * 查询普通用户状态 是否禁用
	 * 
	 * @param phone
	 * @return
	 */
	@Query(value = "select u.state from user u where u.license_plat = :licensePlat and u.type = 0", nativeQuery = true)
	String findUserByState(@Param("licensePlat") String licensePlat);

	/**
	 * 根据车牌号返回用户信息
	 * 
	 * @param licensePlat
	 * @return
	 */
	@Query(value = "select u.* from user u where u.license_plat = :licensePlat and u.type = 0", nativeQuery = true)
	User findUserDetailsByLicensePlat(@Param("licensePlat") String licensePlat);
	
	/**
	 * 获取总积分
	 * @param phone
	 * @return
	 */
	@Query(value="SELECT IFNULL(SUM(score),0) as totalScore FROM point_record WHERE user_id=:userId", nativeQuery=true)
	Map<String, Object> findTotalScore(@Param("userId") String userId);
	
	/**
	 * 获取积分明细
	 * @param phone
	 * @return
	 */
	@Query(value="SELECT\r\n" + 
			"	pr.score,\r\n" + 
			"	pr.remark,\r\n" + 
			"	pr.create_time AS create_time\r\n" + 
			"FROM\r\n" + 
			"	point_record pr\r\n" + 
			"WHERE\r\n" + 
			"	user_id = :userId\r\n" + 
			"AND YEAR (pr.create_time) = :year\r\n" + 
			"AND MONTH (pr.create_time) = :month", nativeQuery=true)
	List<Map<String, Object>> findScoreDetail(@Param("userId") String userId , @Param("year")String year,@Param("month")String month);

	/**
	 * 获取总积分
	 * @param phone
	 * @return
	 */
	@Query(value="SELECT\r\n" + 
			"	IFNULL(SUM(pr.score),0)  " + 
			"FROM\r\n" + 
			"	point_record pr\r\n" + 
			"WHERE\r\n" + 
			"	user_id = :userId\r\n" + 
			"AND YEAR (pr.create_time) = :year\r\n" + 
			"AND MONTH (pr.create_time) = :month", nativeQuery=true)
	Integer findTotalScore(@Param("userId") String userId , @Param("year")String year,@Param("month")String month);

	/**
	 * 根据userId，返回user对象
	 * 
	 * @param Id
	 * @return
	 */
	@Query(value="select u.* from User u where u.id = :userId",nativeQuery=true)
	User findUserById(@Param("userId") Integer userId);

	/**
	 * @param Id
	 * @return
	 */
	@Query(value = "update user set balance = :balance where id =:userId ", nativeQuery = true)
	@Modifying
	@Transactional
	void changeBalance(@Param("userId") Integer userId, @Param("balance") BigDecimal balance);
	
	/**
	 * 查看优惠
	 */
	@Query(value = "SELECT " + 
			"	uc.id AS userCouponId, " + 
			"	a.discount, " + 
			"	a.type " + 
			"FROM " + 
			"	user_coupon uc " + 
			"LEFT JOIN coupon c ON (c.id = uc.coupon_id) " + 
			"LEFT JOIN activity a ON (a.coupon_id = c.id) " + 
			"WHERE " + 
			"	uc.`status` = 0 " + 
			"AND c.`status` = 0 " + 
			"AND " + 
			"IF ( " + 
			"	a.effective_type = 2, " + 
			"	a.effective_time > curdate() , " + 
			"	1 = 1 " + 
			") " + 
			"AND uc.user_id = :userId", nativeQuery = true)
	List<Map<String, Object>> checkDiscounts(@Param("userId") Object userId);
	
	/**
	 * 通过用户输入的车牌号码查找该车牌是否已注册
	 * by zyy
	 * @param licensePlat
	 * @return
	 */
	@Query(value = "SELECT count(*) FROM user WHERE license_plat = :licensePlat ", nativeQuery = true)
	Integer findByLicensePlate(@Param("licensePlat") String licensePlat);

	/**
	 * 通过用户输入的车牌号码查找该用户id
	 * by hrd
	 * @param licensePlat
	 * @return
	 */
	@Query(value = "SELECT id FROM user WHERE license_plat = :licensePlat limit 1", nativeQuery = true)
	Integer findIdByLicensePlate(@Param("licensePlat") String licensePlat);
	
	/**
	 *更新用户会员等级
	 */
	@Query(value = "update user set member_id = :memberId  where id = :userId", nativeQuery = true)
	@Modifying
	@Transactional
	void updateUserMember(@Param("userId") Integer userId, @Param("memberId") Integer memberId);
	/**
	 * 通过车牌号码查找该用户
	 * @param carNum
	 * @return
	 */
	@Query(value = "SELECT u.* FROM `user` u WHERE u.license_plat =:carNum ", nativeQuery = true)
	User findUserByCarNum(@Param("carNum") String carNum);
	
	
}
