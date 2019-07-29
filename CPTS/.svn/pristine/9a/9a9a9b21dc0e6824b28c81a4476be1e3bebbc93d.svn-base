package com.dchip.cloudparking.web.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.Session;

@Repository
public interface ISessionRepository extends JpaRepository<Session, Integer> {
	
	/**
	 * 根据用户id查找session
	 * @param userId
	 * @return
	 */
	@Query(value = "select * from session where user_id =:userId ", nativeQuery = true)
	Session findSessionByUserId(@Param("userId") Integer userId);
		
	/**
	 * 查看accessToken是否失效
	 * @param token
	 * @return
	 */
	@Query(value = "select * from session where token =:token ", nativeQuery = true)
	Session findSessionByToken(@Param("token") String token);

	@Query(value = "delete from session where type=:userType and user_id =:userId ", nativeQuery = true)
	@Modifying
    int delParkingUserSession(@Param("userId")String userId,@Param("userType") Integer userType);
}
