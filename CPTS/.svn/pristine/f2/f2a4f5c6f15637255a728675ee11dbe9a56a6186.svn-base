package com.dchip.cloudparking.api.serviceImp;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.dchip.cloudparking.api.constant.BaseConstant;
import com.dchip.cloudparking.api.iRepository.IMainControlRepository;
import com.dchip.cloudparking.api.iRepository.IMainControlVersionRepository;
import com.dchip.cloudparking.api.iRepository.IVersionRepository;
import com.dchip.cloudparking.api.iService.IMainControlService;
import com.dchip.cloudparking.api.model.po.MainControl;
import com.dchip.cloudparking.api.model.po.MainControlVersion;
import com.dchip.cloudparking.api.model.po.Version;
import com.dchip.cloudparking.api.utils.QiniuUtil;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;
@Service
public class MainControlServiceImp implements IMainControlService {

	@Autowired
	private IMainControlRepository mainControlRepository;
	@Autowired
	private IMainControlVersionRepository mainControlVersionRepository;
	@Autowired
	private IVersionRepository versionRepository;
	public RetKit uploadMainControl(String mac, String networkType, String type) {
		try {
			Integer typ = 100;
			if (StrKit.notBlank(type)) {
				typ = Integer.parseInt(type);
			}
			MainControl mainControl = new MainControl();
			mainControl.setMac(mac);
			Example<MainControl> example = Example.of(mainControl);
			Optional<MainControl> result = mainControlRepository.findOne(example);
			if(result.isPresent()) {
				mainControl = result.get();
				mainControl.setCreateTime(new Date());
				mainControl.setType(typ);
				if(mainControl.getStatus() == BaseConstant.MainControlStatus.DeleteState.getTypeValue()) {
					mainControl.setStatus(BaseConstant.MainControlStatus.EnabledState.getTypeValue());
				}
				return mainControlRepository.save(mainControl) != null ? RetKit.ok() : RetKit.fail();
			}else {
				mainControl = new MainControl();
				mainControl.setNetworkType(Integer.parseInt(networkType));
				mainControl.setMac(mac);
				mainControl.setCreateTime(new Date());
				mainControl.setStatus(BaseConstant.MainControlStatus.EnabledState.getTypeValue());
				mainControl.setType(typ);
				return mainControlRepository.save(mainControl) != null ? RetKit.ok() : RetKit.fail();
			}
		} catch (Exception e) {
			return RetKit.fail();
		}
	}

	@Override
	public RetKit latestVersion(String type) {
		if (StrKit.isBlank(type)) {
			return RetKit.fail("type不能为空");
		}
		Version version = versionRepository.findLatestVersionByType(Integer.parseInt(type));
		if(version == null) {
			return RetKit.fail("版本信息不存在");
		}else {
			if(StrKit.isBlank(version.getAddress())) {
				return RetKit.fail("版本信息不存在");
			}
			version.setAddress("http://"+QiniuUtil.getDomain()+"/"+version.getAddress());
			return RetKit.okData(version);
		}
	}

	@Override
	public RetKit uploadMainControlVersion(String mac, String version, String type) {
		try {
			if (StrKit.isBlank(mac)) {
				return RetKit.fail("Mac不能为空");
			}
			if (StrKit.isBlank(version)) {
				return RetKit.fail("version不能为空");
			}
			if (StrKit.isBlank(type)) {
				return RetKit.fail("type不能为空");
			}
			MainControl mainControl = mainControlRepository.findMainControl(mac);
			boolean o = false;
			// 判断Mac是否存在，如果存在就更新信息，没有存在则新增一条数据
			if (mainControl == null) {
				return RetKit.fail("主控板不存在");
			} else {
				MainControlVersion mainControlVersion = mainControlVersionRepository.findMainControlVersionByMac(mac);
				if (mainControlVersion != null) {
					mainControlVersion.setCreateTime(new Date());
					mainControlVersion.setCurrentVersion(version);
					mainControlVersion.setType(Integer.parseInt(type));
				} else {
					mainControlVersion = new MainControlVersion();
					mainControlVersion.setCurrentVersion(version);
					mainControlVersion.setCreateTime(new Date());
					mainControlVersion.setMac(mac);
					mainControlVersion.setType(Integer.parseInt(type));
				}

				return mainControlVersionRepository.save(mainControlVersion) != null ? RetKit.ok() : RetKit.fail();
			}
		} catch (Exception e) {
			return RetKit.fail();
		}
	}
}
