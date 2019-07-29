package com.dchip.cloudparking.web.iRepository;

import com.dchip.cloudparking.web.model.po.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer>{

	/**
	 * 判断是否有这个用户名
	 * @param loginName
	 * @return
	 */
	@Query(value="select count(*) from account a where a.status = 1 and a.user_name = :loginName", nativeQuery = true)
	public Integer getUserLoginNameNum (@Param("loginName") String loginName);
	
	/**
	 * 校验是否用户名密码合法
	 * @param loginName
	 * @param pwdmd
	 * @return
	 */
	@Query(value="select count(*) from account a where a.status = 1 and a.user_name = :loginName and a.password = :pwdmd", nativeQuery = true)
	public Integer getUserLoginSuccessNum(@Param("loginName") String loginName, @Param("pwdmd") String pwdmd);

	@Query(value="update account set status = 0 where id = :accountId", nativeQuery = true)
	@Modifying
    Integer softlyDel(@Param("accountId") Integer accountId);

	@Query(value="select * from account where user_name = :userName limit 1", nativeQuery = true)
	Account findByUserName(@Param("userName") String userName);
	
	@Query(value="select * from account a where a.status = 1 and a.user_name = :userName limit 1", nativeQuery = true)
	Account findByStatusAndUserName(@Param("userName") String userName);

	@Query(value="select * from account a where a.status = -2 and a.user_name =:userName limit 1", nativeQuery = true)
	Account checkUserName(@Param("userName")String parkingUserName);
}
