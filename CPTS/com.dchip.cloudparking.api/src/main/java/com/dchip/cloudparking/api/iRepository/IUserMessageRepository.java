package com.dchip.cloudparking.api.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.api.model.po.UserMessage;

@Repository
public interface IUserMessageRepository extends JpaRepository<UserMessage, Integer> {

	@Query(value="SELECT * FROM user_message WHERE user_id = :userId LIMIT 1",nativeQuery=true)
	UserMessage findByUserId(@Param("userId") Integer userId);
}
