package com.dchip.cloudparking.api.serviceImp;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.api.constant.BaseConstant;
import com.dchip.cloudparking.api.constant.SocketConstant;
import com.dchip.cloudparking.api.iRepository.IAppointmentRepository;
import com.dchip.cloudparking.api.iRepository.IFreeStandardRepository;
import com.dchip.cloudparking.api.iRepository.IMessageRepository;
import com.dchip.cloudparking.api.iRepository.IOrderRepository;
import com.dchip.cloudparking.api.iRepository.IParkingRepository;
import com.dchip.cloudparking.api.iRepository.IPointRecordRepository;
import com.dchip.cloudparking.api.iRepository.IStockRepository;
import com.dchip.cloudparking.api.iRepository.IUserRepository;
import com.dchip.cloudparking.api.iService.IAppointmentService;
import com.dchip.cloudparking.api.iService.IParkingInfoService;
import com.dchip.cloudparking.api.iService.IParkingService;
import com.dchip.cloudparking.api.model.po.Appointment;
import com.dchip.cloudparking.api.model.po.FreeStandard;
import com.dchip.cloudparking.api.model.po.MainControl;
import com.dchip.cloudparking.api.model.po.Message;
import com.dchip.cloudparking.api.model.po.Order;
import com.dchip.cloudparking.api.model.po.Parking;
import com.dchip.cloudparking.api.model.po.PointRecord;
import com.dchip.cloudparking.api.model.po.Stock;
import com.dchip.cloudparking.api.model.po.User;
import com.dchip.cloudparking.api.model.qpo.QMainControl;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.SocketKit;
import com.dchip.cloudparking.api.utils.StrKit;
import com.dchip.parking.api.util.jpush.JGuangPush;

@Service
public class AppointmentServiceImp  extends BaseService implements IAppointmentService,BaseConstant{
    
	@Autowired
	private IAppointmentRepository appointmentRepository;
	@Autowired
	private IParkingService parkingService;
	@Autowired
	private IParkingRepository parkingRepository;
	@Autowired
	private IParkingInfoService parkingInfoService;
	@Autowired
	private IOrderRepository orderRepository;
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IStockRepository stockRepository;
	@Autowired
	private IFreeStandardRepository freeStandardRepository;
	@Autowired
	private IMessageRepository messageRepository;
	@Autowired
	private IPointRecordRepository pointRecordRepository;
	
	private QMainControl qMainControl = QMainControl.mainControl;

	//定期更新过期预约
	private static Timer timer = new Timer(true);

	public AppointmentServiceImp() {
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				updateAllOverdue();
			}
		}, 0,4000);
	}
	
	/**
	 * 提交预约
	 */
	private final static Integer TIME_LIMIT = 15;//一定时间内（分钟）不能预约两次
	public RetKit appointmentSubmit(String userId, String parkingId, String date, String licensePlat) { 
		//判断是否是会员
		Optional<User> userOptional = userRepository.findById(Integer.parseInt(userId));
		if(!userOptional.isPresent()) {
			return RetKit.fail("该用户不存在");
		} 
		User user = userOptional.get();
		if(user.getIsBlack() != null) {//黑名单用户提示
			if(user.getIsBlack().equals(BaseConstant.UserIsBlack.BlacklistUser.getTypeValue())) {
				return RetKit.fail("您的未支付订单已超标，请缴清费用后再使用！");
			}
		}
		if(appointmentRepository.appointmentCount(userId)>0) {//0-正常预约时间内  
			return RetKit.fail("您已预约过了！");
		}
		
		try {
			Appointment lastAppointment = appointmentRepository.findLastAppointmentInfo(userId);//获取上一次预约的信息
			//一定时间内不能预约两次
			if (lastAppointment != null) {
				Date lastAppointmentDate = lastAppointment.getCreateTime();//上次预约的时间
				Date now = new Date();//将要预约的时间
				// 获得两个时间的毫秒时间差异
				double diff = now.getTime() - lastAppointmentDate.getTime();
				// 计算差多少分钟
				int min = (int)Math.ceil(diff / 1000 / 60);
				if (min<TIME_LIMIT) {//如果少于15分钟,预约失败
					return RetKit.fail(TIME_LIMIT+"分钟内不能预约两次！");
				}
			}
			//预约入场的时间
			SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date Date = spf.parse(date);
			if(Date.before(new Date())) {
				return RetKit.fail("预约入场的时间必须大于当前时间");
			}
			
			//找到预约的停车场
			Optional<Parking> parkingOptional = parkingRepository.findById(Integer.parseInt(parkingId));
			if(parkingOptional.isPresent()) {
				Parking parking = parkingOptional.get();	
				//停车场停车位情况
				Stock stock = stockRepository.findByParkingId(parking.getId());
				if(stock == null) {
					return RetKit.fail("找不到车位数信息");
				}
				//已进入车辆
				if (stock.getEnterCount() < stock.getTotalCount()) {
					stock.setEnterCount(stock.getEnterCount()+1);
				}else {
					return RetKit.fail("车位已满");
				}
				stock.setUpdateTime(new Date());
				stockRepository.save(stock);
				//通知硬件更新车位数
				UpdateHardwareStock(stock);	
			}else {
				return RetKit.fail("该停车场不存在");
			}		
			
			//生成预约信息
			Appointment appointment = new Appointment();
			appointment.setUserId(Integer.parseInt(userId));
			appointment.setParkingId(Integer.parseInt(parkingId));
			appointment.setStatus(AppointmentStatus.Normal.getTypeValue());//0-正常预约时间内 
			appointment.setCreateTime(new Date());
			appointment.setAppointStartTime(Date);
			appointment.setLicensePlate(licensePlat);
			appointment = appointmentRepository.save(appointment);
		} catch (ParseException e) {
			e.printStackTrace();
			return RetKit.fail("日期格式错误");
		}
		
		return RetKit.ok("预约成功");
	}
	
	//更新硬件车位数
	private void UpdateHardwareStock(Stock stock) {
		//通知硬件更新绑定情况
		try {
			List<MainControl> mainControlList = jpaQueryFactory.select(qMainControl).from(qMainControl).where(qMainControl.parkingId.eq(stock.getParkingId())).fetch();
			for(MainControl mainControl: mainControlList) {
				SocketKit.sendMessage(mainControl.getMac(), "cameraMac", SocketConstant.CommandType.BindingMainControl.getValue());
			}			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public RetKit getAppointments(String userId) {
		getLastAppointmentCount(userId);
		if(StrKit.isBlank(userId)) {
			return RetKit.fail("用户id不能为空");
		}
		List<Map<String, Object>> data = appointmentRepository.findAppointments(userId);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if(!data.isEmpty()) {
			for (Map<String, Object> map : data) {
				Integer freeStandardId = appointmentRepository.findFreeStandardId(map.get("parkingId").toString());
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
				Map<String, Object> m = new HashMap<>(map);
				try {
					m.put("fee", parkingService.calculateFree(freeStandardId,
							simpleDateFormat.parse(map.get("createDate").toString()), 
							simpleDateFormat.parse(map.get("appointDate").toString())
							));
				} catch (ParseException e) {
					e.printStackTrace();
					return RetKit.fail("日期格式错误");
				}
				result.add(m);
			}
		}

		return RetKit.okData(result);
	}
	
	@Override
	public RetKit getFee(String parkingId, String date) {
		if(StrKit.isBlank(parkingId)) {
			return RetKit.fail("停车场id不能为空");
		}
		DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
		Date Date;
		try {
			Date = dateFormat.parse(date);
		} catch (ParseException e) {
			return RetKit.fail("日期不能为空或格式错误");
		}
		if(Date.before(new Date())) {
			return RetKit.fail("预约的时间必须大于当前时间");
		}
		Integer freeStandardId = appointmentRepository.findFreeStandardId(parkingId);
		Map<String, Object> fee = new HashMap<>();
		fee.put("fee", parkingService.calculateFree(freeStandardId, new Date(), Date));
		return RetKit.okData(fee);
	}
	
	/**
	 * 获取最新状态的用户预约数,这个方法会更新过期预约并结单
	 * @param userId
	 * @return
	 */
	@Override
	public Integer getLastAppointmentCount(String userId) {
		if(getLastAppointment(userId)==null) {
			return 0;
		}
		return 1;
	}
	
	/**
	 * 获取最新状态的用户预约,这个方法会更新过期预约并结单
	 * @param userId
	 * @return
	 */
	private Appointment getLastAppointment(String userId) {
		Optional<User> userOptional = userRepository.findById(Integer.parseInt(userId));
		if(!userOptional.isPresent()) {
			return null;
		}
		//先指定获取过期的预约
		Appointment appointment = appointmentRepository.findOverdue(userId);
		if(appointment != null) {
			updateOverdue(appointment);
		}
		//返回该用户更新后的预约
		return appointmentRepository.findByUserId(userId);
	}
	
	//更新全部预约
	private RetKit updateAllOverdue() {
		List<Appointment> appointments=null;
		if(appointmentRepository != null){
			appointments = appointmentRepository.findAllOverdue();
		}
		for (Appointment appointment : appointments) {
			updateOverdue(appointment);
		}
		return RetKit.ok();
	}
	//更新过期预约
	private RetKit updateOverdue(Appointment appointment) {
		Optional<User> userOptional = userRepository.findById(appointment.getUserId());
		if(!userOptional.isPresent()) {
			return RetKit.fail("找不到用户");
		}
		//更新过期的预约
		appointmentRepository.updateOverdueStatus(appointment.getId());
		Parking parking = parkingRepository.findById(appointment.getParkingId()).get();
		Stock stock = stockRepository.findByParkingId(parking.getId());
		if(stock == null) {
			return RetKit.fail("找不到车位数信息");
		}
		stock.setEnterCount(stock.getEnterCount()-1);
		stock.setUpdateTime(new Date());
		//预约过期后，需要结单
		orderRepository.save(generateAppointmentOrder(appointment.getUserId(), appointment.getId()));
		stockRepository.save(stock);
		//通知客户端
		JGuangPush.jiguangPush(userOptional.get().getRegistrationId(), "预约过期", 0, 3);
		//通知硬件更新车位数
		UpdateHardwareStock(stock);
		return RetKit.ok();
		
	}
 

	
	/**
	 * 取消预约
	 */
	@Override
	public RetKit cancel(String userId) { 
		Appointment appointment = getLastAppointment(userId);
		if(appointment==null) {
			return RetKit.fail("没有找到预约信息");
		}
		Optional<Parking> optionalParking = parkingRepository.findById(appointment.getParkingId());
		if(!optionalParking.isPresent()) {
			return RetKit.fail("找不到停车场");
		}
		//获取预约停车场
		Parking parking = optionalParking.get();
		//车位数-1
		Stock stock = stockRepository.findByParkingId(parking.getId());
		if(stock == null) {
			return RetKit.fail("找不到车位数信息");
		}
		//取消预约
		appointment.setStatus(BaseConstant.AppointmentStatus.Cancel.getTypeValue());
		//改变车位数
		stock.setEnterCount(stock.getEnterCount() == 0 ? 0 : stock.getEnterCount()-1);
		stock.setUpdateTime(new Date());
		
		//结单
		orderRepository.save(generateAppointmentOrder(Integer.parseInt(userId), appointment.getId()));
		appointmentRepository.save(appointment);
		stockRepository.save(stock);
		//通知硬件更新车位数
		UpdateHardwareStock(stock);
		return RetKit.ok();
	}
	
	
	/**
	 * 获取用户的预约信息
	 */
	@Override
	public RetKit getUserAppointmentInfo(Integer userId,Integer pageSize,Integer pageNum) {
		if (userId == null) {
			return RetKit.fail("userId不能为空");
		};
		getLastAppointmentCount(userId.toString());//更新预约
		if (pageSize == null) {
			return RetKit.fail("pageSize不能为空");
		};
		if (pageNum == null) {
			return RetKit.fail("pageNum不能为空");
		}
		Integer totalElements = appointmentRepository.findUserAppointmentsCount(userId);
		if(totalElements == null) {
			totalElements = 0;
		}
		Integer totalPages = (int) Math.ceil(totalElements / pageSize);
		Map<String, Object> result = new HashMap<>();
		result.put("content", appointmentRepository.findUserAppointmentInfo(userId, pageSize, pageNum * pageSize));
		result.put("totalElements", totalElements);
		result.put("totalPages", totalPages);
		return RetKit.okData(result);
	}
	
	//生成预约订单信息
	private Order generateAppointmentOrder(Integer userId,Integer appointmentId) {
		Appointment appointment = appointmentRepository.findById(appointmentId).get();
		Parking parking = parkingRepository.findById(appointment.getParkingId()).get();
		Order order = new Order();
		order.setUserId(userId);
		//预约的优惠仅为为0
		order.setDiscountAmount(new BigDecimal(0));
		//预约订单
		order.setAppointmentId(appointmentId);
		//未转账到公司
		order.setIsTransfer(0);
		
		//预约时间加15分钟(15分钟免费,超过十五分钟后，按停车场收费标准收费)
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(appointment.getCreateTime());
		calendar.add(Calendar.MINUTE, TIME_LIMIT);
		Date date = calendar.getTime();//加了15分钟之后的时间
		Date nowDate = new Date();
		// 获得两个时间的毫秒时间差异
		double diff = nowDate.getTime() - date.getTime();
		// 计算差多少分钟
		int min = (int)Math.ceil(diff / 1000 / 60);
		//预约费用
		BigDecimal fee;
		if(date.before(nowDate)) {//取消预约时超过了15分钟
			//获取收费标准的免费时长
			FreeStandard freeStandard = freeStandardRepository.findById(parking.getFreeStandardId()).get();
			Integer freeTime = freeStandard.getFreeTimeLength();
			//如果该停车场的免费时长大于15分钟
			if (freeTime<=TIME_LIMIT) {
				fee = parkingService.calculateFree(parking.getFreeStandardId(), appointment.getCreateTime(), nowDate);
			}else {
				calendar.setTime(nowDate);
				calendar.add(Calendar.MINUTE, freeTime + min);//抵消免费时长
				fee=parkingService.calculateFree(parking.getFreeStandardId(), nowDate, calendar.getTime());
			}			
		}else {
			//没有超过了15分钟，不扣费
			fee = new BigDecimal(0);
		}
	 
		//扣费
		User user = userRepository.findById(userId).get();	
		BigDecimal userBalance = user.getBalance();
		if(userBalance.compareTo(fee) >= 0) {
			//用户的余额足够扣费
			user.setBalance(userBalance.subtract(fee));//扣费
			userRepository.save(user);
			order.setStatus(BaseConstant.OrderStatus.AlreadyPay.getTypeValue());
			
			//送积分
			PointRecord pointRecord = new PointRecord();
			pointRecord.setCreateTime(new Date());
			pointRecord.setRemark("预约消费");
			pointRecord.setScore((int)Math.ceil(Double.parseDouble(fee.toString())));
			pointRecord.setUserId(userId);
			pointRecordRepository.save(pointRecord);
			
		}else {
			//费用不足，扣费失败
			order.setStatus(BaseConstant.OrderStatus.PayFail.getTypeValue());
			//写入用户消息
			Message message = new Message();
			message.setTitle("扣费失败");
			message.setContent("您的余额不足，有一张订单自动扣费失败，请及时处理！");
			message.setCreateTime(new Date());
			message.setType(BaseConstant.MessageType.Specify.getTypeValue());
			message.setTarget(user.getId().toString());
			messageRepository.save(message);
			
			//黑名单
			parkingInfoService.checkBlack(userId);
			
		}
		order.setType(BaseConstant.OrderType.Balance.getTypeValue());
		order.setPayTime(new Date());	
		order.setFee(fee);
		order.setFinalFee(fee);

		return order;
	}

}
