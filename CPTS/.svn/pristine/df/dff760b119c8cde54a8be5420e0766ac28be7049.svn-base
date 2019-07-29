package com.dchip.cloudparking.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dchip.cloudparking.service.ApiConsumeService;
import com.dchip.cloudparking.utils.MessageUtil;
import com.dchip.cloudparking.utils.RetKit;
import com.dchip.cloudparking.utils.StrKit;
import com.jfinal.kit.HttpKit;

@RestController
@RequestMapping("/cloudParkingApi")
public class ApiConsumeController {
    @Autowired
    ApiConsumeService apiConsumeService;
    @Autowired
    private RestTemplate restTemplate;

    Log log = LogFactory.getLog(ApiConsumeController.class);

    /**
     * 发送短信验证码接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/user/sendVerificationCode")
    public Object sendVerificationCode(HttpServletRequest request) {
        String phone = request.getParameter("phone");
        if (StrKit.isBlank(phone)) {
            return RetKit.fail("手机号码不能为空");
        }
        return JSON.parse(restTemplate.getForObject("http://api/user/sendVerificationCode?phone=" + phone, String.class));
    }

    /**
     * 获取订单详情
     *
     * @param request
     * @return
     */
    @RequestMapping("/order/getOrderDetail")
    public Object getOrderDetail(HttpServletRequest request) {
        String orderId = request.getParameter("orderId");
        if (orderId == null) {
            return RetKit.fail("订单id不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/order/getOrderDetail?orderId=" + orderId, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 获取订单（账单）列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/order/getOrders")
    public Object getOrders(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String pageSize = request.getParameter("pageSize");
        String pageNum = request.getParameter("pageNum");
        if (userId == null) {
            return RetKit.fail("用户不能为空");
        }
        if (pageSize == null) {
            return RetKit.fail("pageSize不能为空");
        }
        if (pageNum == null) {
            return RetKit.fail("pageNum不能为空");
        }
        String parameters = "userId=" + userId + "&pageSize=" + pageSize + "&" + "pageNum=" + pageNum;
        return JSON.parse(restTemplate.postForObject("http://api/order/getOrders?" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 获取我的消息
     *
     * @param request
     * @return
     */
    @RequestMapping("/message/getMessage")
    public Object getMessage(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String pageSize = request.getParameter("pageSize");
        String pageNum = request.getParameter("pageNum");
        if (userId == null) {
            return RetKit.fail("用户不能为空");
        }
        if (pageSize == null) {
            return RetKit.fail("pageSize不能为空");
        }
        if (pageNum == null) {
            return RetKit.fail("pageNum不能为空");
        }
        String parameters = "userId=" + userId + "&pageSize=" + pageSize + "&pageNum=" + pageNum;
        return JSON.parse(restTemplate.postForObject("http://api/message/getMessage?" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 获取总积分
     *
     * @param request
     * @return
     */
    @RequestMapping("/user/getTotalScore")
    public Object getTotalScore(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        if (userId == null) {
            userId = "";
        }
        return JSON.parse(restTemplate.postForObject("http://api/user/getTotalScore?userId=" + userId, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 获取积分明细
     *
     * @param request
     * @return
     */
    @RequestMapping("/user/getScoreDetail")
    public Object getScoreDetail(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        if (userId == null) {
            userId = "";
        }
        String year = request.getParameter("year");
        if (year == null) {
            year = "";
        }
        String month = request.getParameter("month");
        if (month == null) {
            month = "";
        }
        String parameters = "userId=" + userId + "&year=" + year + "&" + "month=" + month;
        return JSON.parse(restTemplate.postForObject("http://api/user/getScoreDetail?" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 提交预约
     *
     * @param request
     * @return
     */
    @RequestMapping("/appointment/submit")
    public Object appointmentSubmit(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        if (userId == null) {
            return RetKit.fail("用户id不能为空");
        }
        String parkingId = request.getParameter("parkingId");
        if (parkingId == null) {
            return RetKit.fail("停车场id不能为空");
        }
        String date = request.getParameter("date");
        if (date == null) {
            return RetKit.fail("日期不能为空");
        }
        String licensePlat = request.getParameter("licensePlat");
        if (licensePlat == null) {
            return RetKit.fail("车牌号不能为空");
        }
        String parameters = "userId=" + userId + "&parkingId=" + parkingId + "&date=" + date + "&licensePlat=" + licensePlat;
        return JSON.parse(restTemplate.postForObject("http://api/appointment/submit?" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 获取预约列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/appointment/getAppointments")
    public Object getAppointments(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        if (userId == null) {
            userId = "";
        }
        String parameters = "userId=" + userId;
        return JSON.parse(restTemplate.postForObject("http://api/appointment/getAppointments?" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 用户登录接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/user/usePhoneLogin")
    public Object usePhoneLogin(HttpServletRequest request) {
        String phone = request.getParameter("phone");
        String code = request.getParameter("code");
        String registrationId = request.getParameter("registrationId");
        String licensePlat = request.getParameter("licensePlat");  //车牌号码
        String platform = request.getHeader("platform");
        if (StrKit.isBlank(phone)) {
            return RetKit.fail("手机号码不能为空");
        }
        if (StrKit.isBlank(platform)) {
            return RetKit.fail("platform不能为空");
        }
        if (StrKit.isBlank(licensePlat)) {
            return RetKit.fail("licensePlat不能为空");
        }
        String splicingURL = "?phone=" + phone + "&code=" + code + "&registrationId=" + registrationId + "&licensePlat=" + licensePlat;
        HttpEntity<String> formEntity = apiConsumeService.getHttpEntityByRequst(request);
        return JSON.parse(restTemplate.postForObject("http://api/user/usePhoneLogin" + splicingURL, formEntity, String.class));
    }

    /**
     * app扫码产生临时车牌
     */
    @RequestMapping("/parking/scanToGeneratLicensePlate")
    public Object scanToGeneratLicensePlate(HttpServletRequest request) {
        return JSON.parse(restTemplate.postForObject("http://api/parking/scanToGeneratLicensePlate", apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 扫码结算(无牌车)
     */
    @RequestMapping("/parking/scanToSettlement")
    public Object scanToSettlement(HttpServletRequest request) {
        String parkCode = request.getParameter("parkCode");
        String userIdStr = request.getParameter("userId");
        String parkInfoIdStr = request.getParameter("parkingInfoId");
        String splicingURL = "?parkCode=" + parkCode + "&userId=" + userIdStr + "&parkingInfoId=" + parkInfoIdStr;
        HttpEntity<String> formEntity = apiConsumeService.getHttpEntityByRequst(request);
        return JSON.parse(restTemplate.postForObject("http://api/parking/scanToSettlement" + splicingURL, formEntity, String.class));
    }

    /**
     * 黑名单用户数据查询
     *
     * @param req
     * @return
     */
    @RequestMapping("/blackList/getBlackUserInfo")
    public Object findBlackUserInfoByUserId(HttpServletRequest req) {
        String userId = req.getParameter("userId");
        if (userId == null) {
            userId = "";
        }
        return JSON.parse(restTemplate.postForObject("http://api/blackList/getBlackUserInfo?userId=" + userId, apiConsumeService.getHttpEntityByRequst(req), String.class));
    }

    /**
     * 查询要注册的车牌是否已存在
     *
     * @param req
     * @return
     */
    @RequestMapping("/LicensePlat/validateLicensePlate")
    public Object validateLicensePlate(HttpServletRequest req) {
        String licensePlat = req.getParameter("licensePlat");
        if (licensePlat == null) {
            licensePlat = "";
        }
        return JSON.parse(restTemplate.postForObject("http://api/LicensePlat/validateLicensePlate?licensePlat=" + licensePlat, apiConsumeService.getHttpEntityByRequst(req), String.class));
    }

    /**
     * 解绑车牌
     * @param req
     * @return
     */
	/*@RequestMapping("/LicensePlat/deleteLicensePlate")
	public Object deleteLicensePlate(HttpServletRequest req) {
		String licensePlatId = req.getParameter("licensePlatId");
		if(licensePlatId == null) {
			licensePlatId="";
		}
		return JSON.parse(restTemplate.postForObject("http://api/LicensePlat/deleteLicensePlate?licensePlatId="+licensePlatId,apiConsumeService.getHttpEntityByRequst(req),String.class));
	}*/

    /**
     * 改变车辆状态 0-不锁车，1-锁车
     *
     * @param req
     * @return
     */
    @RequestMapping("/parkingInfo/changeCarStatus")
    public Object changeCarStatus(HttpServletRequest req) {
        String isLock = req.getParameter("isLock");
        String parkingInfoId = req.getParameter("parkingInfoId");
        if (isLock == null) {
            isLock = "";
        }
        if (parkingInfoId == null) {
            parkingInfoId = "";
        }
        String splicingURL = "?isLock=" + isLock + "&parkingInfoId=" + parkingInfoId;
        return JSON.parse(restTemplate.postForObject("http://api/parkingInfo/changeCarStatus" + splicingURL, apiConsumeService.getHttpEntityByRequst(req), String.class));
    }

    /**
     * 根据停车场ID查找出管理服务中心电话
     *
     * @param req
     * @return
     */
    @RequestMapping("/parking/getManagerPhone")
    public Object findPhoneByParkingId(HttpServletRequest req) {
        String parkingId = req.getParameter("parkingId");
        if (parkingId == null) {
            parkingId = "";
        }
        return JSON.parse(restTemplate.postForObject("http://api/parking/getManagerPhone?parkingId=" + parkingId, apiConsumeService.getHttpEntityByRequst(req), String.class));
    }

    /**
     * 获取预约费用
     *
     * @param request
     * @return
     */
    @RequestMapping("/appointment/getFee")
    public Object getFee(HttpServletRequest request) {
        String parkingId = request.getParameter("parkingId");
        String date = request.getParameter("date");
        if (parkingId == null) {
            parkingId = "";
        }
        if (date == null) {
            date = "";
        }
        String parameters = "?parkingId=" + parkingId + "&date=" + date;
        return JSON.parse(restTemplate.postForObject("http://api/appointment/getFee" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 停车场车位信息等情况接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/parking/findParkingByParkingId")
    public Object findParkingByParkingId(HttpServletRequest request) {
        String parkingId = request.getParameter("parkingId");
        if (parkingId == null) {
            return RetKit.fail("parkingId不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/parking/findParkingByParkingId?parkingId=" + parkingId, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 停车场信息及计费信息接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/parkingInfo/getParkingInfoByUserId")
    public Object getParkingInfoByUserId(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        if (userId == null) {
            return RetKit.fail("userId不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/parkingInfo/getParkingInfoByUserId?userId=" + userId, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 账户余额查询接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/user/getBalance")
    public Object getBalance(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        if (userId == null) {
            return RetKit.fail("userId不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/user/getBalance?userId=" + userId, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 按月查询总消费接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/order/findMonthFee")
    public Object findMonthFee(HttpServletRequest request) {
        String payTime = request.getParameter("payTime");
        if (payTime == null) {
            return RetKit.fail("payTime不能为空");
        }
        String userId = request.getParameter("userId");
        if (userId == null) {
            return RetKit.fail("userId不能为空");
        }
        String parameters = "payTime=" + payTime + "&userId=" + userId;
        return JSON.parse(restTemplate.postForObject("http://api/order/findMonthFee?" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 按月查询充值总额接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/rechargeLog/findMonthMoney")
    public Object findMonthAmount(HttpServletRequest request) {
        String rechargeDate = request.getParameter("rechargeDate");
        if (rechargeDate == null) {
            return RetKit.fail("rechargeDate不能为空");
        }
        String userId = request.getParameter("userId");
        if (userId == null) {
            return RetKit.fail("userId不能为空");
        }
        String parameters = "rechargeDate=" + rechargeDate + "&userId=" + userId;
        return JSON.parse(restTemplate.postForObject("http://api/rechargeLog/findMonthMoney?" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 按月维度查询当前年份消费柱状图数据接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/order/findFeeByUserIdAndYear")
    public Object findFeeByUserIdAndYear(HttpServletRequest request) {
        String year = request.getParameter("year");
        if (year == null) {
            return RetKit.fail("year不能为空");
        }
        String userId = request.getParameter("userId");
        if (userId == null) {
            return RetKit.fail("userId不能为空");
        }
        String parameters = "year=" + year + "&userId=" + userId;
        return JSON.parse(restTemplate.postForObject("http://api/order/findFeeByUserIdAndYear?" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 会员判断接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/user/getUserGrade")
    public Object getUserGrade(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        if (userId == null) {
            return RetKit.fail("userId不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/user/getUserGrade?userId=" + userId, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 按月查询消费明细接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/order/findOrderByUserIdAndPayTime")
    public Object findOrderByUserIdAndPayTime(HttpServletRequest request) {
        String payTime = request.getParameter("payTime");
        if (payTime == null) {
            return RetKit.fail("payTime不能为空");
        }
        String userId = request.getParameter("userId");
        if (userId == null) {
            return RetKit.fail("userId不能为空");
        }
        String parameters = "payTime=" + payTime + "&userId=" + userId;
        return JSON.parse(restTemplate.postForObject("http://api/order/findOrderByUserIdAndPayTime?" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 获取会员等级列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/memberRule/getRule")
    public RetKit getRule(HttpServletRequest request) {
        return restTemplate.postForObject("http://api/memberRule/getRule", apiConsumeService.getHttpEntityByRequst(request), RetKit.class);
    }

    /**
     * 按月查询充值明细接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/rechargeLog/getMoneyByRechargeDate")
    public Object getAmountByCreateTime(HttpServletRequest request) {
        String rechargeDate = request.getParameter("rechargeDate");
        if (rechargeDate == null) {
            return RetKit.fail("rechargeDate不能为空");
        }
        String userId = request.getParameter("userId");
        if (userId == null) {
            return RetKit.fail("userId不能为空");
        }
        String parameters = "rechargeDate=" + rechargeDate + "&userId=" + userId;
        return JSON.parse(restTemplate.postForObject("http://api/rechargeLog/getMoneyByRechargeDate?" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 绑定车牌
     *
     * @param request
     * @return
     */
    @RequestMapping("/LicensePlat/binding")
    public Object binding(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String plateLicense = request.getParameter("plateLicense");
        String realName = request.getParameter("realName");
        String drivingLicenseCode = request.getParameter("drivingLicenseCode");
        String idCard = request.getParameter("idCard");
        String driverLicenseCode = request.getParameter("driverLicenseCode");
        if (userId == null) {
            return RetKit.fail("用户id不能为空");
        }
        if (plateLicense == null) {
            return RetKit.fail("车牌号不能为空");
        }
        if (realName == null) {
            return RetKit.fail("车主姓名不能为空");
        }
        if (drivingLicenseCode == null) {
            return RetKit.fail("行驶证编号不能为空");
        }
        if (idCard == null) {
            return RetKit.fail("身份证号码不能为空");
        }
        if (driverLicenseCode == null) {
            return RetKit.fail("驾驶证编号不能为空");
        }
        String parameters = "?userId=" + userId + "&plateLicense=" + plateLicense + "&realName=" + realName
                + "&drivingLicenseCode=" + drivingLicenseCode + "&idCard=" + idCard + "&driverLicenseCode=" + driverLicenseCode;
        return JSON.parse(restTemplate.postForObject("http://api/LicensePlat/binding" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 获取用户绑定信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/LicensePlat/getBindingInfo")
    public Object getBindingInfo(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        if (userId == null) {
            return RetKit.fail("用户id不能为空");
        }
        String parameters = "?userId=" + userId;
        return JSON.parse(restTemplate.postForObject("http://api/LicensePlat/getBindingInfo" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 查找附近的停车场
     *
     * @param request
     * @return
     */
    @RequestMapping("/parking/findNeighborhoodParking")
    public Object findNeighborhoodParking(HttpServletRequest request) {
        String longitude = request.getParameter("longitude");
        String latitude = request.getParameter("latitude");
        String distance = request.getParameter("distance");
        if (longitude == null) {
            return RetKit.fail("经度不能为空");
        }
        if (latitude == null) {
            return RetKit.fail("纬度不能为空");
        }
        if (distance == null) {
            return RetKit.fail("距离不能为空");
        }
        String parameters = "?longitude=" + longitude + "&latitude=" + latitude + "&distance=" + distance;
        return JSON.parse(restTemplate.postForObject("http://api/parking/findNeighborhoodParking" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 查找附近的停车场
     *
     * @param request
     * @return
     */
    @RequestMapping("/parking/search")
    public Object search(HttpServletRequest request) {
        String name = request.getParameter("name");
        if (name == null) {
            return RetKit.fail("名称不能为空");
        }
        String parameters = "?name=" + name;
        return JSON.parse(restTemplate.postForObject("http://api/parking/search" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 查找具体的停车场
     *
     * @param request
     * @return
     */
    @RequestMapping("/parking/findParking")
    public Object findParking(HttpServletRequest request) {
        String parkingId = request.getParameter("parkingId");
        if (parkingId == null) {
            return RetKit.fail("停车场id不能为空");
        }
        String parameters = "?parkingId=" + parkingId;
        return JSON.parse(restTemplate.postForObject("http://api/parking/findParking" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));

    }

    /**
     * @param request
     * @return
     */
    @RequestMapping("/user/checkDiscounts")
    public Object checkDiscounts(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        if (userId == null) {
            return RetKit.fail("用户id不能为空");
        }
        String amount = request.getParameter("amount");
        if (amount == null) {
            return RetKit.fail("充值的金额不能为空");
        }
        String parameters = "?userId=" + userId + "&amount=" + amount;
        return JSON.parse(restTemplate.postForObject("http://api/user/checkDiscounts" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 用户退出登录
     *
     * @param request
     * @return
     */
    @RequestMapping("/user/loginOut")
    public Object logOut(HttpServletRequest request) {
        return JSON.parse(restTemplate.postForObject("http://api/user/loginOut", apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 查询用户绑定过的车牌   zyy
     *
     * @param request
     * @return
     */
    @RequestMapping("/LicensePlat/findLicensePlate")
    public Object findLicensePlateByUserId(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        if (StrKit.isBlank(userId)) {
            return RetKit.fail("userId不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/LicensePlat/findLicensePlate?userId=" + userId, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 获取用户详情
     *
     * @param request
     * @return
     */
    @RequestMapping("/user/getUserDetails")
    public Object getUserDetails(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        if (StrKit.isBlank(userId)) {
            return RetKit.fail("userId不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/user/getUserDetails?userId=" + userId, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 更换手机号
     *
     * @param request
     * @return
     */
    @RequestMapping("/user/changePhone")
    public Object changePhone(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String newPhone = request.getParameter("newPhone");
        String code = request.getParameter("code");
        if (StrKit.isBlank(userId)) {
            return RetKit.fail("用户id不能为空");
        }
        if (StrKit.isBlank(newPhone)) {
            return RetKit.fail("新手机号不能为空");
        }
        if (StrKit.isBlank(code)) {
            return RetKit.fail("验证码不能为空");
        }
        String parameters = "?userId=" + userId + "&newPhone=" + newPhone + "&code=" + code;
        return JSON.parse(restTemplate.postForObject("http://api/user/changePhone" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));

    }

    /**
     * 将消息状态改为已读
     *
     * @param request
     * @return
     */
    @RequestMapping("/message/changeToRead")
    public Object changeToRead(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String messageId = request.getParameter("messageId");
        if (StrKit.isBlank(userId)) {
            return RetKit.fail("userId不能为空");
        }
        if (StrKit.isBlank(messageId)) {
            return RetKit.fail("messageId不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/message/changeToRead?userId=" + userId + "&messageId=" + messageId, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 查询用户充值记录
     *
     * @param request
     * @return
     */
    @RequestMapping("/rechargeLog/findRechargeLog")
    public Object findRechargeLog(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String pageSize = request.getParameter("pageSize");
        String pageNum = request.getParameter("pageNum");
        if (userId == null) {
            return RetKit.fail("用户不能为空");
        }
        if (pageSize == null) {
            return RetKit.fail("pageSize不能为空");
        }
        if (pageNum == null) {
            return RetKit.fail("pageNum不能为空");
        }
        String parameters = "userId=" + userId + "&pageSize=" + pageSize + "&pageNum=" + pageNum;
        return JSON.parse(restTemplate.postForObject("http://api/rechargeLog/findRechargeLog?" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 按月查询消费记录
     *
     * @param request
     * @return
     */
    @RequestMapping("/order/findMonthOrder")
    public Object findMonthOrder(HttpServletRequest request) {
        String payTime = request.getParameter("payTime");
        if (payTime == null) {
            return RetKit.fail("payTime不能为空");
        }
        String userId = request.getParameter("userId");
        if (userId == null) {
            return RetKit.fail("userId不能为空");
        }
        String parameters = "payTime=" + payTime + "&userId=" + userId;
        return JSON.parse(restTemplate.postForObject("http://api/order/findMonthOrder?" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 改变订单状态，并根据订单费用扣除用户余额
     *
     * @param request
     * @return
     */
    @RequestMapping("/order/changeOrderStatus")
    public Object changeOrderStatus(HttpServletRequest request) {
        String orderId = request.getParameter("orderId");
        if (StrKit.isBlank(orderId)) {
            return RetKit.fail("orderId不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/order/changeOrderStatus?orderId=" + orderId, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 充值
     */
    @RequestMapping("/payment/recharge")
    public Object recharge(HttpServletRequest request) {
        String strUserId = request.getParameter("userId");// 用户ID
        String strType = request.getParameter("type");// 1-支付宝 2-微信
        String strRcId = request.getParameter("rcId");// recharge_coupon表ID

        if (StrKit.isBlank(strUserId) || StrKit.isBlank(strType) || StrKit.isBlank(strRcId)) {
            return RetKit.fail("参数缺少");
        }

        String parameters = "userId=" + strUserId + "&type=" + strType + "&rcId=" + strRcId;
        return JSON.parse(restTemplate.postForObject("http://api/payment/recharge?" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }


    /**
     * 取消预约
     *
     * @param request
     * @return
     */
    @RequestMapping("/appointment/cancel")
    public Object cancel(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        if (userId == null) {
            return RetKit.fail("userId不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/appointment/cancel?userId=" + userId, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 有牌车进出场
     *
     * @param request
     * @return
     */
    @RequestMapping("/parkingInfo/parking")
    public Object parkNumParkingIn(HttpServletRequest request) {
        //主板mac
        String mac = request.getParameter("mac");
        //车牌号
        String carNum = request.getParameter("carNum");
        //车牌类型
        String plateType = request.getParameter("plateType");
        plateType = (plateType == null ? "" : plateType);
        //车辆类型
        String carType = request.getParameter("carType");
        carType = (carType == null ? "" : carType);
        //车照片
        String img = request.getParameter("imgUrl");
        img = (img == null ? "" : img);
        //入场时间
        String dateStr = request.getParameter("inDate");
        if (StrKit.isBlank(mac)) {
            return RetKit.fail(MessageUtil.loadMessage("parking.param.error.null", "MAC信息"));
        }
        if (StrKit.isBlank(carNum)) {
            return RetKit.fail(MessageUtil.loadMessage("parking.param.error.null", "车牌"));
        }
        if (StrKit.isBlank(dateStr)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateStr = sdf.format(new Date());
        }
        return JSON.parse(
                restTemplate.postForObject("http://api/parkingInfo/parking?mac=" + mac + "&carNum=" + carNum + "&img="
                        + img + "&inDate=" + dateStr + "&plateType=" + plateType + "&carType=" + carType, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 查询充值钱数
     */
    @RequestMapping("/rechargeCoupon/findRechargeCoupon")
    public Object findInMoney(HttpServletRequest request) {
        return JSON.parse(restTemplate.postForObject("http://api/rechargeCoupon/findRechargeCoupon", apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 我的停车场
     *
     * @param request
     * @return
     */
    @RequestMapping("/userParking/getInfo")
    public Object findMonthlyCardAndParkingInfo(HttpServletRequest request) {
        String licensePlat = request.getParameter("licensePlat");
        String statue = request.getParameter("statue");
        if (licensePlat == null) {
            return RetKit.fail("licensePlat不能为空");
        }
        if (statue == null) {
            return RetKit.fail("statue不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/userParking/getInfo?licensePlat=" + licensePlat + "&statue=" + statue , apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 上传主控板mac
     * --leif
     *
     * @param request
     * @return
     */
    @RequestMapping("/mainControl/uploadMainControl")
    public Object uploadMainControl(HttpServletRequest request) {
        String mac = request.getParameter("mac");
        String networkType = request.getParameter("networkType");
        String type = request.getParameter("type");
        if (StrKit.isBlank(mac)) {
            return RetKit.fail("mac不能为空");
        }
        if (StrKit.isBlank(networkType)) {
            return RetKit.fail("networkType不能为空");
        }
        if (StrKit.isBlank(type)) {
            return RetKit.fail("type不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/mainControl/uploadMainControl?mac=" + mac + "&networkType=" + networkType + "&type=" + type, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 根据主控板mac获取摄像机的一些信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/hardware/getCameraInfo")
    public Object getCameraInfo(HttpServletRequest request) {
        String mac = request.getParameter("mac");
        if (StrKit.isBlank(mac)) {
            return RetKit.fail("mac不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/hardware/getCameraInfo?mac=" + mac, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    @RequestMapping("/hardware/changeCarportNum")
    public Object changeCarportNum(HttpServletRequest request) {
        String mac = request.getParameter("mac");
        if (StrKit.isBlank(mac)) {
            return RetKit.fail("mac不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/hardware/changeCarportNum?mac=" + mac, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 月卡续费
     *
     * @param request
     * @return
     */
    @RequestMapping("/userParking/renew")
    public Object renewMonthlyCard(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String monthlyCardId = request.getParameter("monthlyCardId");
        if (StrKit.isBlank(userId)) {
            return RetKit.fail("userId不能为空");
        }
        if (StrKit.isBlank(monthlyCardId)) {
            return RetKit.fail("monthlyCardId不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/userParking/renew?userId=" + userId + "&monthlyCardId=" + monthlyCardId, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 获取用户的预约信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/appointment/getUserAppointmentInfo")
    public Object getUserAppointmentInfo(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String pageSize = request.getParameter("pageSize");
        String pageNum = request.getParameter("pageNum");
        if (StrKit.isBlank(userId)) {
            return RetKit.fail("userId不能为空");
        }
        if (StrKit.isBlank(pageSize)) {
            return RetKit.fail("pageSize不能为空");
        }
        if (StrKit.isBlank(pageNum)) {
            return RetKit.fail("pageNum不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/appointment/getUserAppointmentInfo?userId=" + userId + "&pageSize=" + pageSize + "&pageNum=" + pageNum, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 获取用户是否认证的flag   0-未认证  1-认证通过   2-认证不通过
     *
     * @param request
     * @return
     */
    @RequestMapping("/user/getAuthenticationFlag")
    public Object getAuthenticationFlag(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        if (StrKit.isBlank(userId)) {
            return RetKit.fail("userId不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/user/getAuthenticationFlag?userId=" + userId, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 微信回调
     */
    @RequestMapping("/payment/weixin_notify")
    public Object weixin_notify(HttpServletRequest request, HttpServletResponse response) {
        String xmlMsg = HttpKit.readData(request);
        return JSON.parse(restTemplate.postForObject("http://api/payment/weixin_notify?xmlMsg=" + xmlMsg, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 支付宝回调
     */
    @RequestMapping("/payment/ali_notify")
    public Object ali_notify(HttpServletRequest request, HttpServletResponse response) {
        String trade_status = request.getParameter("trade_status");
        String out_trade_no = request.getParameter("out_trade_no");
        log.info("XXXXXXXXXXXXXXXXXXX:trade_status" + trade_status);
        log.info("XXXXXXXXXXXXXXXXXXX:out_trade_no" + out_trade_no);
        return JSON.parse(restTemplate.postForObject("http://api/payment/ali_notify?trade_status=" + trade_status + "&out_trade_no=" + out_trade_no, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    @RequestMapping("/help/aboutPoint")
    public Object aboutPoint(HttpServletRequest request) {
        return JSON.parse(restTemplate.postForObject("http://api//help/aboutPoint", apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    @RequestMapping("/help/getHelp")
    public Object getHelp(HttpServletRequest request) {
        return JSON.parse(restTemplate.postForObject("http://api/help/getHelp", apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 根据车道mac(相机mac)修改在线状态
     * --leif
     *
     * @param request
     * @return
     */
    @RequestMapping("/roadway/changeRoadwayOnline")
    public Object changeRoadwayOnline(HttpServletRequest request) {
        String cameraMac = request.getParameter("cameraMac");
        String online = request.getParameter("online");
        if (StrKit.isBlank(cameraMac)) {
            return RetKit.fail("cameraMac不能为空");
        }
        if (StrKit.isBlank(online)) {
            return RetKit.fail("online不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/roadway/changeRoadwayOnline?cameraMac=" + cameraMac + "&online=" + online, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 返回服务器时间
     * --leif
     *
     * @param request
     * @return
     */
    @RequestMapping("/roadway/getServerTime")
    public Object getServerTime(HttpServletRequest request) {
        return JSON.parse(restTemplate.postForObject("http://api/roadway/getServerTime", apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 车位租让 信息发布
     *
     * @param request
     */
    @RequestMapping(value = "/parkingConcession/publishMsg")
    public Object publishMsg(HttpServletRequest request) {
        String parkingId = "parkingId=" + request.getParameter("parkingId");
        String licensePlate = "licensePlate=" + request.getParameter("licensePlate");
        String spaceNo = "spaceNo=" + request.getParameter("spaceNo");
        String userId = "userId=" + request.getParameter("userId");
        String cost = "cost=" + request.getParameter("cost");
        String effectDuringTime = "effectDuringTime=" + request.getParameter("effectDuringTime");
        String effectDuringDate = "effectDuringDate=" + request.getParameter("effectDuringDate");

        String params = parkingId + "&" + licensePlate + "&" + spaceNo + "&" + userId + "&" + cost + "&" + effectDuringTime + "&" + effectDuringDate;
        return JSON.parse(restTemplate.postForObject("http://api/parkingConcession/publishMsg?" + (params), apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 认领操作
     *
     * @param request
     */
    @RequestMapping(value = "/parkingConcession/accept")
    public Object accept(HttpServletRequest request) {
        String params = "";
        String parkingConcessionId = request.getParameter("parkingConcessionId");
        if (parkingConcessionId == null) {
            return RetKit.fail("parkingConcessionId不能为空");
        }
        String licensePlate = request.getParameter("licensePlate");
        if (licensePlate == null) {
            return RetKit.fail("licensePlate不能为空");
        }

        params += "parkingConcessionId=" + parkingConcessionId + "&licensePlate=" + licensePlate;
        String tenantId = request.getParameter("tenantId");
        if (tenantId != null) {
            params += "&tenantId=" + tenantId;
        }
        return JSON.parse(restTemplate.postForObject("http://api/parkingConcession/accept?" + (params), apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 我的发布/我的认领
     *
     * @param request
     * @return
     */
    @RequestMapping("/parkingConcession/getList")
    public Object getPublishList(HttpServletRequest request) {
        String params = "";
        String userId = request.getParameter("userId");
        String tenantId = request.getParameter("tenantId");
        String pageSize = request.getParameter("pageSize");
        String pageNum = request.getParameter("pageNum");

        if (StrKit.isBlank(userId) && StrKit.isBlank(tenantId)) {
            return RetKit.fail("userId或tenantId不能为空");
        }
        if (StrKit.isBlank(pageSize)) {
            return RetKit.fail("pageSize不能为空");
        }
        if (StrKit.isBlank(pageNum)) {
            return RetKit.fail("pageNum不能为空");
        }
        if (StrKit.notBlank(userId)) {
            params += "userId=" + userId + "&";
        } else {
            params += "tenantId=" + tenantId + "&";
        }
        params += "pageSize=" + pageSize + "&pageNum=" + pageNum;
        return JSON.parse(restTemplate.postForObject("http://api/parkingConcession/getList?" + params, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 我的发布/我的认领 删除
     *
     * @param request
     */
    @RequestMapping(value = "/parkingConcession/delMsg")
    public Object parkingConcessionDelMsg(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (StrKit.isBlank(id)) {
            return RetKit.fail("id不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/parkingConcession/delMsg?id=" + (id), apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 我的认领  取消
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/parkingConcession/cancel")
    public Object cancelRent(HttpServletRequest request) {
        String parkingConcessionId = request.getParameter("parkingConcessionId");
        if (StrKit.isBlank(parkingConcessionId)) {
            return RetKit.fail("parkingConcessionId不能为空！");
        }
        String params ="parkingConcessionId=" + parkingConcessionId;
        return JSON.parse(restTemplate.postForObject("http://api/parkingConcession/cancel?" + params, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 搜索车位
     *
     * @param request
     */
    @RequestMapping(value = "/parkingConcession/search")
    public Object parkingConcessionSearch(HttpServletRequest request) {
        String params = "";
        String keyword = request.getParameter("keyword");
        String pageSize = request.getParameter("pageSize");
        String pageNum = request.getParameter("pageNum");
        String longitude = request.getParameter("longitude");
        String latitude = request.getParameter("latitude");
        if (StrKit.isBlank(pageSize)) {
            return RetKit.fail("pageSize不能为空");
        } else if (Integer.parseInt(pageSize) <= 0) {
            return RetKit.fail("pageSize不能小于等于0");
        }
        if (StrKit.isBlank(pageNum)) {
            return RetKit.fail("pageNum不能为空");
        } else if (Integer.parseInt(pageNum) <= 0) {
            return RetKit.fail("pageNum不能小于等于0");
        }
        params += "pageSize=" + pageSize + "&pageNum=" + pageNum;
        if (StrKit.notBlank(keyword)) {
            params += "&keyword=" + keyword;
        }
        if (StrKit.notBlank(longitude) && StrKit.notBlank(latitude)) {
            params += "&longitude=" + longitude + "&latitude=" + latitude;
        }
        return JSON.parse(restTemplate.postForObject("http://api/parkingConcession/search?" + (params), apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 获取某类型主控版最新版本号
     * --sjh
     *
     * @param request
     * @return
     */
    @RequestMapping("/mainControl/latestVersion")
    public Object latestVersion(HttpServletRequest request) {
        String type = request.getParameter("type");
        if (StrKit.isBlank(type)) {
            return RetKit.fail("type不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/mainControl/latestVersion?type=" + type, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 上传主控板版本号
     * --sjh
     *
     * @param request
     * @return
     */
    @RequestMapping("/mainControl/uploadMainControlVersion")
    public Object uploadMainControlVersion(HttpServletRequest request) {
        String mac = request.getParameter("mac");
        String version = request.getParameter("version");
        String type = request.getParameter("type");
        if (StrKit.isBlank(mac)) {
            return RetKit.fail("mac不能为空");
        }
        if (StrKit.isBlank(version)) {
            return RetKit.fail("version不能为空");
        }
        if (StrKit.isBlank(type)) {
            return RetKit.fail("type不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/mainControl/uploadMainControlVersion?mac=" + mac + "&version=" + version + "&type=" + type, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 获取共享车位平台所有的出租车位信息
     * --zyy
     *
     * @param request
     * @return
     */
    @RequestMapping("/parkingConcession/getAllPublishList")
    public Object getAllPublishList(HttpServletRequest request) {
        String params = "";
        String searchParkName = request.getParameter("searchParkName");
        String pageSize = request.getParameter("pageSize");
        String pageNum = request.getParameter("pageNum");
        if (StrKit.isBlank(pageSize)) {
            return RetKit.fail("pageSize不能为空");
        } else if (Integer.parseInt(pageSize) <= 0) {
            return RetKit.fail("pageSize不能小于等于0");
        }
        if (StrKit.isBlank(pageNum)) {
            return RetKit.fail("pageNum不能为空");
        } else if (Integer.parseInt(pageNum) <= 0) {
            return RetKit.fail("pageNum不能小于等于0");
        }
        params += "pageSize=" + pageSize + "&pageNum=" + pageNum;
        if (StrKit.notBlank(searchParkName)) {
            params += "&searchParkName=" + searchParkName;
        }
        return JSON.parse(restTemplate.postForObject("http://api/parkingConcession/getAllPublishList?" + (params), apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 添加白名单
     *
     * @param request
     * @return
     * @author ZYY
     */
    @RequestMapping("/whiteList/addWhiteList")
    public Object addWhiteList(HttpServletRequest request) {
        String parkingId = request.getParameter("parkingId");
        String licencePlate = request.getParameter("licencePlate");
        String tmpFlag = request.getParameter("tmpFlag");
        if (StrKit.isBlank(parkingId)) {
            return RetKit.fail("parkingId不能为空！");
        }
        if (StrKit.isBlank(licencePlate)) {
            return RetKit.fail("licencePlate不能为空！");
        }
        if (StrKit.isBlank(tmpFlag)) {
            return RetKit.fail("tmpFlag不能为空！");
        }
        String params = "parkingId=" + parkingId + "&licencePlate=" + licencePlate + "&tmpFlag=" + tmpFlag;
        return JSON.parse(restTemplate.postForObject("http://api/whiteList/addWhiteList?" + params, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 获取白名单列表
     *
     * @param request
     * @return
     * @author ZYY
     */
    @RequestMapping("/whiteList/getWhiteList")
    public Object getWhiteList(HttpServletRequest request) {
        String parkingId = request.getParameter("parkingId");
        String pageSize = request.getParameter("pageSize");
        String pageNum = request.getParameter("pageNum");
        if (StrKit.isBlank(parkingId)) {
            return RetKit.fail("parkingId不能为空！");
        }
        if (StrKit.isBlank(pageSize)) {
            return RetKit.fail("pageSize不能为空");
        } else if (Integer.parseInt(pageSize) <= 0) {
            return RetKit.fail("pageSize不能小于等于0");
        }
        if (StrKit.isBlank(pageNum)) {
            return RetKit.fail("pageNum不能为空");
        } else if (Integer.parseInt(pageNum) <= 0) {
            return RetKit.fail("pageNum不能小于等于0");
        }
        String params = "parkingId=" + parkingId + "&pageSize=" + pageSize + "&pageNum=" + pageNum;
        return JSON.parse(restTemplate.postForObject("http://api/whiteList/getWhiteList?" + params, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 删除白名单
     *
     * @param request
     * @return
     * @author ZYY
     */
    @RequestMapping("/whiteList/delWhiteList")
    public Object delWhiteList(HttpServletRequest request) {
        String whiteListId = request.getParameter("whiteListId");
        if (StrKit.isBlank(whiteListId)) {
            return RetKit.fail("whiteListId不能为空！");
        }
        return JSON.parse(restTemplate.postForObject("http://api/whiteList/delWhiteList?whiteListId=" + whiteListId, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 商户登录
     *
     * @param request
     * @return
     * @author ZYY
     */
    @RequestMapping("/merchant/loginIn")
    public Object merchantLogin(HttpServletRequest request) {
        String userName = request.getParameter("userName");
        String pwd = request.getParameter("pwd");
        String platform = request.getHeader("platform");
//		String type = request.getHeader("type");
        if (userName == null || StrKit.isBlank(userName)) {
            return RetKit.fail("用户名不能为空！");
        }
        if (pwd == null || StrKit.isBlank(pwd)) {
            return RetKit.fail("密码不能为空！");
        }
        if (StrKit.isBlank(platform)) {
            return RetKit.fail("platform不能为空！");
        }
//		if (StrKit.isBlank(type)) {
//			return RetKit.fail("type不能为空！");
//		}
        return JSON.parse(restTemplate.postForObject("http://api/merchant/loginIn?userName=" + userName + "&pwd=" + pwd, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 获取出入口车道信息
     *
     * @param request
     * @return
     * @author ZYY
     */
    @RequestMapping("/roadway/getRoadWayInfo")
    public Object getRoadWayInfo(HttpServletRequest request) {
        String parkingId = request.getParameter("parkingId");
        if (StrKit.isBlank(parkingId)) {
            return RetKit.fail("parkingId不能为空！");
        }
        return JSON.parse(restTemplate.postForObject("http://api/roadway/getRoadWayInfo?parkingId=" + parkingId, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 商户的停车场
     *
     * @param request
     * @return
     */
    @RequestMapping("/merchant/getParkingList")
    public Object merchantParkingList(HttpServletRequest request) {
        return JSON.parse(restTemplate.postForObject("http://api/merchant/getParkingList", apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 商户给车主绑定抵扣券
     *
     * @author sjh
     */
    @RequestMapping("/deduction/bindDeduction")
    public Object bindDeduction(HttpServletRequest request) {
        String licensePlat = request.getParameter("licensePlat");
        String parkingUserId = request.getParameter("parkingUserId");
        if (StrKit.isBlank(licensePlat)) {
            return RetKit.fail("车牌不能为空");
        }
        if (StrKit.isBlank(parkingUserId)) {
            return RetKit.fail("商户id不能为空");
        }
        return JSON.parse(restTemplate.postForObject(
                "http://api/deduction/bindDeduction?licensePlat=" + licensePlat + "&parkingUserId="
                        + Integer.parseInt(parkingUserId),
                apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 获取已使用的抵扣券列表
     *
     * @param request
     * @return
     * @author hrd
     */
    @RequestMapping("/deduction/getList")
    public Object deductionList(HttpServletRequest request) {
        String pageSize = request.getParameter("pageSize");
        String pageNum = request.getParameter("pageNum");
        String parkingUserId = request.getParameter("parkingUserId");
        String yearMonth = request.getParameter("yearMonth");
        if (StrKit.isBlank(pageSize)) {
            return RetKit.fail("pageSize 不能为空！");
        }
        if (StrKit.isBlank(pageNum)) {
            return RetKit.fail("pageNum 不能为空！");
        }
        if (StrKit.isBlank(parkingUserId)) {
            return RetKit.fail("parkingUserId 不能为空！");
        }
        if (StrKit.isBlank(yearMonth)) {
            return RetKit.fail("yearMonth 不能为空！");
        }
        String params = "";
        params += "?pageSize=" + pageSize;
        params += "&pageNum=" + pageNum;
        params += "&parkingUserId=" + parkingUserId;
        params += "&yearMonth=" + yearMonth;
        return JSON.parse(restTemplate.postForObject("http://api/deduction/getList" + (params), apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    @RequestMapping("/parkingInfo/getParkingInfoByParkingId")
    public Object getAllParkingInfo(HttpServletRequest request) {
        String pageSize = request.getParameter("pageSize");
        String pageNum = request.getParameter("pageNum");
        String parkingId = request.getParameter("parkingId");
        String licensePlate = request.getParameter("licensePlate");
        if (StrKit.isBlank(parkingId)) {
            return RetKit.fail("parkingId 不能为空！");
        }
        if (StrKit.isBlank(pageSize)) {
            return RetKit.fail("pageSize 不能为空！");
        }
        if (StrKit.isBlank(pageNum)) {
            return RetKit.fail("pageNum 不能为空！");
        }
        if (StrKit.isBlank(pageNum)) {
            return RetKit.fail("pageNum不能为空");
        } else if (Integer.parseInt(pageNum) <= 0) {
            return RetKit.fail("pageNum不能小于等于0");
        }
        String params = "parkingId=" + parkingId + "&pageSize=" + pageSize + "&pageNum=" + pageNum;
        if (StrKit.notBlank(licensePlate)) {
            params += "&licensePlate=" + licensePlate;
        }
        return JSON.parse(restTemplate.postForObject("http://api/parkingInfo/getParkingInfoByParkingId?" + (params), apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    @RequestMapping("/groundLock/controlGroundLock")
    public Object controlGroundLock(HttpServletRequest request) {
        String groundUid = request.getParameter("groundUid");
        String type = request.getParameter("type");//101-关闭  102-打开
        if (StrKit.isBlank(groundUid)) {
            return RetKit.fail("地锁唯一识别码不能为空");
        }
        if (StrKit.isBlank(type)) {
            return RetKit.fail("type不能为空");
        }
        String params = "groundUid=" + groundUid + "&type=" + type;
        return JSON.parse(restTemplate.postForObject("http://api/groundLock/controlGroundLock?" + params, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    @RequestMapping("/groundLock/checkStatus")
    public Object checkStatus(HttpServletRequest request) {
        String groundUid = request.getParameter("groundUid");
        if (StrKit.isBlank(groundUid)) {
            return RetKit.fail("地锁唯一识别码不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/groundLock/checkStatus?groundUid=" + groundUid, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    @RequestMapping("/groundLock/findGroundLockInfo")
    public Object findGroundLockInfo(HttpServletRequest request) {
        String licensePlate = request.getParameter("licensePlate");
        if (StrKit.isBlank(licensePlate)) {
            return RetKit.fail("车牌号码不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/groundLock/findGroundLockInfo?licensePlate=" + licensePlate, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }


    /**
     * 活动优惠券列表
     * hrd
     *
     * @param request
     * @return
     */
    @RequestMapping("/user/getActivityCoupons")
    public Object getActivityCoupons(HttpServletRequest request) {
        String pageSize = request.getParameter("pageSize");
        String pageNum = request.getParameter("pageNum");

        if (pageSize == null) {
            return RetKit.fail("pageSize不能为空");
        }
        if (pageNum == null) {
            return RetKit.fail("pageNum不能为空");
        }
        String parameters = "pageSize=" + pageSize + "&" + "pageNum=" + pageNum;
        return JSON.parse(restTemplate.postForObject("http://api/user/getActivityCoupons?" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * (绑定)领取活动优惠券
     * hrd
     * @param request
     * @return
     */
    @RequestMapping("/user/bindActivityCoupon")
    public Object bindActivityCoupon(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String activityId = request.getParameter("activityId");

        if (StrKit.isBlank(userId)) {
            return RetKit.fail("userId不能为空");
        }
        if (StrKit.isBlank(activityId)) {
            return RetKit.fail("activityId不能为空");
        }
        String parameters = "userId=" + userId + "&" + "activityId=" + activityId;
        return JSON.parse(restTemplate.postForObject("http://api/user/bindActivityCoupon?" + parameters, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    /**
     * 获取我的优惠券
     * hrd
     * @param request
     * @return
     */
    @RequestMapping("/user/getCoupons")
    public Object getCoupons(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        if (StrKit.isBlank(userId)) {
            return RetKit.fail("userId不能为空");
        }
        String staus = request.getParameter("staus");
        if (StrKit.isBlank(staus)) {
            return RetKit.fail("staus不能为空");
        }
        return JSON.parse(restTemplate.postForObject("http://api/user/getCoupons?userId=" + userId + "&status=" + staus, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }
    
    @RequestMapping("/groundLock/studyCommond")
    public Object OpenAndCloseCommand(HttpServletRequest request,@RequestBody String jsonBodyStr) {
    	JSONObject jsonBody = JSON.parseObject(jsonBodyStr);
		String groundUid = jsonBody.getString("groundUid");
		if (StrKit.isBlank(groundUid)) {
			return RetKit.fail("groundUid不能为空");
		}
		String groundlist = jsonBody.getString("groundlist");	
		JSONArray jsonArray = JSON.parseArray(groundlist);
		if (jsonArray.isEmpty()||jsonArray.size()<1) {
			return RetKit.fail("groundlist的内容不能为空");
		}
		JSONObject itemOne = (JSONObject) jsonArray.get(0);
		String commond1 = itemOne.get("commond").toString();
		String type1 = itemOne.get("type").toString();
		JSONObject itemTwo = (JSONObject) jsonArray.get(1);
		String commond2 = itemTwo.get("commond").toString();
		String type2 = itemTwo.get("type").toString();
		String params = "groundUid="+groundUid+"&commond1="+commond1+"&type1="+type1+"&commond2="+commond2+"&type2="+type2;
		return JSON.parse(restTemplate.postForObject("http://api/groundLock/studyCommond?" + params, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }
    
    @RequestMapping("/groundLock/reportStatus")
    public Object reportStatus(HttpServletRequest request) {
    	String groundUid = request.getParameter("groundUid");
		String type = request.getParameter("type");
		if (StrKit.isBlank(groundUid)) {
			return RetKit.fail("地锁唯一识别码不能为空");
		}
		if (StrKit.isBlank(type)) {
			return RetKit.fail("type不能为空");
		}
		return JSON.parse(restTemplate.postForObject("http://api/groundLock/reportStatus?groundUid="+groundUid+"&type="+type, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }

    @RequestMapping("/parkingInfo/getParkingInfoList")
    public Object getParkingInfoList(HttpServletRequest request) {
    	String pageSize = request.getParameter("pageSize");
		String pageNum = request.getParameter("pageNum");
		String companyId = request.getParameter("companyId");
		if (StrKit.isBlank(pageSize)) {
			return RetKit.fail("pageSize不能为空");
		}
		if (StrKit.isBlank(pageNum)) {
			return RetKit.fail("pageNum不能为空");
		}
		if (StrKit.isBlank(pageNum)) {
			return RetKit.fail("companyId不能为空");
		}
    	String searchParkName = request.getParameter("searchParkName");
		String searchParkingLicensePlate = request.getParameter("searchParkingLicensePlate");
		String searchUserPhone = request.getParameter("searchUserPhone");
		return JSON.parse(restTemplate.postForObject("http://api/parkingInfo/getParkingInfoList?pageSize="+pageSize+"&pageNum="+pageNum+"&companyId="+companyId+"&searchParkName="+searchParkName+"&searchParkingLicensePlate="+searchParkingLicensePlate+"&searchUserPhone="+searchUserPhone, apiConsumeService.getHttpEntityByRequst(request), String.class));
    }
}
