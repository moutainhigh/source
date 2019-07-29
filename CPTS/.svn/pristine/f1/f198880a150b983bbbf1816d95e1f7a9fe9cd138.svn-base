package com.dchip.cloudparking.api.serviceImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dchip.cloudparking.api.constant.BaseConstant;
import com.dchip.cloudparking.api.iRepository.ISessionRepository;
import com.dchip.cloudparking.api.iRepository.IUserRepository;
import com.dchip.cloudparking.api.model.po.ParkingUser;
import com.dchip.cloudparking.api.model.po.Session;
import com.dchip.cloudparking.api.utils.StrKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.api.iRepository.IParkingUserRepository;
import com.dchip.cloudparking.api.iService.IMerchantService;
import com.dchip.cloudparking.api.model.po.Parking;
import com.dchip.cloudparking.api.model.qpo.QAccount;
import com.dchip.cloudparking.api.model.qpo.QParking;
import com.dchip.cloudparking.api.utils.HashKit;
import com.dchip.cloudparking.api.utils.RetKit;
import com.querydsl.core.types.Predicate;

import javax.servlet.http.HttpServletRequest;

@Service
public class MerchantServiceImp extends BaseService implements IMerchantService {

	@Autowired
	private IParkingUserRepository parkingUserRepository;
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private ISessionRepository sessionRepository;
	
	/**
	 * 判断是否有这个用户名
	 */
	public Boolean hasLoginName(String LoginName) {
		Boolean loginFlag = true;
		if (parkingUserRepository.getUserLoginNameNum(LoginName) == 0) {
			loginFlag = false;
		}
		return loginFlag;
	}
	
	/**
	 * 校验是否用户名密码合法
	 */
	public Boolean pwdIsCorrect(String loginName, String pwd) {
		Boolean loginFlag = true;
		String pwdmd = HashKit.md5(pwd);
		if (parkingUserRepository.getUserLoginSuccessNum(loginName, pwdmd) == 0) {
			loginFlag = false;
		}
		return loginFlag;
	}

	@Override
	public RetKit getParkingList(String userName) {
		QAccount qAccount = QAccount.account;
		QParking qParking = QParking.parking;
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(qAccount.companyId.eq(qParking.companyId));
		predicates.add(qAccount.userName.eq(userName));

		List<Parking> parkings = jpaQueryFactory
				.select(qParking)
				.from(qAccount, qParking)
				.where(predicates.toArray(new Predicate[predicates.size()])).fetch();
		return RetKit.okData(parkings);
	}

	@Override
	public RetKit getParkingUser(HttpServletRequest request, String userName, String pwd) {
		Map<String, Object> parkingUserMap = parkingUserRepository.findByUserName(userName,HashKit.md5(pwd));
		if(parkingUserMap != null){
			if (parkingUserMap.get("status").equals(BaseConstant.parkingUserStatus.delete.getTypeValue())) {
				return RetKit.fail("该用户已被删除");
			};
			Integer parkingUserId = Integer.parseInt(parkingUserMap.get("id").toString());
			Session session = sessionRepository.findSessionByUserId(parkingUserId, BaseConstant.SessionUserType.ParkingUser.getTypeValue());
			Integer platform = Integer.parseInt(request.getHeader("platform"));
			String token = StrKit.getRandomUUID();
			if (session != null) {
				session.setPlatform(platform);
				session.setToken(token);
				sessionRepository.save(session);
			} else {
				session = new Session();
				session.setPlatform(platform);
				session.setToken(token);
				session.setUserId(parkingUserId);
				session.setType(BaseConstant.SessionUserType.ParkingUser.getTypeValue());
				sessionRepository.save(session);
			}

            Map<String, Object> userLoginDetails = new HashMap<>();
            userLoginDetails.put("accessToken", token);
            userLoginDetails.put("parkingUserId", parkingUserMap.get("id"));
            userLoginDetails.put("createTime", parkingUserMap.get("create_time"));
            userLoginDetails.put("realName", parkingUserMap.get("real_name"));
            userLoginDetails.put("parkName", parkingUserMap.get("park_name"));
            userLoginDetails.put("parkingId", parkingUserMap.get("parking_id"));
            userLoginDetails.put("type", parkingUserMap.get("type"));
            userLoginDetails.put("userName", parkingUserMap.get("user_name"));
            userLoginDetails.put("status", parkingUserMap.get("status"));
            return RetKit.okData(userLoginDetails);
		}else{
			return RetKit.fail("用户名或密码不正确，请重新输入");
		}
	}
	
	public ParkingUser getParkingUserByName(String userName) {
		return null;
	}

}
 