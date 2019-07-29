package com.dchip.cloudparking.web.iRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.MainControl;

@Repository
public interface IMainControlRepository extends JpaRepository<MainControl, Integer> {
	
	@Query(value = "SELECT COUNT(*) FROM main_control WHERE parking_id =:parkingId and status <> -2",nativeQuery=true)
	Integer findMainControlByParkingId(@Param("parkingId") Integer parkingId);
	
	/**
	 * 查询某种类型的主控板列表
	 */
	@Query(value="SELECT * from main_control where type = :type ",nativeQuery = true)
	List<MainControl> findMainControlsByType(@Param("type") Integer type);

	/**
	 * 根据停车场id返回主控板信息
	 * @param parkingId
	 * @return
	 */
	@Query(value="SELECT mc.* from main_control mc where mc.parking_id = :parkingId",nativeQuery=true)
	List<MainControl> findMainControlListByParkingId(@Param("parkingId") Integer parkingId);

	@Query(value = "SELECT * from main_control mc where mc.mac = :mainControlMac",nativeQuery=true)
	Optional<MainControl> findByMainControlMac(@Param("mainControlMac")String mainControlMac);

	@Query(value="SELECT * from main_control mc where mc.act_to like CONCAT('%',:actTo,'%') ",nativeQuery=true)
	List<MainControl> findMainControl(@Param("actTo")Integer actTo);
	
}
