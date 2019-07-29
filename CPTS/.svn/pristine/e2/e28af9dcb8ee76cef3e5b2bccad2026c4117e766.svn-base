package com.dchip.cloudparking.web.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.ParkingUser;

@Repository
public interface IParkingUserRepository extends JpaRepository<ParkingUser, Integer> {
	
	/*@Query(value="select count(*) from parking_user pu where pu.status = 1 and a.parkingVo = :parkingVo", nativeQuery = true)
	public Integer getUserLoginNameNum (@Param("parkingVo") String parkingVo);*/

	@Query(value="select count(*) from parking_user pu where pu.status=1 and pu.user_name =:userName", nativeQuery = true)
	Integer findByUserName(@Param("userName")String userName);
	
	/**
	 * 根据角色名称返回角色数量（可判断重名)
	 * @param roleName
	 * @return
	 */
	@Query(value="select count(*) from parking_user pu where pu.user_Name = :userName ", nativeQuery = true)
	Integer getUserNumByRoleName(@Param("userName") String userName);
	
	@Query(value="select pu.* from parking_user pu where pu.user_name = :userName limit 1", nativeQuery = true)
	public ParkingUser findParkingUserByUserName(@Param("userName") String userName);

	@Query(value="select * from parking_user pu where pu.status = 1 and pu.user_name = :userName limit 1", nativeQuery = true)
	ParkingUser findByStatusAndUserName(@Param("userName")String userName);
	
	@Query(value="select * from parking_user pu where pu.status = -2 and pu.user_name = :userName limit 1", nativeQuery = true)
	ParkingUser findParkingUser(@Param("userName")String userName);
	
	
}
