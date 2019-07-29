package com.dchip.cloudparking.web.iService;

import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.web.model.vo.HelpVo;
import com.dchip.cloudparking.web.utils.RetKit;

public interface IHelpCenterService {

	Object getHelpCenterList(Integer pageSize, Integer pageNum, String sortName, String direction, List<Map<String, Object>> para);

	RetKit save(HelpVo helpVo);

	RetKit del(Integer helpId);

}
