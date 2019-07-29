package com.dchip.cloudparking.web.iRepository;

import com.dchip.cloudparking.web.model.po.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface IActivityRepository extends JpaRepository<Activity, Integer>{

//    @Query(value = "update activity set status = :status where id =:activityId",nativeQuery = true)
//    @Modifying
//    Integer softlyDel(@Param("status") Integer status,@Param("activityId")  Integer activityId);
	/**
	 * 判断是否有这个用户名
	 * @param activityName
	 * @return
	 */
	@Query(value="select count(*) from activity a where a.remark = :activityName", nativeQuery = true)
	public Integer getActivityNameNum (@Param("activityName") String activityName);

	@Modifying
	@Transactional
	//在调用的地方必须加事务，没有事务不能正常执行
	@Query(value="update activity set status=:status where id=:activityId", nativeQuery = true)
    int changeStatus(@Param("status") Integer status,@Param("activityId") Integer activityId);

	@Query(value="select status from activity a where a.remark =:activityName ", nativeQuery = true)
	public Integer findByName(@Param("activityName") String activityName);

	@Query(value="SELECT * FROM activity a where a.status = 0 and a.remark =:activityRemark ", nativeQuery = true)
	public Activity findStatusByRemark(@Param("activityRemark")String activityRemark);
}
