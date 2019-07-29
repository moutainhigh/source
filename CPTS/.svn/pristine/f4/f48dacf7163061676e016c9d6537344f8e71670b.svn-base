package com.dchip.cloudparking.wechat.common;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.dchip.cloudparking.wechat.utils.MessageUtil;
import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;

/**
 * @version 1.0 2017/3/2
 * @auther <a href="mailto:lly835@163.com">廖师兄</a>
 * @since 1.0
 */
@Component
public class PayConfig {

    @Bean
    public WxPayH5Config wxPayH5Config() {
        WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId("wxc84679c3bcc8d70f");
        wxPayH5Config.setAppSecret("6fd2655e8cfc19de314f3551513fe137");
        wxPayH5Config.setMchId("1507760871");
        wxPayH5Config.setMchKey("zhuhaidchip2018companycontacters");
        wxPayH5Config.setNotifyUrl(MessageUtil.loadMessage("parking.domain")+"/cloudParkingWechat/payment/notify");
        return wxPayH5Config;
    }

    @Bean
    public BestPayServiceImpl bestPayService(WxPayH5Config wxPayH5Config) {
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config);
        return bestPayService;
    }
}
