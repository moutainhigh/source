package com.dchip.cloudparking.api.serviceImp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.api.iRepository.IUserRepository;
import com.dchip.cloudparking.api.iService.ILicensePlatService;
import com.dchip.cloudparking.api.model.po.User;
import com.dchip.cloudparking.api.model.qpo.QUser;
import com.dchip.cloudparking.api.utils.RetKit;
import com.querydsl.core.Tuple;

@Service
public class LicensePlatServiceImp  extends BaseService implements ILicensePlatService{
	
	@Autowired
	private IUserRepository userRepository;
	
	/**
	 * 通过用户输入的车牌号码查找该车牌是否已注册
	 * by zyy
	 */
	@Override
	public RetKit validateLicensePlate(String licensePlat) {
		Integer licenseCount = userRepository.findByLicensePlate(licensePlat);
		if (licenseCount == 0) {
			return RetKit.ok("该车牌可注册");
		}else {
			return RetKit.ok("该车牌已被注册");
		}
	}
	
	/**
	 * 
	 * @param userId  用户id 
	 * @param plateLicense 车牌号
	 * @param realName  真实姓名
	 * @param drivingLicenseCode 行驶证编号
	 * @param idCard  身份证号码
	 * @param driverLicenseCode 驾驶证编号
	 * @return
	 */
	@Override
	public RetKit binding(String userId,String plateLicense,String realName,String drivingLicenseCode,String idCard,String driverLicenseCode) {
		RetKit retKit = validateLicensePlate(plateLicense);
		if(retKit.success()) {
			
			User user = userRepository.findById(Integer.parseInt(userId)).get();
			user.setDrivingLicenseNumber(drivingLicenseCode);
			user.setIdCard(idCard);
			user.setLicensePlat(plateLicense);
			user.setCarOwnerName(realName);
			user.setDriverLicenseNumber(driverLicenseCode);
			user.setIsAuthentication(1);
			userRepository.save(user);
			
			return RetKit.ok("绑定成功");
		}else {
			return retKit;
		}
	}

	@Override
	public RetKit getBindingInfo(String userId) {
		QUser u = QUser.user;
		Tuple tuple = jpaQueryFactory.select(u.drivingLicenseNumber,u.idCard,u.licensePlat,u.carOwnerName
				,u.driverLicenseNumber,u.isAuthentication,u.authenticationFailReason)
		.from(u).where(u.id.eq(Integer.parseInt(userId))).fetchFirst();
		Map<String, Object> map=new HashMap<>();
		if(tuple.size()==0) {
			return RetKit.okData("");
		}
		map.put("drivingLicenseCode", tuple.get(u.drivingLicenseNumber));
		map.put("idCard", tuple.get(u.idCard));
		map.put("plateLicense", tuple.get(u.licensePlat));
		map.put("realName", tuple.get(u.carOwnerName));
		map.put("driverLicenseCode", tuple.get(u.driverLicenseNumber));
		map.put("isAuthentication", tuple.get(u.isAuthentication));
		map.put("authenticationFailReason", tuple.get(u.authenticationFailReason));
		return RetKit.okData(map);
	}

}
