package com.dchip.cloudparking.api.iRepository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.api.model.po.Message;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Integer>{

	@Query(value="SELECT " + 
			"	m.id, " + 
			"	m.title, " + 
			"	m.content,m.create_time as createTime, " + 
			"IF ( " + 
			"	LOCATE( " + 
			"		m.id, " + 
			"		( " + 
			"			SELECT " + 
			"				um.has_read " + 
			"			FROM " + 
			"				user_message um " + 
			"			WHERE " + 
			"				um.user_id = :userId " + 
			"			LIMIT 1 " + 
			"		) " + 
			"	) > 0, " + 
			"	1, " + 
			"	0 " + 
			") AS has_read " + 
			"FROM " + 
			"	message m " + 
			"WHERE " + 
			"	m.type = 1 " + 
			"OR ( " + 
			"	m.type = 2 " + 
			"	AND LOCATE(:userId, m.target) > 0 " + 
			") ORDER BY m.create_time DESC LIMIT :pageSize OFFSET :offset",nativeQuery=true)
	 List<Map<String, Object>> findMessage(@Param("userId")Object userId,@Param("pageSize")Object pageSize,@Param("offset")Object offset);

	@Query(value="SELECT count(*) FROM message m WHERE m.type = 1 OR (m.type = 2 AND LOCATE(:userId, m.target) > 0 )"
			,nativeQuery=true)
	 Integer findMessageCount(@Param("userId")Object userId);

}
