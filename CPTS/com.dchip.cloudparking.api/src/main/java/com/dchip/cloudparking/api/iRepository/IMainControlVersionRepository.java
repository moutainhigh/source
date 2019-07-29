package com.dchip.cloudparking.api.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dchip.cloudparking.api.model.po.MainControlVersion;

public interface IMainControlVersionRepository extends JpaRepository<MainControlVersion, Integer> {
	/**
	 * 根据mac查看版本信息
	 */
	@Query(value="select m.* from main_control_version m where m.mac = :mac",nativeQuery = true)
	MainControlVersion findMainControlVersionByMac(@Param("mac")String mac);
}
