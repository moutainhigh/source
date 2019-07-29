package com.dchip.cloudparking.api.serviceImp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.api.constant.BaseConstant;
import com.dchip.cloudparking.api.iRepository.IMainControlRepository;
import com.dchip.cloudparking.api.iRepository.IParkingLotRepository;
import com.dchip.cloudparking.api.iService.IParkingLotManageService;
import com.dchip.cloudparking.api.model.po.MainControl;
import com.dchip.cloudparking.api.model.po.ParkingLot;
import com.dchip.cloudparking.api.utils.RetKit;

@Service
public class ParkingLotManageServiceImp implements IParkingLotManageService{
	@Autowired
	private IMainControlRepository mainControlRepository;
	@Autowired
	private IParkingLotRepository parkingLotRepository;

	@Override
	public RetKit saveParkingLot(Map<String, Object> params) {
		String mac = params.get("mac").toString();
		MainControl mainControl = mainControlRepository.findMainControl(mac);
		if (mainControl != null) {
			String uid = params.get("uid").toString();
			ParkingLot parkingLot = parkingLotRepository.findByUid(uid);
			if (parkingLot == null) {
				parkingLot = new ParkingLot();
				parkingLot.setUid(uid);
			}
			parkingLot.setMainId(mainControl.getId());
			parkingLot.setVersion(params.get("version").toString());
			parkingLot.setInitState(Integer.parseInt(params.get("initState").toString()));
			parkingLot.setState(BaseConstant.parkingLotState.NoCar.getTypeValue());//无车
			parkingLot.setStatus(BaseConstant.parkingLotStatus.Open.getTypeValue());//开放
			Integer online = Integer.parseInt(params.get("online").toString());
			if (online != 3) {
				parkingLot.setOnline(online);
			}
			return parkingLotRepository.save(parkingLot) != null ? RetKit.ok():RetKit.fail();
		}else {
			return RetKit.fail("该主控版不存在");
		}
	}

	@Override
	public RetKit changeStatus(String uid, String status) {
		ParkingLot parkingLot = parkingLotRepository.findByUid(uid);
		if (parkingLot != null) {
			parkingLot.setStatus(Integer.parseInt(status));
			return parkingLotRepository.save(parkingLot) != null ? RetKit.ok():RetKit.fail();
		}else {
			return RetKit.fail("找不到信息,uid输入错误");
		}
	}

}
