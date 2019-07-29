package com.dchip.cloudparking.api.serviceImp;



import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.api.constant.BaseConstant;
import com.dchip.cloudparking.api.iRepository.ICardLicensePlateRepository;
import com.dchip.cloudparking.api.iRepository.ICardRepository;
import com.dchip.cloudparking.api.iRepository.IMonthlyCardPayRepository;
import com.dchip.cloudparking.api.iRepository.IMonthlyCardRepository;
import com.dchip.cloudparking.api.iRepository.IParkingRepository;
import com.dchip.cloudparking.api.iRepository.IUserRepository;
import com.dchip.cloudparking.api.iService.IUserParkingService;
import com.dchip.cloudparking.api.model.po.Card;
import com.dchip.cloudparking.api.model.po.MonthlyCardPay;
import com.dchip.cloudparking.api.model.po.Parking;
import com.dchip.cloudparking.api.model.po.User;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;

@Service
public class UserParkingServiceImp implements IUserParkingService{
	@Autowired 
	private IMonthlyCardRepository monthlyCardRepository;
	@Autowired 
	private IUserRepository userRepository;
	@Autowired
	private IParkingRepository parkingRepository;
	@Autowired
	private IMonthlyCardPayRepository monthlyCardPayRepository;
	@Autowired
	private ICardRepository cardRepository; 
	@Autowired
	private ICardLicensePlateRepository cardLicensePlateRepository;
	/**
	 * 根据车牌显示月卡停车场信息
	 */
	@Override
	public RetKit findMonthlyCardAndParkingInfo(String licensePlate,Integer statue) {
		List<Map<String, Object>> data=new ArrayList<>();
		if (statue==0) {
			data = cardRepository.findCardAndParkingInfoByLicensePlat(licensePlate);
		}else {
			 data = cardRepository.findCardAndParkingInfoAndisFixedByLicensePlat(licensePlate); 
		}
		return RetKit.okData(data);
	}
	

	@Override
	public RetKit renewMonthlyCard(String userId,String cardId) {
			if (StrKit.isBlank(cardId)) {
				return RetKit.fail("月卡ID不能为空");
			}else {
				Optional<Card> cardOptional = monthlyCardRepository.findById(Integer.parseInt(cardId));
				if (!cardOptional.isPresent()) {
					return RetKit.fail("该月卡不存在");
				}else {
					Card card = cardOptional.get();
					Optional<Parking> parkingOptional = parkingRepository.findById(card.getParkingId());
					if (!parkingOptional.isPresent()) {
						return RetKit.fail("相关联的停车场不存在");
					}else {
						Parking parking = parkingOptional.get();
						//获取停车场的月卡费用
						BigDecimal fee = parking.getMonthFree();
						if (fee == null) {
							return RetKit.fail("该停车场的月卡费用为0");
						}else {
							if (!StrKit.isBlank(userId)) {
								Optional<User> userOptional = userRepository.findById(Integer.parseInt(userId));
								if (!userOptional.isPresent()) {
									return RetKit.fail("该用户不存在");
								}
								User user = userOptional.get();
								//生成月卡续费订单
								MonthlyCardPay monthlyCardPay = new MonthlyCardPay();
								monthlyCardPay.setMonthlyCardId(Integer.parseInt(cardId));
								if (user.getBalance().compareTo(fee) >= 0) {//用户的余额足够扣费
									user.setBalance(user.getBalance().subtract(fee));//扣费
									userRepository.save(user);
									//设置缴费费用，缴费时间，缴费方式，缴费状态
									monthlyCardPay.setPaymentMoney(fee);
									monthlyCardPay.setPaymentTime(new Date());
									monthlyCardPay.setPaymentWay(BaseConstant.OrderType.Balance.getTypeValue());
									monthlyCardPay.setStatus(BaseConstant.OrderStatus.AlreadyPay.getTypeValue());
									monthlyCardPayRepository.save(monthlyCardPay);
									//续费后更改月卡到期时间，在原基础上加一个月
									Date expiryTime = card.getExpiryTime();
									Calendar rightNow = Calendar.getInstance();
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
									String now = sdf.format(rightNow.getTime())+" 00:00:00";
									
									//2018年12月28号修改，若当前充值时间超过过期时间，月卡截止时间设置为当前时间加上一个月
									if(rightNow.getTime().after(expiryTime)) {
										try {
											rightNow.setTime(sdf.parse(now));
										} catch (ParseException e) {
											e.printStackTrace();
										}
										rightNow.add(Calendar.MONTH, 1);
										card.setExpiryTime(rightNow.getTime());
									}else {
										rightNow.setTime(expiryTime);
										rightNow.add(Calendar.MONTH, 1);
										card.setExpiryTime(rightNow.getTime());
									}
						
									monthlyCardRepository.save(card);
									return RetKit.ok("续费成功");
								}else {
									//费用不足，支付失败
									monthlyCardPay.setStatus(BaseConstant.OrderStatus.PayFail.getTypeValue());
									monthlyCardPayRepository.save(monthlyCardPay);
									return RetKit.fail("用户余额不足以扣费，月卡续费失败");
								}
						}else {
							return RetKit.fail("userId为空");
					}
				}
			}
					
		  }
				
		}
		 
}
		
}
