package com.dchip.cloudparking.web.iService;

import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.model.po.Company;
import com.dchip.cloudparking.web.model.vo.CompanyVo;
import com.dchip.cloudparking.web.utils.RetKit;

public interface ICompanyManageService {

	Object getCompanyManageList(Integer pageSize, Integer pageNum, String sortName, String direction,
			List<Map<String, Object>> para);

	RetKit delete(Integer companyId, Integer companyBalanceWayId);

	RetKit save(CompanyVo companyVo);
	
	List<Company> findAllCompany();

	Company findByName(String companyName);
}
