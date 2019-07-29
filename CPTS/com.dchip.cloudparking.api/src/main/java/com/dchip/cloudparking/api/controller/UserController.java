package com.dchip.cloudparking.api.controller;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.api.constant.BaseConstant;
import com.dchip.cloudparking.api.iRepository.IMemberRuleRepository;
import com.dchip.cloudparking.api.iRepository.IUserRepository;
import com.dchip.cloudparking.api.iService.IRechargeLogService;
import com.dchip.cloudparking.api.iService.IUserService;
import com.dchip.cloudparking.api.model.po.MemberRule;
import com.dchip.cloudparking.api.model.po.Session;
import com.dchip.cloudparking.api.model.po.User;
import com.dchip.cloudparking.api.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 用户
 *
 * @author 鼎芯科技
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private Environment ev;
    @Resource
    private CytSdk cytsdk;
    @Resource
    private StringRedisTemplate stringTemplate;
    @Autowired
    private IUserService userService;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRechargeLogService rechangeBillService;
    @Autowired
    private IMemberRuleRepository memberRuleRepository;

    /**
     * 发送短信验证码接口
     *
     * @param request
     * @return
     * @author leif
     */
    @RequestMapping("/sendVerificationCode")
    public RetKit sendVerificationCode(HttpServletRequest request) {
        try {
            String phone = request.getParameter("phone");
            // 生成验证码
            String code = generatorVerifyCode();

            // 模板语言消息实例化
            String temp = ev.getProperty("message.template.text");
            String message = MessageFormat.format(temp, new Object[]{code});
            // 发送消息
            RetKit rs = cytsdk.sendMessage(phone, message);
            // 发送成功，缓存验证码，10分钟有效果
            if (rs.success()) {
                // 将手机号码作为key,存入redis中
                // stringTemplate.opsForValue().set(phone, code,10,TimeUnit.MINUTES);
                stringTemplate.opsForValue().set(phone, code, 10, TimeUnit.DAYS);
            }
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            return RetKit.fail();
        }
    }

    /**
     * 生成6位验证码
     *
     * @return
     */
    private String generatorVerifyCode() {
        return RandomUtil.getRandomPsw(6);
    }

    /**
     * 用户登录接口
     *
     * @param request
     * @return
     * @author leif
     */
    @RequestMapping("/usePhoneLogin")
    public RetKit usePhoneLogin(HttpServletRequest request) {
        String phone = request.getParameter("phone");
        String code = request.getParameter("code");
        String registrationId = request.getParameter("registrationId");
        String platform = request.getHeader("platform");
        String licensePlat = request.getParameter("licensePlat");  //车牌号码
        if (StrKit.isBlank(phone)) {
            return RetKit.fail("手机号码不能为空");
        }
        if (StrKit.isBlank(platform)) {
            return RetKit.fail("platform不能为空");
        }
        if (StrKit.isBlank(licensePlat)) {
            return RetKit.fail("licensePlat不能为空");
        }
        licensePlat = licensePlat.toUpperCase();  //将字母都转为大写
        String verifyCode = stringTemplate.opsForValue().get(phone);
        if (verifyCode == null) {
            return RetKit.fail("没获取验证码或验证码已过期");
        }
        if (verifyCode.equals(code)) {
            // stringTemplate.delete(phone); // 删除缓存
            String ip = IpKit.getRealIp(request);
            if (licensePlat.equals(BaseConstant.DemoAccounts.demoLicensePlat.getTypeValue())) {
                //演示帐号登录
                return userService.DemoUserLoginOperation(phone, ip, registrationId, Integer.parseInt(platform), licensePlat);
            } else {
                //普通帐号登录
                return userService.userLoginOperation(phone, ip, registrationId, Integer.parseInt(platform), licensePlat);
            }

        } else {
            // return RetKit.fail(MessageUtil.loadMessage("message.verify.code"));
            return RetKit.fail("验证码有误！");
        }
    }

    /**
     * 更改手机号
     *
     * @param request
     * @return
     */
    @RequestMapping("/changePhone")
    public RetKit changePhone(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String newPhone = request.getParameter("newPhone");
        String code = request.getParameter("code");
        String verifyCode = stringTemplate.opsForValue().get(newPhone);
        if (verifyCode == null) {
            return RetKit.fail("没获取验证码或验证码已过期");
        }
        if (verifyCode.equals(code)) {
            // stringTemplate.delete(phone); // 删除缓存
            return userService.changePhone(userId, newPhone);
        } else {
            // return RetKit.fail(MessageUtil.loadMessage("message.verify.code"));
            return RetKit.fail("验证码有误！");
        }
    }

    /**
     * 账户余额查询接口
     * by 小梁
     */
    @RequestMapping("/getBalance")
    public RetKit getBalance(HttpServletRequest request) {
        try {
            Integer userId = Integer.valueOf(request.getParameter("userId"));
            return userService.getBalance(userId);
        } catch (Exception e) {
            return RetKit.fail();
        }
    }

    /**
     * 获取总积分
     *
     * @param request
     * @return
     */
    @RequestMapping("/getTotalScore")
    public RetKit getTotalScore(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        return userService.getTotalScore(userId);
    }

    /**
     * 获取积分明细
     *
     * @param request
     * @return
     */
    @RequestMapping("/getScoreDetail")
    public RetKit getScoreDetail(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        return userService.getScoreDetail(userId, year, month);
    }

    /**
     * 会员判断接口
     * by 小梁
     */
    @RequestMapping("/getUserGrade")
    public RetKit getUserGrade(HttpServletRequest request) {
        try {
            Integer userId = Integer.valueOf(request.getParameter("userId"));
            Optional<User> userOpt = userRepository.findById(userId);
            Map<String, Object> map = new HashMap<>();
            Optional<MemberRule> memberRule = memberRuleRepository.findById(userOpt.get().getMemberId());
            map.put("grade", memberRule.get().getGrade());
            map.put("allMoney", rechangeBillService.findAllMoneyByUserId(userOpt.get().getId()));
            map.put("nextMoney", memberRule.get().getMoney());
            map.put("gradeDescription", memberRule.get().getGradeDescription());
            return RetKit.okData(map);
        } catch (Exception e) {
            return RetKit.fail();
        }
    }

    @RequestMapping("/checkDiscounts")
    public RetKit checkDiscounts(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String amount = request.getParameter("amount");
        return userService.checkDiscounts(userId, amount);
    }

    /**
     * 用户退出登录操作
     *
     * @param request
     * @return
     */
    @RequestMapping("/loginOut")
    public RetKit logOut(HttpServletRequest request) {
        String accesstoken = request.getHeader("accessToken");
        Session session = userService.findSessionByToken(accesstoken);
        return userService.userlogOut(session);
    }

    /**
     * 获取用户详情
     * --leif
     *
     * @param request
     * @return
     */
    @RequestMapping("/getUserDetails")
    public RetKit getUserDetails(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        return userService.getUserDetails(userId);
    }

    /**
     * 获取用户是否认证的flag   0-未认证  1-认证通过   2-认证不通过
     *
     * @param request
     * @return
     */
    @RequestMapping("/getAuthenticationFlag")
    public RetKit getAuthenticationFlag(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        return userService.getAuthenticationFlag(userId);
    }

    /**
     * 活动优惠券列表
     * hrd
     *
     * @param request
     * @return
     */
    @RequestMapping("/getActivityCoupons")
    public Object getActivityCoupons(HttpServletRequest request) {
        String accessToken = request.getHeader("accessToken");
        Session session = userService.findSessionByToken(accessToken);
        if (session == null) {
//			return RetKit.fail("accessToken已失效");
			return RetKit.fail("该账号已在其他手机登录");
		}
        long userId = session.getUserId();
        try {
            Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
            Integer pageNum = Integer.parseInt(request.getParameter("pageNum")) - 1;
            return JSON.toJSON(userService.getActivityCoupons(userId, pageSize, pageNum, null));
        } catch (Exception e) {
            return RetKit.fail();
        }
    }

    /**
     * 用户领取活动优惠券，使用优惠券的方法在ParkingInfoServiceImp -> generateOrder中使用
     * hrd
     *
     * @param request
     * @return
     */
    @RequestMapping("/bindActivityCoupon")
    public RetKit bindActivityCoupon(HttpServletRequest request) {
        try {
            String userId = request.getParameter("userId");
            String activityId = request.getParameter("activityId");

            return userService.bindActivityCoupon(Integer.parseInt(userId), Integer.parseInt(activityId));
        } catch (Exception e) {
            return RetKit.fail();
        }
    }

    /**
     * 获取我的优惠券
     *
     * @param request
     * @return
     */
    @RequestMapping("/getCoupons")
    public RetKit getUserCoupons(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String status = request.getParameter("status");
        return userService.getUserCoupons(userId, status);
    }

}
