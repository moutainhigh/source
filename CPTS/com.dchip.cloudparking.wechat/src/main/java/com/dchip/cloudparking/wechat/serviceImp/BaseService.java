package com.dchip.cloudparking.wechat.serviceImp;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.querydsl.jpa.impl.JPAQueryFactory;

@Transactional
public class BaseService {
	@Autowired
	JPAQueryFactory jpaQueryFactory ;
	
	
}
