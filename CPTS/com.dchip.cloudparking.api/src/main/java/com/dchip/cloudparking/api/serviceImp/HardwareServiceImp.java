package com.dchip.cloudparking.api.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.api.constant.BaseConstant;
import com.dchip.cloudparking.api.iRepository.IMainControlRepository;
import com.dchip.cloudparking.api.iRepository.IMainRoadwayRepository;
import com.dchip.cloudparking.api.iRepository.IRoadwayRepository;
import com.dchip.cloudparking.api.iRepository.IStockRepository;
import com.dchip.cloudparking.api.iService.IHardwareService;
import com.dchip.cloudparking.api.model.po.MainRoadway;
import com.dchip.cloudparking.api.model.po.Roadway;
import com.dchip.cloudparking.api.model.po.Stock;
import com.dchip.cloudparking.api.utils.RetKit;
@Service
public class HardwareServiceImp extends BaseService implements IHardwareService {

	@Autowired
	private IRoadwayRepository roadwayRepository;
	@Autowired
	private IStockRepository stockRepository;
	@Override
	public RetKit getCameraInfo(String mac) {		
		return RetKit.okData(roadwayRepository.findCameraInfo(mac));
	}
	
	@Override
	public RetKit changeCarportNum(String mac) {
		Roadway roadway = roadwayRepository.findByCameraMac(mac);
		if(roadway==null) {
			return RetKit.fail("camera not find");
		}
		
		Stock stock = stockRepository.findByParkingId(roadway.getParkingId());
		if(stock==null) {
			return RetKit.fail("parking info not find");
		}
		if(roadway.getInOutMarker() == null) {
			return RetKit.fail("in_out_marker not find");
		}
		String msg = "";
		if(roadway.getInOutMarker() == 1) {
			//入口+1
			stock.setEnterCount(stock.getEnterCount()==stock.getTotalCount()?stock.getTotalCount():stock.getEnterCount()+1);
			msg = "add succeed";
		}else if(roadway.getInOutMarker() == 2){
			//出口-1
			stock.setEnterCount(stock.getEnterCount()==0?0:stock.getEnterCount()-1);
			msg = "reduce succeed";
		}
		stockRepository.save(stock);
		return RetKit.ok(msg);
	}

	@Override
	public RetKit changeStatus(String mac, String onLine) {
		Roadway roadway = roadwayRepository.findByCameraMac("mac");
		if(roadway==null) {
			return RetKit.fail("camera is not found");
		}
		if(onLine.equals(BaseConstant.CameraStatus.OnLine.getTypeValue().toString()) 
				|| onLine.equals(BaseConstant.CameraStatus.OffLine.getTypeValue().toString())) {
			roadway.setStatus(Integer.parseInt(onLine));
			roadwayRepository.save(roadway);
			return RetKit.ok();
		}else {
			return RetKit.fail("onLine false");
		}
		
		
	}



}
