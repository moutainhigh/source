package com.dchip.cloudparking.web.iRepository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.web.model.po.Message;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Integer>{

	@Query(value="SELECT\r\n" + 
			"	m.id,\r\n" + 
			"	m.title,\r\n" + 
			"	m.content,\r\n" + 
			"\r\n" + 
			"IF (\r\n" + 
			"	LOCATE(\r\n" + 
			"		:userId,\r\n" + 
			"		(\r\n" + 
			"			SELECT\r\n" + 
			"				has_read\r\n" + 
			"			FROM\r\n" + 
			"				user_message\r\n" + 
			"			WHERE\r\n" + 
			"				user_id = 1\r\n" + 
			"			LIMIT 1\r\n" + 
			"		)\r\n" + 
			"	) > 0,\r\n" + 
			"	'已读',\r\n" + 
			"	'未读'\r\n" + 
			") AS has_read\r\n" + 
			"FROM\r\n" + 
			"	message m\r\n" + 
			"WHERE\r\n" + 
			"	m.target = 1\r\n" + 
			"OR (\r\n" + 
			"	m.target = 2\r\n" + 
			"	AND LOCATE(:userId, m.target) > 0\r\n" + 
			")",nativeQuery=true)
	 List<Map<String, Object>> getMessage(@Param("userId")String userId);
}
