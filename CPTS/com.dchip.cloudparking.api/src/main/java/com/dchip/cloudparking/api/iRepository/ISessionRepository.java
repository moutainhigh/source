package com.dchip.cloudparking.api.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.dchip.cloudparking.api.model.po.Session;

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

	/**
	 * 停车场用户查看accessToken是否失效
	 * @param token
	 * @param userType
	 * @return
	 */
	@Query(value = "select * from session where token =:token and type=:userType", nativeQuery = true)
    Session findSessionByTokenAndType(@Param("token") String token,@Param("userType")  int userType);


	/**
	 * 根据用户id 和用户类型 查找session
	 * @param userId
	 * @param userType
	 * @return
	 */
	@Query(value = "select * from session where user_id=:userId and type=:userType", nativeQuery = true)
	Session findSessionByUserId(@Param("userId") Integer userId,@Param("userType") Integer userType);
}
