package com.dchip.cloudparking.api.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.api.model.po.Version;

@Repository
public interface IVersionRepository extends JpaRepository<Version, Integer> {
	/**
	 * 获取某类最新安装包
	 * 
	 * @param type
	 * @return
	 */
	@Query(value = "select v.* from version v where v.type = :type ORDER BY v.create_time desc LIMIT 1 ", nativeQuery = true)
	Version findLatestVersionByType(@Param("type") Integer type);
}
