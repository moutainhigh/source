package com.dchip.cloudparking.wechat.iRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dchip.cloudparking.wechat.model.po.WechatConfig;

@Repository
public interface IWechatConfigRepository extends JpaRepository<WechatConfig, Integer> {

}
