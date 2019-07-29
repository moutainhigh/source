package com.dchip.cloudparking.api.serviceImp;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dchip.cloudparking.api.constant.BaseConstant;
import com.dchip.cloudparking.api.iRepository.IFreeStandardRepository;
import com.dchip.cloudparking.api.iRepository.IOrderRepository;
import com.dchip.cloudparking.api.iRepository.IParkingInfoRepository;
import com.dchip.cloudparking.api.iRepository.IParkingRepository;
import com.dchip.cloudparking.api.iRepository.IUserRepository;
import com.dchip.cloudparking.api.iService.IParkingInfoService;
import com.dchip.cloudparking.api.iService.IParkingService;
import com.dchip.cloudparking.api.model.po.FreeStandard;
import com.dchip.cloudparking.api.model.po.MainControl;
import com.dchip.cloudparking.api.model.po.MainRoadway;
import com.dchip.cloudparking.api.model.po.Order;
import com.dchip.cloudparking.api.model.po.ParkingInfo;
import com.dchip.cloudparking.api.model.po.Roadway;
import com.dchip.cloudparking.api.model.po.User;
import com.dchip.cloudparking.api.model.qpo.QMainControl;
import com.dchip.cloudparking.api.model.qpo.QMainRoadway;
import com.dchip.cloudparking.api.model.qpo.QParkingInfo;
import com.dchip.cloudparking.api.model.qpo.QRoadway;
import com.dchip.cloudparking.api.utils.DateUtil;
import com.dchip.cloudparking.api.utils.MessageUtil;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;
import com.dchip.parking.api.util.jpush.JGuangPush;
@Service
public class ParkingServiceImp  extends BaseService implements IParkingService {

	@Autowired
	private IParkingRepository parkingRepository;

	@Autowired
	private IParkingInfoRepository parkingInfoRepository;

	@Autowired
	private IOrderRepository orderRepository;

	@Autowired
	private IFreeStandardRepository freeStandardRepository;

	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IParkingInfoService parkingInfoService;

	/**
	 * 地图图钉： 点击图钉弹出显示停车场名称，地址，车位信息情况。可直接导航 by 小梁
	 */
	public RetKit findParkingByParkingId(Integer parkingId) {
		try {
			List<Map<String, Object>> parking = parkingRepository.findParkingByParkingId(parkingId);
			return RetKit.okData(parking);
		} catch (Exception e) {
			return RetKit.fail();
		}
	}

	/**
	 * 生成临时车牌
	 */
	public RetKit scanToGeneratLicensePlate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");// 转换格式
		String randomName = StrKit.getRandomUUID();
		randomName = sdf.format(date) + randomName.substring(30, randomName.length());
		return RetKit.okData(randomName);
	}
	
	/**
	 * 根据停车场ID查找出管理服务中心电话  zyy
	 */
	@Override
	public RetKit findPhoneByParkingId(Integer parkingId) {
		Map<String, Object> phone = parkingRepository.findPhoneByParkingId(parkingId);
		if (phone.size() == 0) {
			return RetKit.fail("parkingId有误");
		}
		return RetKit.okData(phone);
	}

	/**
	 * 扫码结算（无牌） sjh
	 */
	@Override
	public RetKit scanToSettlement(HttpServletRequest request) {
		String parkCode = request.getParameter("parkCode");
		String userIdStr = request.getParameter("userId");
		String parkInfoIdStr = request.getParameter("parkingInfoId");
		if (StrKit.isBlank(userIdStr)) {
			return RetKit.fail("用户id不能为空");
		}
		if (StrKit.isBlank(parkInfoIdStr)) {
			return RetKit.fail("停车id不能为空");
		}
		Integer parkInfoId = Integer.parseInt(parkInfoIdStr);
		Integer userId = Integer.parseInt(userIdStr);

		List<Map<String, Object>> parkingInfoList = parkingInfoRepository.findParkingInfoByParkingCode(parkCode,
				userId);
		if (StrKit.isBlank(parkCode)) {
			return RetKit.fail("请扫码");
		}
		if (parkingInfoList != null && parkingInfoList.size() > 0) {
			ParkingInfo parkingInfo = parkingInfoRepository.findParkingInfoById(parkInfoId);
			// 计算缴费金额
			if (parkingInfo.getParkCode() != null) {
				String outMessage = "";
				Order order = orderRepository.findOrder(parkingInfo.getId());
				Map<String, Object> parkingMap = parkingInfoRepository.findParkingFree(parkInfoId);
				int freeStandardId = parkingMap.get("freeStandardId") != null
						? Integer.parseInt(parkingMap.get("freeStandardId").toString())
						: -1;
				BigDecimal free = calculateFree(freeStandardId, parkingInfo.getParkDate(), new Date());
				if (free.equals(new BigDecimal(-1))) {
					return RetKit.fail("停车场没有设置收费标准");
				}
				if (order == null) {
					return RetKit.fail("订单不存在");
				}
				order.setFee(free);
				order.setParkingTime(DateUtil.calculateMinute(parkingInfo.getParkDate(), new Date()));
				order.setPayTime(new Date());
				orderRepository.save(order);
				// 如果是手动报障的订单先不扣费
				if (parkingInfo.getUserId() != null && parkingInfo.getUserId() > 0
						&& !parkingInfo.getStatus().equals(BaseConstant.parkingInfoStatus.errorStauts.getValue())) {
					RetKit retKit = autoDeductBalance(order, parkingInfo, free); // 自动扣余额
					if (retKit.success()) {
						outMessage = "本次停车已缴费，缴费金额为 " + order.getFee() + " 元";
					} else {
						parkingInfoService.checkBlack(userId);
						outMessage = retKit.getStr("msg") + "，缴费失败，待缴费金额为 " + order.getFee() + " 元";
					}

				} else {
					outMessage = "缴费失败，待缴费金额为 " + order.getFee() + " 元";
				}
				

				// 极光推送通知app端已经出库
				if (parkingInfo.getUserId() != null && parkingInfo.getUserId() > 0) {
					User user = userRepository.findUserById(parkingInfo.getUserId());
					if (user != null && StrKit.notBlank(user.getRegistrationId())) {
						pushMsgToUser(parkingInfo.getId(), BaseConstant.JpushType.CarOut.getTypeValue(),
								user.getRegistrationId(), outMessage);
					}
				}
			}
			return RetKit.ok();
		} else {
			return RetKit.fail("您无该停车场停车记录");
		}
	}

	private void pushMsgToUser(Integer msgId, Integer type, String registrationId, String msg) {
		JGuangPush.jiguangPush(registrationId, msg,msgId,type);
	}

	/**
	 * 当返回-1 表示该停车场没有设置收费模板
	 * 
	 * @param freeModelId
	 *            收费模板id
	 * @param parkDate
	 *            停进去时间
	 * @param outDate
	 *            出库时间
	 */
	@Override
	public BigDecimal calculateFree(Integer freeStandardId, Date parkDate, Date outDate) {
		BigDecimal currentDayFree = new BigDecimal(-1);
		FreeStandard freeStandard = freeStandardRepository.findFreeStandard(freeStandardId);
		if (freeStandard != null) {
			Date parkDateEndTime = new Date();// 停车当天的最大时间
			parkDateEndTime.setYear(parkDate.getYear());
			parkDateEndTime.setMonth(parkDate.getMonth());
			parkDateEndTime.setDate(parkDate.getDate());
			parkDateEndTime.setHours(24);
			parkDateEndTime.setMinutes(0);
			parkDateEndTime.setSeconds(0);
			if (outDate.getTime() > parkDateEndTime.getTime()) {// 停车超过凌晨24点重新计算
				int parkingFirstDayMinute = DateUtil.calculateMinute(parkDate, parkDateEndTime); // 停车超过24点
				int firstTotalHour = parkingFirstDayMinute / 60;// 停了多少小时
				int remainderMinute = parkingFirstDayMinute - firstTotalHour * 60; // 剩余的分钟
				if (remainderMinute >= 0) {
					firstTotalHour += 1;
				}
				/**
				 * 停车当天
				 */
				if (parkingFirstDayMinute < freeStandard.getFreeTimeLength()) {// 还没超过免费时长
					currentDayFree = new BigDecimal(0);
				} else if (freeStandard.getMostCost() != null
						&& new BigDecimal(firstTotalHour).multiply(freeStandard.getHourCost())
								.doubleValue() < freeStandard.getMostCost().doubleValue()) {
					currentDayFree = new BigDecimal(firstTotalHour).multiply(freeStandard.getHourCost());
				} else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
					currentDayFree = new BigDecimal(firstTotalHour).multiply(freeStandard.getHourCost());
				} else {
					currentDayFree = freeStandard.getMostCost();
				}
				/**
				 * 停车当天之后的时间
				 */
				int parkingFirstDayOtherMinute = DateUtil.calculateMinute(parkDateEndTime, new Date()); // 停车当天之后的时间
				int afterFirstDayDays = parkingFirstDayOtherMinute / 60 / 24;// 第一天停车之后 后面停了多少天
				int afterFirstDayMinutes = parkingFirstDayOtherMinute - afterFirstDayDays * 24 * 60; // 剩余分钟
				if (afterFirstDayDays > 0) {
					// 超出停车当日的天数
					if (freeStandard.getMostCost() != null && new BigDecimal(24).multiply(freeStandard.getHourCost())
							.doubleValue() < freeStandard.getMostCost().doubleValue()) {
						currentDayFree = currentDayFree
								.add(new BigDecimal(24 * afterFirstDayDays).multiply(freeStandard.getHourCost()));
					} else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
						currentDayFree = currentDayFree
								.add(new BigDecimal(24 * afterFirstDayDays).multiply(freeStandard.getHourCost()));
					} else {
						currentDayFree = currentDayFree
								.add(freeStandard.getMostCost().multiply(new BigDecimal(afterFirstDayDays)));
					}
					// 还要加上余数分钟
					int endDayHour = afterFirstDayMinutes / 60;// 最后不到一天的小时数
					int endDayHourMinute = afterFirstDayMinutes - endDayHour * 60;// 最后不到一天的小时数-剩余的分钟数
					if (endDayHourMinute >= 0) {
						endDayHour += 1;
					}
					// 超出停车当日的天数-但是剩余的不够一天的
					if (freeStandard.getMostCost() != null
							&& new BigDecimal(endDayHour).multiply(freeStandard.getHourCost())
									.doubleValue() < freeStandard.getMostCost().doubleValue()) {
						currentDayFree = currentDayFree
								.add(new BigDecimal(endDayHour).multiply(freeStandard.getHourCost()));
					} else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
						currentDayFree = currentDayFree
								.add(new BigDecimal(endDayHour).multiply(freeStandard.getHourCost()));
					} else {
						currentDayFree = currentDayFree.add(freeStandard.getMostCost());
					}
				} else {
					int secondDayHours = parkingFirstDayOtherMinute / 60; // 只超了停车当天但是又不满一天
					int secondDayMinutes = parkingFirstDayOtherMinute - secondDayHours * 60; // 只超了停车当天但是又不满一天-剩余的分钟的余数
					if (secondDayMinutes >= 0) {
						secondDayHours += 1;
					}
					if (freeStandard.getMostCost() != null
							&& new BigDecimal(secondDayHours).multiply(freeStandard.getHourCost())
									.doubleValue() < freeStandard.getMostCost().doubleValue()) {
						currentDayFree = currentDayFree
								.add(new BigDecimal(secondDayHours).multiply(freeStandard.getHourCost()));
					} else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
						currentDayFree = currentDayFree
								.add(new BigDecimal(secondDayHours).multiply(freeStandard.getHourCost()));
					} else {
						currentDayFree = currentDayFree.add(freeStandard.getMostCost());
					}
				}
			} else {
				int parkingFirstDayMinute = DateUtil.calculateMinute(parkDate, outDate); // 停车不超过停车当天
				int totalHour = parkingFirstDayMinute / 60;
				int remainderMinute = (int) parkingFirstDayMinute - totalHour * 60 - freeStandard.getFreeTimeLength(); // 剩余的分钟
				if (remainderMinute >= 0) {
					totalHour += 1;
				}
				if (parkingFirstDayMinute < freeStandard.getFreeTimeLength()) {// 还没超过免费时长
					currentDayFree = new BigDecimal(0);
				} else if (freeStandard.getMostCost() != null
						&& new BigDecimal(totalHour).multiply(freeStandard.getHourCost()).doubleValue() < freeStandard
								.getMostCost().doubleValue()) {
					currentDayFree = new BigDecimal(totalHour).multiply(freeStandard.getHourCost());
				} else if (freeStandard.getMostCost() == null) {// 没有设置当日最大收费
					currentDayFree = new BigDecimal(totalHour).multiply(freeStandard.getHourCost());
				} else {
					currentDayFree = freeStandard.getMostCost();
				}
			}
			return currentDayFree;

		} else {
			return currentDayFree;
		}

	}

	/**
	 * 扣去个人账号余额
	 */
	private RetKit autoDeductBalance(Order order, ParkingInfo parkingInfo, BigDecimal free) {
		User user = userRepository.findUserById(parkingInfo.getUserId());
		if (user == null) {
			return RetKit.fail("缴费账号信息不存在");
		}
		if (free.compareTo(user.getBalance()) == 1) { // 不够钱扣
			order.setStatus(BaseConstant.OrderStatus.PayFail.getTypeValue());
			orderRepository.save(order);
			return RetKit.fail("余额不足");
		}
		BigDecimal remain = new BigDecimal(0); // 剩余余额
		remain = user.getBalance().subtract(free);
		userRepository.changeBalance(parkingInfo.getUserId(), remain);
		parkingInfoRepository.save(parkingInfo);
		order.setType(BaseConstant.OrderType.Balance.getTypeValue());
		order.setStatus(BaseConstant.OrderStatus.AlreadyPay.getTypeValue());
		orderRepository.save(order);
		return RetKit.ok();
	}
	
	
	/**
	 * 查找附近的停车场
	 * @param longitude 当前经度
	 * @param latitude 当前纬度
	 * @param distance 附近的距离(千米)
	 * @return
	 */
	public RetKit findneighborhoodParking(Double longitude,Double latitude,Double distance){
			//先计算查询点的经纬度范围
			Double r = 6371.00;//地球半径千米
			Double angleLongi =  2*Math.asin(Math.sin(distance/(2*r))/Math.cos(latitude*Math.PI/180));
			angleLongi = angleLongi*180/Math.PI;//角度转为弧度
			Double angleLati = distance/r;
			angleLati = angleLati*180/Math.PI;		
			Double minLati =latitude-angleLati;
			Double maxLati = latitude+angleLati;
			Double minLongi = longitude -angleLongi;
			Double maxLongi = longitude + angleLongi;
			return RetKit.okData(parkingRepository.findneighborhoodParking(minLongi, maxLongi, minLati, maxLati,longitude,latitude));
	}

	@Override
	public RetKit search(String name) {
		return RetKit.okData(parkingRepository.findByNameLike(name));
	}

	@Override
	public RetKit findParking(String parkingId) {
		return RetKit.okData(parkingRepository.findByPakingId(parkingId));
	}

}
