package com.dchip.cloudparking.web.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.web.iRepository.IMainControlRepository;
import com.dchip.cloudparking.web.iService.IMainControlService;
import com.dchip.cloudparking.web.model.po.MainControl;

@Service
public class MainControlServiceImp extends BaseService implements IMainControlService {

	@Autowired
	private IMainControlRepository mainControlRepository;
	
	@Override
	public List<MainControl> findMainControlsByType(Integer type) {
		return mainControlRepository.findMainControlsByType(type);
	}
	
	public List<MainControl> findMainControl(Integer actTo){
		return mainControlRepository.findMainControl(actTo);
	}

}
