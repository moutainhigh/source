package com.dchip.cloudparking.wechat.serviceImp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dchip.cloudparking.wechat.constant.BaseConstant;
import com.dchip.cloudparking.wechat.iRepository.ICompanyRepository;
import com.dchip.cloudparking.wechat.iRepository.IFreeStandardRepository;
import com.dchip.cloudparking.wechat.iRepository.IParkingInfoRepository;
import com.dchip.cloudparking.wechat.iRepository.IParkingRepository;
import com.dchip.cloudparking.wechat.iRepository.IRoadWayRepository;
import com.dchip.cloudparking.wechat.iRepository.IUserRepository;
import com.dchip.cloudparking.wechat.iService.IRoadWayService;
import com.dchip.cloudparking.wechat.model.po.Company;
import com.dchip.cloudparking.wechat.model.po.FreeStandard;
import com.dchip.cloudparking.wechat.model.po.MainControl;
import com.dchip.cloudparking.wechat.model.po.MainRoadway;
import com.dchip.cloudparking.wechat.model.po.Parking;
import com.dchip.cloudparking.wechat.model.po.ParkingInfo;
import com.dchip.cloudparking.wechat.model.po.Roadway;
import com.dchip.cloudparking.wechat.model.po.User;
import com.dchip.cloudparking.wechat.model.qpo.QMainControl;
import com.dchip.cloudparking.wechat.model.qpo.QMainRoadway;
import com.dchip.cloudparking.wechat.model.qpo.QRoadway;
import com.dchip.cloudparking.wechat.pay.util.DateTimeUtil;
import com.dchip.cloudparking.wechat.utils.ParkingFeeUtil;
import com.dchip.cloudparking.wechat.utils.RetKit;
import com.dchip.cloudparking.wechat.utils.WeChatUtil;

@Service
public class RoadWayServiceImp extends BaseService implements IRoadWayService{

	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IRoadWayRepository roadWayRepository;
	@Autowired
	private IParkingRepository parkingRepository;
	@Autowired
	private ICompanyRepository companyRepository;
	@Autowired
	private IParkingInfoRepository parkingInfoRepository;
	@Autowired
	private IFreeStandardRepository freeStandardRepository;
	
	/**
	 * 开闸
	 */
	@Override
	public RetKit openBrake(String roadWayId) {
		try {
			//发送websocket
			Integer inRoadWayId = Integer.parseInt(roadWayId);
			QRoadway roadWayEntity = QRoadway.roadway;
			Roadway roadWayPoO = this.jpaQueryFactory.select(roadWayEntity).from(roadWayEntity)
					.where(roadWayEntity.id.eq(inRoadWayId)).fetchFirst();
			QMainRoadway mainRoadWayEntity = QMainRoadway.mainRoadway;
			MainRoadway mainRoadWay = this.jpaQueryFactory.select(mainRoadWayEntity).from(mainRoadWayEntity)
					.where(mainRoadWayEntity.roadwayId.eq(inRoadWayId)).fetchFirst();
			QMainControl mainControlEntity = QMainControl.mainControl;
			MainControl mainControll = this.jpaQueryFactory.select(mainControlEntity).from(mainControlEntity)
					.where(mainControlEntity.id.eq(mainRoadWay.getMainId())).fetchFirst();
			CloseableHttpClient httpclient = HttpClients.createDefault();
			StringBuffer url = new StringBuffer();
			
			url.append(WeChatUtil.DOMAIN_NAME_URL + "/websocket");
			url.append("/socketController/sendMessage");
			HttpPost post = new HttpPost(url.toString());
			String parms = "controlMac="+mainControll.getMac()+"&cameraMac="+roadWayPoO.getCameraMac()+"&command=1";
			StringEntity s;
			s = new StringEntity(parms);
			s.setContentEncoding("UTF-8");
	        s.setContentType("application/x-www-form-urlencoded");//发送json数据需要设置contentType
	        post.setEntity(s);
	        HttpResponse response;
			try {
				response = httpclient.execute(post);
				if(response.getStatusLine().getStatusCode() == 200) {
	            	String conResult = EntityUtils.toString(response.getEntity());
	            	JSONObject sobj = new JSONObject();
	            	RetKit websocketRs = sobj.parseObject(conResult, RetKit.class);
	            	if(websocketRs.getBoolean("success")) {
	            		return RetKit.ok("开闸成功");
	            	}
	            }

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return RetKit.ok();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return RetKit.fail();
		}
	}
	
	/**
	 * 无牌入场返回页面信息:公司名称，停车场名称，每小时收费，免费时长，入场时间
	 */
	public Map<String,Object> findRoadWay(String roadWayId){
		Map<String,Object> returnObj = new HashMap<>();
		RetKit openbrake = openBrake(roadWayId);
		if (openbrake.getBoolean("success")) {
			Integer inRoadWayId = Integer.parseInt(roadWayId);
			Optional<Roadway> roadwayOptional = roadWayRepository.findById(inRoadWayId);
			if (roadwayOptional.isPresent()) {
				Optional<Parking> parkingOptional = parkingRepository.findById(roadwayOptional.get().getParkingId());
				if (parkingOptional.isPresent()) {
					//User user = userRepository.findUserByOpenid(openid);
					Optional<Company> companyOptional = companyRepository.findById(parkingOptional.get().getCompanyId());
					if (companyOptional.isPresent()) {
						returnObj.put("companyName", companyOptional.get().getName());
					}
					Optional<FreeStandard> freeStandardOptional = freeStandardRepository.findById(parkingOptional.get().getFreeStandardId());
					if (freeStandardOptional.isPresent()) {
						returnObj.put("freeTimeLength", freeStandardOptional.get().getFreeTimeLength());
						returnObj.put("hourCost", freeStandardOptional.get().getHourCost());
					}
						/*//新建停车信息
						ParkingInfo parkingInfo = new ParkingInfo();
						parkingInfo.setParkCode(parkingOptional.get().getParkCode());
						parkingInfo.setLicensePlate(openid);
						parkingInfo.setParkDate(new Date());
						//parkingInfo.setUserId(user.getId());
						parkingInfo.setInRoadWayId(inRoadWayId);
						parkingInfo.setIsLock(0);//0-不锁车
						parkingInfo.setCarType(2);//默认小汽车
						parkingInfo.setPlateType(1);//默认蓝牌
						parkingInfo.setStatus(BaseConstant.parkingInfoStatus.unfinishedStatus.getValue());
						parkingInfo = parkingInfoRepository.save(parkingInfo);*/
						SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						returnObj.put("parkName", parkingOptional.get().getParkName());
						//returnObj.put("parkinginfoId",parkingInfo.getId());
						returnObj.put("parkDate", spf.format(new Date()));
				}
			}
		}
		return returnObj;
	}

	@Override
	public Map<String, Object> outAction(String roadWayId, String openid) {
		Map<String,Object> returnObj = new HashMap<>();
		User user = userRepository.findUserByOpenid(openid);
		if (user != null) {
			Optional<Roadway> roadwayOptional = roadWayRepository.findById(Integer.parseInt(roadWayId));
			if (roadwayOptional.isPresent()) {
				Roadway roadway = roadwayOptional.get();
				Integer parkingId = roadway.getParkingId();
				Parking parking = parkingRepository.findParkingById(parkingId.toString());
				if (parking != null) {
					Company company = companyRepository.findCompanyById(parking.getCompanyId());
					Integer parkCode = parking.getParkCode();
					Integer userId = user.getId();
					ParkingInfo parkingInfo = parkingInfoRepository.findByUserId(userId,parkCode);
					if (parkingInfo != null) {
						parkingInfo.setOutRoadWayId(Integer.parseInt(roadWayId));
						parkingInfo.setOutDate(new Date());
						parkingInfo = parkingInfoRepository.save(parkingInfo);
					}
					FreeStandard freeStandard = freeStandardRepository.findFreeStandardByParkingId(parkingId);
					BigDecimal fee = ParkingFeeUtil.getNewParkingFee(parkingInfo.getOutDate(), parkingInfo, freeStandard);
					String longTime = DateTimeUtil.getTimeDesc(parkingInfo.getParkDate(), parkingInfo.getOutDate());
					Integer payFlag = 0;   //支付标志   0 为未支付     1为已支付
					if((parkingInfo.getStatus()).equals(BaseConstant.parkingInfoStatus.finishedStatus.getValue())) {
						payFlag = 1;
					}
					SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					returnObj.put("fee", fee);
					returnObj.put("parkingTime", longTime);
					returnObj.put("outDate", spf.format(parkingInfo.getOutDate()));
					returnObj.put("parkName", parking.getParkName());
					returnObj.put("companyName", company.getName());
					returnObj.put("parkingInfoId", parkingInfo.getId());
					returnObj.put("parkDate", spf.format(parkingInfo.getParkDate()));
					returnObj.put("payFlag", payFlag);
				}
			}
		}
		return returnObj;
	}


}
