package com.dchip.cloudparking.api.iRepository;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.api.model.po.ParkingUser;

@Repository
public interface IParkingUserRepository extends JpaRepository<ParkingUser, Integer>{
	
	/**
	 * 判断是否有这个用户名
	 * @param loginName
	 * @return
	 */
	@Query(value="select count(*) from parking_user pu where pu.user_name = :loginName", nativeQuery = true)
	public Integer getUserLoginNameNum (@Param("loginName") String loginName);
	
	/**
	 * 校验是否用户名密码合法
	 * @param loginName
	 * @param pwdmd
	 * @return
	 */
	@Query(value="select count(*) from parking_user pu where pu.user_name = :loginName and pu.password = :pwdmd", nativeQuery = true)
	public Integer getUserLoginSuccessNum(@Param("loginName") String loginName, @Param("pwdmd") String pwdmd);

	@Query(value="select pu.*,p.park_name from parking_user pu,parking p " +
			"where (p.id=pu.parking_id)  " +
			"and pu.user_name = :userName and pu.password=:pwd limit 1", nativeQuery = true)
	Map<String, Object> findByUserName(@Param("userName") String userName,@Param("pwd") String pwd);
	
	
	/**
	 * 查询某个商户是否正常
	 * @return
	 */
	@Query(value="select * from parking_user pu where pu.status = 1 and id = :id", nativeQuery = true)
	public ParkingUser findParkingUser(@Param("id") Integer id);
	
	
}
