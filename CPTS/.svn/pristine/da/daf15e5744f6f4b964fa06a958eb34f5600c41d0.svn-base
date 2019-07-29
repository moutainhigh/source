package com.dchip.cloudparking.web.serviceImp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.web.iRepository.IVersionRepository;
import com.dchip.cloudparking.web.iService.IVersionService;
import com.dchip.cloudparking.web.model.po.Version;
import com.dchip.cloudparking.web.po.qpo.QVersion;
import com.dchip.cloudparking.web.utils.QiniuUtil;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.Sort;
import com.qiniu.common.QiniuException;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

@Service
public class VersionServiceImp extends BaseService implements IVersionService {
	@Autowired
	private IVersionRepository versionRepository;

	@Override
	public Object getVersionList(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para) {
		List<Map<String, Object>> data = new ArrayList<>();
		QVersion qVersion = QVersion.version;

		Sort sort = new Sort(sortName, direction, qVersion);
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(!para.isEmpty()) {
			for (Map<String, Object> map : para) {
				//搜索参数
				if(map.get("VersionNum")!=null) {
					
					predicates.add(qVersion.versionCode.like("%"+map.get("VersionNum").toString()+"%"));
				}
			}
		}

		// 查询结果
		JPAQuery<Tuple> jPAQuery = jpaQueryFactory
				.select(qVersion.id, qVersion.versionCode, qVersion.address, qVersion.createTime, qVersion.type,
						qVersion.md5, qVersion.updateType, qVersion.remark)
				.from(qVersion)
				.where(predicates.toArray(new Predicate[predicates.size()]))
				.orderBy(sort.getOrderSpecifier()).offset(pageNum * pageSize).limit(pageSize);

		QueryResults<Tuple> queryResults = jPAQuery.fetchResults();
		// 将元组集合转成map集合
		for (Tuple tuple : queryResults.getResults()) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", tuple.get(qVersion.id));
			map.put("versionCode", tuple.get(qVersion.versionCode));
			map.put("address", tuple.get(qVersion.address));
			map.put("createTime", tuple.get(qVersion.createTime));
			map.put("type", tuple.get(qVersion.type));
			map.put("md5", tuple.get(qVersion.md5));
			map.put("updateType", tuple.get(qVersion.updateType));
			map.put("remark", tuple.get(qVersion.remark));
			data.add(map);
		}
		// jqgrid数据拼装
		Map<String, Object> result = new HashMap<>();
		result.put("content", data);// 添加主体数据
		result.put("totalElements", queryResults.getTotal());// 添加总条数
		result.put("code", 0);
		return result;
	}

	@Override
	public RetKit save(Version vo) {
		Version version = new Version();
		if (vo.getId() != 0) {
			version = versionRepository.findById(vo.getId()).get();
			try {
				System.out.println("------------deleteId"+version.getId());
				System.out.println("------------deleteAddress"+version.getAddress());
				QiniuUtil.delete(version.getAddress());
			} catch (QiniuException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		version.setAddress(vo.getAddress());
		version.setCreateTime(new Date());
		version.setRemark(vo.getRemark());
		version.setType(vo.getType());
		version.setUpdateType(vo.getUpdateType());
		version.setVersionCode(vo.getVersionCode());
		if (versionRepository.save(version) != null) {
			return RetKit.ok();
		} else {
			return RetKit.fail();
		}
	}

	@Override
	public RetKit delete(Integer versionId) {
		if (versionId == null) {
			return RetKit.fail();
		}
		Version version = versionRepository.findById(versionId).get();
		// 删除七牛云apk
		try {
			QiniuUtil.delete(version.getAddress());
		} catch (QiniuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			versionRepository.deleteById(versionId);
		}

		return RetKit.ok();
	}

	@Override
	public Map<String, Object> findLatestVersionMap(Integer type) {
		return versionRepository.findLatestVersionByTypeMap(type);
	}

}
