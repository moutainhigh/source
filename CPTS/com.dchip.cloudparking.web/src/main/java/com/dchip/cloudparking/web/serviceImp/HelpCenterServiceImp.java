package com.dchip.cloudparking.web.serviceImp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.web.iRepository.IHelpRepository;
import com.dchip.cloudparking.web.iService.IHelpCenterService;
import com.dchip.cloudparking.web.model.po.Help;
import com.dchip.cloudparking.web.model.vo.HelpVo;
import com.dchip.cloudparking.web.po.qpo.QHelp;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.Sort;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;

@Service
public class HelpCenterServiceImp extends BaseService implements IHelpCenterService {
	@Autowired 
	public IHelpRepository helpRepository;

	@Override
	public Object getHelpCenterList(Integer pageSize, Integer pageNum, String sortName, String direction,List<Map<String, Object>> para) {
		List<Map<String, Object>> data = new ArrayList<>();	
		QHelp qHelp = QHelp.help;//帮助中心
//		/**
//		 * 查询条件的谓语集合(where条件)
//		 */
//		List<Predicate> predicates = new ArrayList<>();	
//		if(!para.isEmpty()) {
//			for (Map<String, Object> map : para) {
//				//搜索参数
//				if(map.get("searchLicensePlate")!=null) {
//					//停车场名称
//					predicates.add(qParkingInfo.licensePlate.like("%"+map.get("searchLicensePlate").toString()+"%"));
//				}
//			}
//		}
		//动态排序,需要使用自定义的Sort类
		Sort sort = null;
		switch (sortName) {
		case "helpId":		
			sort = new Sort("id",direction.toString(),qHelp);
			break;
		default:
			sort = new Sort(sortName,direction,qHelp);
			break;
		}	
		
		//查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.select(qHelp.id,qHelp.power,qHelp.title,qHelp.content,qHelp.type,qHelp.uploadTime)
				.from(qHelp)
				.orderBy(sort.getOrderSpecifier())
				.limit(pageSize).offset(pageNum*pageSize);
//				.where(predicates.toArray(new Predicate[predicates.size()]))
		
		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		//将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("helpId", tuple.get(qHelp.id));
			map.put("power", tuple.get(qHelp.power));
			map.put("title", tuple.get(qHelp.title));
			map.put("content", tuple.get(qHelp.content));
			map.put("type", tuple.get(qHelp.type));
			map.put("uploadTime", tuple.get(qHelp.uploadTime));
			data.add(map);
		}
		
		//jqgrid数据拼装
		Map<String, Object> result = new HashMap<>();
		result.put("content", data);//添加主体数据
		result.put("totalElements", queryResults.getTotal());//添加总条数
		result.put("code", 0);
		return result;
	}

	@Override
	public RetKit save(HelpVo helpVo) {
		Help help = new Help();
		if (helpVo.getHelpId() != 0) {
			Optional<Help> helpOptional = helpRepository.findById(helpVo.getHelpId());
			if (helpOptional.isPresent()) {
				help = helpOptional.get();
			}
		}
		help.setContent(helpVo.getContent());
		help.setPower(helpVo.getPower());
		help.setTitle(helpVo.getTitle());
		help.setType(helpVo.getType());
		help.setUploadTime(new Date());
		helpRepository.save(help);
		return RetKit.ok("保存成功");
	}

	@Override
	public RetKit del(Integer helpId) {
		helpRepository.deleteById(helpId);
		return RetKit.ok("删除成功");
	}

}
