package com.dchip.cloudparking.api.iService;

import com.dchip.cloudparking.api.utils.RetKit;

public interface IUserParkingService {

	RetKit findMonthlyCardAndParkingInfo(String licensePlat,Integer statue);
	
	RetKit renewMonthlyCard(String userId,String monthlyCardId);

}
