package com.dchip.cloudparking.api.iService;

import com.dchip.cloudparking.api.utils.RetKit;

public interface IAppointmentService {

	RetKit appointmentSubmit(String userId,String parkingId,String date,String licensePlat);
	
	RetKit getAppointments(String userId);
	
	RetKit getFee(String parkingId,String date);
	
	Integer getLastAppointmentCount(String userId);
	
	RetKit cancel(String userId);

	RetKit getUserAppointmentInfo(Integer userId,Integer pageSize,Integer pageNum);
}
