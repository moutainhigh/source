package com.dchip.cloudparking.api.iService;

import com.dchip.cloudparking.api.model.po.ParkingConcession;
import com.dchip.cloudparking.api.model.po.User;
import com.dchip.cloudparking.api.utils.RetKit;

import java.util.List;
import java.util.Map;

public interface IParkingConcessionService {

    /**
     * 发布信息租让车位信息
     * @return
     */
    RetKit publishConcessionMsg(ParkingConcession  parkingConcession);

    /**
     * 认领
     * @param parkingConcessionId
     * @param licensePlate
     * @param tenantId
     * @return
     */
    RetKit accept(Integer parkingConcessionId,String licensePlate,String tenantId);

    /**
     * 我的认领/我的发布
     * @param tenantId
     * @return
     */
    Object queryList(String userId,String tenantId,Integer pageSize,Integer pageNum,List<Map<String,Object>> para);

    /**
     * 我的发布 - 硬删
     * @param id
     * @return
     */
    RetKit delMsg(Integer id);


    /**
     * 查找共享车位的停车场
     * @param keyword
     * @return
     */
    RetKit search(String keyword);
    
    /**
     * 获取所有共享车位列表
     * @param pageSize
     * @param pageNum
     * @param para
     * @return
     */
	RetKit getAllPublishList(Integer pageSize, Integer pageNum,List<Map<String,Object>> para);

	/**
	 * 我的认领  取消
	 * @param effectDuringDate
	 * @param effectDuringTime
	 * @return
	 */
	RetKit cancelRent( String parkingConcessionId);

    /**
     * 根据车牌获取是否已发布信息
     * @param licensePlate
     * @return
     */
    Integer getPublishCountByPlate(String licensePlate, Integer parkingId);

    /**
     * 查找发布消息中的停车位
     * @param parkingId
     * @param spaceNo
     * @return
     */
    Integer findParkingSpaceNo(Integer parkingId, String spaceNo);

    /**
     * 根据停车场id和用户id查找租赁信息
     * @param parkingId
     * @param carNum
     * @param tenant
     * @return
     */
    ParkingConcession findAcceptMsg(Integer parkingId, String carNum, User tenant);


}
