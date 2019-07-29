package com.dchip.cloudparking.api.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.api.model.po.MainControl;

@Repository
public interface IMainControlRepository extends JpaRepository<MainControl, Integer> {
	/**
	 * 查询主控板是否存在
	 * @param mac
	 * @return
	 */
	@Query(value="select * from main_control m where m.mac = :mac",nativeQuery=true)
	MainControl findMainControl(@Param("mac") String mac);
}
