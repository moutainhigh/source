package com.dchip.cloudparking.web.iService;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dchip.cloudparking.web.model.vo.CardVo;
import com.dchip.cloudparking.web.utils.RetKit;

import javax.servlet.http.HttpServletResponse;

public interface IMonthlyCardService {

	public Object getMonthlyCardPayList(Integer pageSize, Integer pageNum, String sortName, String direction, List<Map<String, Object>> para);

	public RetKit cardImport(String fileName, MultipartFile file, Integer parkingId, HttpServletResponse response);

	public RetKit delMonthlyCard(Integer monthlyCardId);

	public Object getCardList(Integer pageSize, Integer pageNum, String sortName, String direction, List<Map<String, Object>> para);

	public RetKit addCardInfo(CardVo vo,String secondLicensePlate,String thirdLicensePlate);

	public RetKit editCardInfo(CardVo vo,String secondLicensePlate,String thirdLicensePlate);

	RetKit delCardLicensePlate(Integer cardId,String licensePlate);
	
	RetKit checkCardLicensePlate(String parkingId, String licensePlate);
}
