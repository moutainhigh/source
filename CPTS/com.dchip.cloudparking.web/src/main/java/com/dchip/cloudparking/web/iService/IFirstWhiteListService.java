package com.dchip.cloudparking.web.iService;

import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.model.po.FirstWhiteList;
import com.dchip.cloudparking.web.utils.RetKit;

public interface IFirstWhiteListService {

	Object getFirstWhiteList(Integer pageSize, Integer pageNum, String sortName, String direction, List<Map<String, Object>> para);

	RetKit save(Integer firstWhiteListId,Integer licensePlateTypeId);

	RetKit delete(Integer firstWhiteListId);

	FirstWhiteList findByLicensePlateId(Integer licensePlateTypeId);

	Integer findFirstWhiteList(Integer licensePlateTypeId);
}
