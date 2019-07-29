package com.dchip.cloudparking.api.serviceImp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dchip.cloudparking.api.constant.BaseConstant;
import com.dchip.cloudparking.api.iService.IHelpService;
import com.dchip.cloudparking.api.model.qpo.QHelp;
import com.dchip.cloudparking.api.utils.RetKit;
import com.querydsl.core.Tuple;

@Service
public class HelpServiceImp extends BaseService implements IHelpService{

	private QHelp qHelp =QHelp.help;
	
	@Override
	public RetKit getHelp() {
		Tuple tuple = jpaQueryFactory.select(qHelp.title,qHelp.content,qHelp.uploadTime)
				.from(qHelp)
				.where(qHelp.type.eq(BaseConstant.HelpType.help.getTypeValue()))
				.orderBy(qHelp.getPower().desc())
				.fetchFirst();
		Map<String, Object> map = new HashMap<>();
		if(tuple == null) {
			map.put("title", null);
			map.put("content", null);
			map.put("uploadTime", null);
		}else {
			map.put("title", tuple.get(qHelp.title));
			map.put("content", tuple.get(qHelp.content));
			map.put("uploadTime", tuple.get(qHelp.uploadTime));
		}
		return RetKit.okData(map);
		
	}
	
	@Override
	public RetKit aboutPoint() {
		
		Tuple tuple = jpaQueryFactory.select(qHelp.title,qHelp.content,qHelp.uploadTime)
				.from(qHelp)
				.where(qHelp.type.eq(BaseConstant.HelpType.Point.getTypeValue()))
				.orderBy(qHelp.getPower().desc())
				.fetchFirst();
		Map<String, Object> map = new HashMap<>();
		if(tuple == null) {
			map.put("title", null);
			map.put("content", null);
			map.put("uploadTime", null);
		}else {
			map.put("title", tuple.get(qHelp.title));
			map.put("content", tuple.get(qHelp.content));
			map.put("uploadTime", tuple.get(qHelp.uploadTime));
		}
		return RetKit.okData(map);
		
	}



}
