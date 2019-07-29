package com.dchip.cloudparking.api.controller;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;

import com.dchip.cloudparking.api.constant.BaseConstant;
import com.dchip.cloudparking.api.iRepository.IGroundLockRepository;
import com.dchip.cloudparking.api.iRepository.IMainControlRepository;
import com.dchip.cloudparking.api.model.po.GroundLock;
import com.dchip.cloudparking.api.model.po.MainControl;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.SocketKit;

public class SubThread extends Thread{
	@Autowired 
	private IGroundLockRepository groundLockRepository;
	@Autowired
	private IMainControlRepository mainControlRepository;

	private String groundUid;
	private String type;
	private String groundCommond;
	
	public SubThread(String groundUid,String type,String groundCommond, IGroundLockRepository groundLockRepository, IMainControlRepository mainControlRepository){
		this.groundUid = groundUid;
		this.type = type;
		this.groundCommond = groundCommond;
		this.groundLockRepository = groundLockRepository;
		this.mainControlRepository = mainControlRepository;
	}
	
	public void run() {
		try {
			GroundLock groundLock = groundLockRepository.findGroundLockByGroundUid(groundUid);
			if (groundLock != null && groundLock.getMainId() != null) {
				MainControl mainControl = mainControlRepository.findById(groundLock.getMainId()).get();
				String mac = mainControl.getMac();
				// 发送消息
				RetKit rs = SocketKit.sendGroundLockMessage(mac, groundUid, type, groundCommond);
				if (rs.success()) {
					if (type.equals("101")) {//状态：关闭地锁
						groundLock.setCurrentState(BaseConstant.groundLockStatus.Close.getTypeValue());
					}else {//状态：打开地锁
						groundLock.setCurrentState(BaseConstant.groundLockStatus.Open.getTypeValue());
					}
					groundLockRepository.save(groundLock);
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
