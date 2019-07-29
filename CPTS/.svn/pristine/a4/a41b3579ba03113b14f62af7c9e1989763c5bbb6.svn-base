package com.dchip.cloudparking.api.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.api.iRepository.IRechargeCouponRepository;
import com.dchip.cloudparking.api.iService.IRechargeCouponService;
import com.dchip.cloudparking.api.model.po.RechargeCoupon;
import com.dchip.cloudparking.api.utils.RetKit;

@Service
public class RechargeCouponServiceImp implements IRechargeCouponService{
	@Autowired
	private IRechargeCouponRepository rechargeCouponRepository;

	@Override
	public RetKit findRechargeCoupon() {
		
		List<RechargeCoupon> data = rechargeCouponRepository.findAll();
		return RetKit.okData(data);
	}

}
