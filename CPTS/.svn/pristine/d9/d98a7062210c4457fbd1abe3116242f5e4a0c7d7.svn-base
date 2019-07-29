package com.dchip.cloudparking.api.iService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.api.utils.RetKit;

public interface IParkingInfoService {
	
	/**
	 * 显示用户停车信息，包括停车场名称、地址、车位区域、收费票准，当前产生的费用。
	 * by 小梁
	 */
	public RetKit getParkingInfoByUserId(Integer userId);
	
	/**
	 * 改变车辆状态 锁车或者解锁 0-不锁车，1-锁车
	 * by zyy
	 * */
	RetKit changeCarStatus(Integer isLock,Integer userId);
	
	/**
	 * 计算费用
	 */
	public BigDecimal calculateFree(Integer parkingId, Date parkDate);
	
	/**
	 * 获取下一个计费周期
	 */
	public long  nextCalculateTime(Integer parkingId, Date parkDate);
	
	/**
	 * 未支付订单累积到了黑名单条件 则该用户变成黑名单 
	 * sjh
	 */
	public void checkBlack(Integer userId);
	
	/**
	 * 有牌车入场业务类
	 * @param mac 相机唯一标识
	 * @param carNum 车牌号
	 * @param imgUrl 车照片url
	 * @param parkingDate 入场时间
	 * @param plateType 车牌类型
	 * @param carType carType 车辆类型
	 */
	public RetKit parking(String mac,String carNum,String imgUrl,String parkingDate, String plateType, String carType);
	
	/**
	 * 获取某停车场的所有车辆停车记录
	 * @author ZYY
	 * @param parkingId
	 * @param pageNum 
	 * @param pageSize 
	 * @param licensePlate 
	 * @return
	 */
	RetKit getParkingInfoList(String parkingId, Integer pageSize, Integer pageNum, String licensePlate);
	
	public RetKit getParkingInfoList(Integer pageSize, Integer pageNum, Integer companyId, List<Map<String, Object>> para);
}
