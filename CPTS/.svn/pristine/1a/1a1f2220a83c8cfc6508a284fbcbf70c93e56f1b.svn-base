package com.dchip.cloudparking.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dchip.cloudparking.api.constant.BaseConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.api.iService.IUserService;
import com.dchip.cloudparking.api.model.po.Session;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;

@SuppressWarnings("deprecation")
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private IUserService userService;

    @Bean
    public InterfaceAuthCheckInterceptor getInterfaceAuthCheckInterceptor() {
        return new InterfaceAuthCheckInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        // 排除配置
        registry.addInterceptor(getInterfaceAuthCheckInterceptor()).excludePathPatterns(
                "/user/usePhoneLogin",
                "/user/sendVerificationCode",
                "/hardware/getCameraInfo", "/hardware/changeCarportNum",
                "/hardware/changeStatus", "/roadway/changeRoadwayOnline",
                "/roadway/getServerTime", "/mainControl/uploadMainControl",
                "/hardware/**", "/parkingInfo/parking",
                "/payment/weixin_notify",
                "/mainControl/uploadMainControlVersion",
                "/payment/ali_notify",
                "/mainControl/latestVersion",

                "/merchant/loginIn",
//				  "/parkingInfo/getParkingInfoByParkingId",
//                "/deduction/bindDeduction",
//                "/deduction/getList",
                "/user/getActivityCoupons",
                "/user/bindActivityCoupon",
//				"/roadway/getRoadWayInfo",
//				"/whiteList/delWhiteList",
//				"/whiteList/getParkingList",
//				"/whiteList/getWhiteList",
//				"/whiteList/addWhiteList",
                "/groundLock/studyCommond",
                "/groundLock/reportStatus",
                "/parkingLotManage/saveParkingLotInfo",
                "/parkingLotManage/changeParkingLotStatus",
                "/parkingInfo/getParkingInfoList",
                "/parking/findNeighborhoodParking",
                "/parking/search"
        ).addPathPatterns("/**");
        // registry.addInterceptor(new InterfaceAuthCheckInterceptor()).addPathPatterns("/api/**");
        // 如果interceptor中不注入redis或其他项目可以直接new，否则请使用上面这种方式
        super.addInterceptors(registry);
    }

    /**
     * 微服务间接口访问密钥验证
     *
     * @author xiaochangwei
     */
    class InterfaceAuthCheckInterceptor implements HandlerInterceptor {

        private Logger logger = LoggerFactory.getLogger(getClass());

        @Autowired
        StringRedisTemplate stringRedisTemplate;

        @Override
        public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
                throws Exception {

        }

        @Override
        public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
                throws Exception {

        }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj)
                throws Exception {
            String accesstoken = request.getHeader("accessToken");
            String platform = request.getHeader("platform");
            String type = request.getHeader("type");
            if (StrKit.isBlank(accesstoken)) {
                response.getWriter().write(JSON.toJSONString(RetKit.fail("accessToken can not be null")));
                return false;
            }
            if (StrKit.isBlank(platform)) {
                response.getWriter().write(JSON.toJSONString(RetKit.fail("platform can not be null")));
                return false;
            }
            Session session;
            if (StrKit.notBlank(type)) {//停车场用户
                session = userService.findSessionByTokenAndType(accesstoken, BaseConstant.SessionUserType.ParkingUser.getTypeValue());
            } else {
                session = userService.findSessionByToken(accesstoken);
            }
            if (session == null) {
                response.getWriter().write(JSON.toJSONString(RetKit.fail(502, "session is overdue")));
                return false;
            }
            return true;
        }

    }
}
