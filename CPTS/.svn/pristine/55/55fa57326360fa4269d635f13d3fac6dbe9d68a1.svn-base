package com.dchip.cloudparking.api.controller;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.api.constant.BaseConstant;
import com.dchip.cloudparking.api.iService.ICardService;
import com.dchip.cloudparking.api.iService.IParkingConcessionService;
import com.dchip.cloudparking.api.iService.IUserService;
import com.dchip.cloudparking.api.model.po.ParkingConcession;
import com.dchip.cloudparking.api.utils.DateUtil;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.*;

/**
 * 共享车位  停车信息
 *
 * @author 鼎芯科技
 */
@RestController
@RequestMapping("/parkingConcession")
public class ParkingConcessionController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IParkingConcessionService parkingConcessionService;

    @Autowired
    private ICardService cardService;

    /**
     * 车位租让 信息发布
     *
     * @param request
     */
    @RequestMapping(value = "/publishMsg")
    public RetKit publishMsg(HttpServletRequest request) {
        ParkingConcession parkingConcession = new ParkingConcession();
        try {
        	String pid = request.getParameter("parkingId");
        	if (StrKit.isBlank(pid)) {
				return RetKit.fail("parkingId不能为空");
			}
            Integer parkingId = Integer.parseInt(pid);
            if (parkingId <= 0) {
                return RetKit.fail("停车场不存在");
            }
            String licensePlate = request.getParameter("licensePlate");
            Integer isFixed = cardService.isFixedByLicensePlate(licensePlate,parkingId);
            if (isFixed == 0) {
            	return RetKit.fail("非固定车位，不能发布租让信息");
			}
//            if(isFixedSpace == null || Integer.parseInt(isFixedSpace) != 1){
//                return RetKit.fail("非固定车位，不能发布租让信息");
//            }else if (parkId == null || Integer.parseInt(parkId) != parkingId) {
//				return RetKit.fail("无权限发布租让信息");
//			}

            parkingConcession.setLicensePlate(licensePlate);

            //查找用户是否有该车牌
            Integer userId = userService.findIdByLicensePlate(licensePlate);
            if (userId == null) {
                return RetKit.fail("不存在该车牌用户");
            } else if (userId != Integer.parseInt(request.getParameter("userId"))) {
                return RetKit.fail("userId不能为空");
            }


            Integer count = parkingConcessionService.getPublishCountByPlate(licensePlate,parkingId);
            if (!(count == null || count == 0)) {
                return RetKit.fail("不能重复发布车位");
            }

            String spaceNo = request.getParameter("spaceNo");
            if (StrKit.notBlank(spaceNo)) {
                Integer msgCount = parkingConcessionService.findParkingSpaceNo(parkingId, spaceNo);
                if (!(msgCount == null || msgCount == 0)) {
                    return RetKit.fail("不能重复发布车位");
                }
                parkingConcession.setSpaceNo(spaceNo);
            }
            parkingConcession.setTenantId(BaseConstant.ParkingConcession.TenantNoId.getTypeValue());
            parkingConcession.setUserId(userId);
            parkingConcession.setParkingId(parkingId);
            parkingConcession.setCost(new BigDecimal(request.getParameter("cost")));
            parkingConcession.setEffectDuringTime(request.getParameter("effectDuringTime"));
            parkingConcession.setEffectDuringDate(request.getParameter("effectDuringDate"));


            String[] effectDuringDates = parkingConcession.getEffectDuringDate().trim().replaceAll(" ", "").split("~");
            int diffDays = DateUtil.getDiffDays(DateUtil.dateToStamp(effectDuringDates[0],"yyyy-MM-dd"),DateUtil.dateToStamp(effectDuringDates[1],"yyyy-MM-dd"));
            if(diffDays > 30){
                return RetKit.fail("不能超过30天");
            }

            parkingConcession.setPublishTime(new Date());//发布时间
            parkingConcession.setStatus(BaseConstant.ParkingConcession.CheckPendingStatus.getTypeValue());//发布的时候,为待审核
            parkingConcessionService.publishConcessionMsg(parkingConcession);
        } catch (Exception e) {
            e.printStackTrace();
            return RetKit.fail("发布失败");
        }
        return RetKit.ok();
    }

    /**
     * 认领操作
     *
     * @param request
     */
    @RequestMapping(value = "/accept")
    public RetKit accept(HttpServletRequest request) {
        try {
            String parkingConcessionIdStr = request.getParameter("parkingConcessionId");
            String tenantId = request.getParameter("tenantId");

            String tenantPlate = request.getParameter("licensePlate");//临时车可以为空
            if (StrKit.isBlank(parkingConcessionIdStr)) {
                return RetKit.fail("parkingConcessionId不能为空！");
            }
            Integer parkingConcessionId = Integer.parseInt(parkingConcessionIdStr);
            if (StrKit.isBlank(tenantId)) {
                return RetKit.fail("tenantId不能为空！");
            }
            return parkingConcessionService.accept(parkingConcessionId, tenantPlate, tenantId);
        } catch (Exception e) {
            return RetKit.fail("认领失败！");
        }
    }

    /**
     * 我的发布 - 硬删
     *
     * @param request
     */
    @RequestMapping(value = "/delMsg")
    public RetKit delMsg(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            if (StrKit.isBlank(id)) {
                return RetKit.fail("id不能为空！");
            }
            parkingConcessionService.delMsg(Integer.parseInt(id));
            return RetKit.ok();
        } catch (Exception e) {
            return RetKit.fail();
        }
    }

    /**
     * 我的认领  取消
     * 取消条件：在不超过预定时间范围即可进行取消操作
     *
     * @param request
     * @return
     */
    @RequestMapping("/cancel")
    public RetKit cancelRent(HttpServletRequest request) {
        String parkingConcessionIdStr = request.getParameter("parkingConcessionId");
        return parkingConcessionService.cancelRent(parkingConcessionIdStr);
    }

    /**
     * 我的发布/我的认领/包含距离的列表
     *
     * @param request
     */
    @RequestMapping(value = "/getList")
    public Object queryList(HttpServletRequest request) {
        try {
            Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
            Integer pageNum = Integer.parseInt(request.getParameter("pageNum")) - 1;

            String tenantId = request.getParameter("tenantId");
            String userId = request.getParameter("userId");
            return JSON.toJSON(parkingConcessionService.queryList(userId, tenantId, pageSize, pageNum, null));
        } catch (Exception e) {
            return RetKit.fail();
        }
    }


    /**
     * 查找共享车位的停车场 - 模糊查询停车场名称
     *
     * @param request
     * @return
     */
    @RequestMapping("/search")
    public Object search(HttpServletRequest request) {
        try {
            Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
            Integer pageNum = Integer.parseInt(request.getParameter("pageNum")) - 1;

            //动态查询参数
            String keyword = request.getParameter("keyword");

            String longitude = request.getParameter("longitude");
            String latitude = request.getParameter("latitude");

            List<Map<String, Object>> para = new ArrayList<>();
            if (StrKit.notBlank(keyword)) {
                Map<String, Object> map = new HashMap<>();
                map.put("keyword", keyword);
                para.add(map);
            }
            if (StrKit.notBlank(longitude)) {
                Map<String, Object> map = new HashMap<>();
                map.put("longitude", keyword);
                para.add(map);
            }
            if (StrKit.notBlank(latitude)) {
                Map<String, Object> map = new HashMap<>();
                map.put("latitude", keyword);
                para.add(map);
            }
            return JSON.toJSON(parkingConcessionService.queryList(null, null, pageSize, pageNum, para));
        } catch (Exception e) {
            return RetKit.fail();
        }
    }

    /**
     * 获取所有出租车位信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/getAllPublishList")
    public RetKit allPublishList(HttpServletRequest request) {
        Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
        Integer pageNum = Integer.parseInt(request.getParameter("pageNum")) - 1;
        String searchParkName = request.getParameter("searchParkName");
        List<Map<String, Object>> para = new ArrayList<>();
        if (StrKit.notBlank(searchParkName)) {
            Map<String, Object> map = new HashMap<>();
            map.put("searchParkName", searchParkName);
            para.add(map);
        }
        return parkingConcessionService.getAllPublishList(pageSize, pageNum, para);
    }

}
