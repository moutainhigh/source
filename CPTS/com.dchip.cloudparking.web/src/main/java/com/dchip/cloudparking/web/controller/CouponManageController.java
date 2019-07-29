package com.dchip.cloudparking.web.controller;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.iService.ICouponManageService;
import com.dchip.cloudparking.web.iService.IMemberRuleService;
import com.dchip.cloudparking.web.model.po.Coupon;
import com.dchip.cloudparking.web.utils.DateUtil;
import com.dchip.cloudparking.web.utils.RetKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/couponManage")
public class CouponManageController {

    @Autowired
    private ICouponManageService couponManageService;

    @Autowired
    private IMemberRuleService memberRuleService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
    	Locale locale = LocaleContextHolder.getLocale();
		request.setAttribute("i18n", locale);
        return "couponManage/index";
    }

    @RequestMapping("/rendering")
    @ResponseBody
    public Object rendering(HttpServletRequest request) {
    	// 排序条件
    	String sortName = request.getParameter("sortName");
    	String  direction = request.getParameter("direction");
    	// 分页条件
        Integer pageSize = Integer.parseInt(request.getParameter("limit"));
        Integer pageNum = Integer.parseInt(request.getParameter("page")) - 1;
        return JSON.toJSON(couponManageService.getCouponList(pageSize, pageNum ,sortName ,direction));
    }

    @RequestMapping("/add")
    @ResponseBody
    public RetKit add(HttpServletRequest request) {
        Coupon vo = new Coupon();
        try {
            vo.setCount(Integer.parseInt(request.getParameter("count")));
            if(vo.getCount() <= 0){
                return RetKit.fail("优惠券面值不能小于等于0");
            }
            vo.setRemark(request.getParameter("remark"));
            vo.setStatus(Integer.parseInt(request.getParameter("status")));
            vo.setMemberIds(request.getParameter("memberIds"));
            vo.setCreateTime(DateUtil.dateToStamp(request.getParameter("createTime"), "yyyy-MM-dd HH:mm:ss"));
            String endTimeStr = request.getParameter("endTime");
            if(endTimeStr != null && !endTimeStr.toString().trim().equals("")){
            	Date endTime = DateUtil.dateToStamp(endTimeStr, "yyyy-MM-dd HH:mm:ss");
            	if (endTime.before(vo.getCreateTime())) {
            		vo.setEndTime(vo.getCreateTime());
				}else {
					vo.setEndTime(endTime);
				}
            }else{
            	Calendar c = Calendar.getInstance();
            	c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(request.getParameter("endNum")));
            	Date endTime = c.getTime();
                vo.setEndTime(endTime);
                vo.setEndNum(Integer.parseInt(request.getParameter("endNum")));
                vo.setEndType(Integer.parseInt(request.getParameter("endType")));
            }
            vo.setDeductionType(Integer.parseInt(request.getParameter("deductionType")));
            if (vo.getDeductionType() == 2) {//若为分次抵扣，则设置分次抵扣值
                Integer partDeduction = Integer.parseInt(request.getParameter("partDeduction"));
                if(vo.getCount() >= partDeduction){
                    vo.setPartDeduction(partDeduction);
                    vo.setEndNum(vo.getCount()/partDeduction);
                    if(vo.getPartDeduction() < 0){
                        return RetKit.fail("分次抵扣数值小于0");
                    }
                }else {
                    return RetKit.fail("分次抵扣数值不能高于优惠券面值");
                }
            }
            return couponManageService.save(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return RetKit.fail("添加优惠券失败");
        }
    }

    @RequestMapping("/edit")
    @ResponseBody
    public RetKit edit(HttpServletRequest request){
        Coupon vo = new Coupon();
        try {
            vo.setId(Integer.parseInt(request.getParameter("couponId")));//id不能为空
            if(vo.getId() == null ){
                return RetKit.fail("id不能为空");
            }
            vo.setCount(Integer.parseInt(request.getParameter("count")));
            if(vo.getCount() <= 0){
                return RetKit.fail("优惠券面值不能小于等于0");
            }
            vo.setRemark(request.getParameter("remark"));
            vo.setStatus(Integer.parseInt(request.getParameter("status")));
            vo.setMemberIds(request.getParameter("memberIds"));
            vo.setCreateTime(DateUtil.dateToStamp(request.getParameter("createTime"), "yyyy-MM-dd HH:mm:ss"));
            String endTimeStr = request.getParameter("endTime");
            if(endTimeStr != null && !endTimeStr.toString().trim().equals("")){
            	Date endTime = DateUtil.dateToStamp(endTimeStr, "yyyy-MM-dd HH:mm:ss");
            	if (endTime.before(vo.getCreateTime())) {
            		vo.setEndTime(vo.getCreateTime());
				}else {
					vo.setEndTime(endTime);
				}
            }else{
                vo.setEndNum(Integer.parseInt(request.getParameter("endNum")));
                vo.setEndType(Integer.parseInt(request.getParameter("endType")));
            }
            vo.setDeductionType(Integer.parseInt(request.getParameter("deductionType")));
            if (vo.getDeductionType() == 2) {//若为分次抵扣，则设置分次抵扣值
                Integer partDeduction = Integer.parseInt(request.getParameter("partDeduction"));
                if(vo.getCount() >= partDeduction){
                    vo.setPartDeduction(partDeduction);
                    if(vo.getPartDeduction() < 0){
                        return RetKit.fail("分次抵扣数值小于0");
                    }
                }else {
                    return RetKit.fail("分次抵扣数值不能高于优惠券面值");
                }
            }
            return couponManageService.save(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return RetKit.fail("编辑优惠券失败");
        }
    }

    @RequestMapping("/changeCouponStatus")
    @ResponseBody
    public RetKit changeCouponStatus(HttpServletRequest request) {
        Integer couponId = Integer.parseInt(request.getParameter("couponId"));
        Integer couponStatus = Integer.parseInt(request.getParameter("couponStatus"));
        return couponManageService.changeCouponStatus(couponId, couponStatus);
    }

    @RequestMapping("/queryAll")
    @ResponseBody
    public RetKit queryAll(HttpServletRequest request) {
        return RetKit.okData(couponManageService.getActivityCouponList(Integer.MAX_VALUE,0));
    }

    @RequestMapping("/delete")
    @ResponseBody
    public RetKit delete(HttpServletRequest request) {
        Integer couponId = Integer.parseInt(request.getParameter("couponId"));
        return couponManageService.softlyDel(couponId);
    }

    @RequestMapping("/queryMemberRule")
    @ResponseBody
    public RetKit queryMemberRule(HttpServletRequest request) {
        return RetKit.okData(memberRuleService.getMemberRuleList(Integer.MAX_VALUE,0,"id","ASC",null));
    }
}
