package com.dchip.cloudparking.api.serviceImp;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.api.constant.BaseConstant;
import com.dchip.cloudparking.api.iRepository.IGroundLockRepository;
import com.dchip.cloudparking.api.iRepository.ILockCommondRepository;
import com.dchip.cloudparking.api.iRepository.IMainControlRepository;
import com.dchip.cloudparking.api.iService.IGroundLockService;
import com.dchip.cloudparking.api.model.po.GroundLock;
import com.dchip.cloudparking.api.model.po.LockCommond;
import com.dchip.cloudparking.api.model.po.MainControl;
import com.dchip.cloudparking.api.model.qpo.QLockCommond;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.SocketKit;

@Service
public class GroundLockServiceImp extends BaseService implements IGroundLockService{
	@Resource
	private StringRedisTemplate stringTemplate;
	@Autowired 
	private IGroundLockRepository groundLockRepository;
	@Autowired
	private IMainControlRepository mainControlRepository;
	@Autowired
	private ILockCommondRepository lockCommondRepository;

//	@Override
//	public RetKit save(String groundUid, JSONArray jsonArray) {
//		try {
//			GroundLock gLock = groundLockRepository.findGroundLockByGroundUid(groundUid);
//			if (gLock == null) {
//				gLock = new GroundLock();
//			}
//			gLock.setGroundUid(groundUid);
//			gLock.setCreateTime(new Date());
//			gLock = groundLockRepository.save(gLock);	
//			
//			QLockCommond qLockCommond = QLockCommond.lockCommond;
//			List<LockCommond> lockCommonds = this.jpaQueryFactory.select(qLockCommond)
//					.from(qLockCommond).where(qLockCommond.groundLockId.eq(gLock.getId())).fetch();
//			if (!lockCommonds.isEmpty()) {
//				for(int i=0;i<jsonArray.size();i++) {
//					JSONObject item = (JSONObject) jsonArray.get(i);
//					LockCommond lockCommond = this.jpaQueryFactory.select(qLockCommond)
//							.from(qLockCommond).where(qLockCommond.groundLockId.eq(gLock.getId())
//									.and(qLockCommond.type.eq(item.getInteger("type")))).fetchFirst();
//					if (lockCommond == null) {
//						lockCommond = new LockCommond();
//					}
//					lockCommond.setGroundLockId(gLock.getId());
//					lockCommond.setCommond(item.get("commond").toString());
//					lockCommond.setType(item.getInteger("type"));
//					lockCommond.setCreateTime(new Date());
//					lockCommondRepository.save(lockCommond);
//				}
//			}else {
//				for(int i=0;i<jsonArray.size();i++) {
//					JSONObject item = (JSONObject) jsonArray.get(i);
//					LockCommond lCommond = new LockCommond();
//					lCommond.setGroundLockId(gLock.getId());
//					lCommond.setCommond(item.get("commond").toString());
//					lCommond.setType(item.getInteger("type"));
//					lCommond.setCreateTime(new Date());
//					lCommond = lockCommondRepository.save(lCommond);
//				}
//			}
//			return RetKit.ok(200,"Successful operation!");
//		}catch (Exception e) {
//			return RetKit.fail();
//		}
//	}
	
	@Override
	public RetKit controlGroundLock(String groundUid, String type) {
		try {
			GroundLock groundLock = groundLockRepository.findGroundLockByGroundUid(groundUid);
			if (groundLock != null) {
				Integer mainId = groundLock.getMainId();
				if (mainId != null) {
					Optional<MainControl> mainControlOptional = mainControlRepository.findById(mainId);
					if (mainControlOptional.isPresent()) {
						MainControl mainControl = mainControlOptional.get();
						String mac = mainControl.getMac();
						LockCommond lCommond = lockCommondRepository.findCommond(groundLock.getId(),type);
						if (lCommond != null && lCommond.getCommond() != null) {
							System.out.println(lCommond.getCommond());
							// 发送消息
							RetKit rs = SocketKit.sendGroundLockMessage(mac,groundUid,type,lCommond.getCommond());
							// 发送成功，缓存地锁唯一识别码，存放有效时间是1分钟
							if (rs.success()) {
								//将groundUid作为key存入redis中.
								stringTemplate.opsForValue().set(groundUid, "0", 1, TimeUnit.MINUTES);
								if (type.equals("101")) {//状态：关闭地锁
									groundLock.setCurrentState(BaseConstant.groundLockStatus.Close.getTypeValue());
								}else {//状态：打开地锁
									groundLock.setCurrentState(BaseConstant.groundLockStatus.Open.getTypeValue());
								}
								groundLockRepository.save(groundLock);
							}
							return rs;
						}else {
							return RetKit.fail("类型为"+type+"的地锁指令不存在");
						}
					}else {
						return RetKit.fail("地锁所绑定的主控板不存在");
					}
				}else {
					return RetKit.fail("地锁未绑定主控板,请先绑定主控板");
				}
			}else {
				return RetKit.fail("地锁不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return RetKit.fail();
		}
	}
	
	@Override
	public RetKit checkStatus(String groundUid) {
		String statu = stringTemplate.opsForValue().get(groundUid);
		if (statu == null) {
			return RetKit.fail(701, "overtime");
		}else if (statu.equals("1")) {
			return RetKit.ok();
		} else {
			return RetKit.fail(702, "waiting");
		}
	}

	@Override
	public RetKit reportStatus(String groundUid, String command) {
		//"1"代表操作成功
		stringTemplate.opsForValue().set(groundUid, "1");
		GroundLock groundLock = groundLockRepository.findGroundLockByGroundUid(groundUid);
		if (groundLock != null) {
			if (command.equals("102")) {//指令为'102'代表地锁打开成功
				groundLock.setCurrentState(BaseConstant.groundLockStatus.Open.getTypeValue());
			}else {
				groundLock.setCurrentState(BaseConstant.groundLockStatus.Close.getTypeValue());
			}
			groundLockRepository.save(groundLock);
			return RetKit.ok();
		}else {
			return RetKit.fail("该地锁不存在");
		}
	}

	@Override
	public RetKit findGroundLockInfo(String licensePlate) {
		List<Map<String, Object>> groundLockList = groundLockRepository.findGroundLock(licensePlate);
		return RetKit.okData(groundLockList);
	}

	@Override
	public RetKit save(Map<String, Object> datas) {
		try {
			String groundUid = datas.get("groundUid").toString();
			GroundLock gLock = groundLockRepository.findGroundLockByGroundUid(groundUid);
			if (gLock == null) {
				gLock = new GroundLock();
			}
			gLock.setCurrentState(BaseConstant.groundLockStatus.Close.getTypeValue());//原始是关闭状态
			gLock.setGroundUid(groundUid);
			gLock.setCreateTime(new Date());
			gLock = groundLockRepository.save(gLock);	
			
			QLockCommond qLockCommond = QLockCommond.lockCommond;
			List<LockCommond> lockCommonds = this.jpaQueryFactory.select(qLockCommond)
					.from(qLockCommond).where(qLockCommond.groundLockId.eq(gLock.getId())).fetch();
			String type1 = datas.get("type1").toString();
			String type2 = datas.get("type2").toString();
			if (!lockCommonds.isEmpty()) {
					LockCommond lockCommond = this.jpaQueryFactory.select(qLockCommond)
							.from(qLockCommond).where(qLockCommond.groundLockId.eq(gLock.getId())
									.and(qLockCommond.type.eq(Integer.parseInt(type1)))).fetchFirst();
					if (lockCommond == null) {
						lockCommond = new LockCommond();
					}
					lockCommond.setGroundLockId(gLock.getId());
					lockCommond.setCommond(datas.get("commond1").toString());
					lockCommond.setType(Integer.parseInt(type1));
					lockCommond.setCreateTime(new Date());
					lockCommondRepository.save(lockCommond);
					LockCommond lockCommond1 = this.jpaQueryFactory.select(qLockCommond)
							.from(qLockCommond).where(qLockCommond.groundLockId.eq(gLock.getId())
									.and(qLockCommond.type.eq(Integer.parseInt(type2)))).fetchFirst();
					if (lockCommond1 == null) {
						lockCommond1 = new LockCommond();
					}
					lockCommond1.setGroundLockId(gLock.getId());
					lockCommond1.setCommond(datas.get("commond2").toString());
					lockCommond1.setType(Integer.parseInt(type2));
					lockCommond1.setCreateTime(new Date());
					lockCommondRepository.save(lockCommond1);
			}else {
				LockCommond lockCommond = new LockCommond();
				lockCommond.setGroundLockId(gLock.getId());
				lockCommond.setCommond(datas.get("commond1").toString());
				lockCommond.setType(Integer.parseInt(type1));
				lockCommond.setCreateTime(new Date());
				lockCommondRepository.save(lockCommond);
				
				LockCommond lockCommond1 = new LockCommond();
				lockCommond1.setGroundLockId(gLock.getId());
				lockCommond1.setCommond(datas.get("commond2").toString());
				lockCommond1.setType(Integer.parseInt(type2));
				lockCommond1.setCreateTime(new Date());
				lockCommondRepository.save(lockCommond1);
			}
			return RetKit.ok(200,"Successful operation!");
		}catch (Exception e) {
			return RetKit.fail();
		}
	}

}
