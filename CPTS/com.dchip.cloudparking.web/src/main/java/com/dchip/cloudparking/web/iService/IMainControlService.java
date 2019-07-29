package com.dchip.cloudparking.web.iService;

import java.util.List;

import com.dchip.cloudparking.web.model.po.MainControl;

public interface IMainControlService {
	/**
	 * 根据type获取所有主控板列表
	 */
	List<MainControl> findMainControlsByType(Integer type);

	List<MainControl> findMainControl(Integer actTo);
}
