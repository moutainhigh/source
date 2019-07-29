package com.dchip.cloudparking.api.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.api.model.po.LockCommond;

@Repository
public interface ILockCommondRepository extends JpaRepository<LockCommond, Integer> {

	@Query(value="SELECT lc.* FROM lock_commond lc WHERE lc.ground_lock_id =:groundLockId AND lc.type =:type",nativeQuery=true)
	LockCommond findCommond(@Param("groundLockId")int groundLockId, @Param("type")String type);

}
