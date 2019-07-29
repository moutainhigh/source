package com.dchip.cloudparking.api.iService;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.dchip.cloudparking.api.utils.RetKit;

public interface IParkingService {
	
	/**
	 * 地图图钉： 点击图钉弹出显示停车场名称，地址，车位信息情况。可直接导航
	 * by 小梁
	 */
	public RetKit findParkingByParkingId(Integer parkingId);
	
	
	/**
	 * 扫码生成临时车牌
	 * sjh
	 */
	public RetKit scanToGeneratLicensePlate();
	
	/**
	 * 扫码结算（无牌）
	 * sjh
	 */
	public RetKit scanToSettlement(HttpServletRequest request);
	
	/**
	 * 根据停车场ID查找出管理服务中心电话
	 * @param parkingId
	 * @return
	 */
	public RetKit findPhoneByParkingId(Integer parkingId);
	
	BigDecimal calculateFree(Integer freeStandardId, Date parkDate, Date outDate);
	
	RetKit findneighborhoodParking(Double longitude,Double latitude,Double distance);
	
	RetKit search(String name);		
	
	RetKit findParking(String parkingId);

}
