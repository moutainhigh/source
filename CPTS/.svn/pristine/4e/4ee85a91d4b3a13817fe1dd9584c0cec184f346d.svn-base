package com.dchip.cloudparking.api.iService;

import com.dchip.cloudparking.api.utils.RetKit;

public interface IDeductionService {

    public Object getDeductionList(Integer pageSize, Integer pageNum,Integer parkingUserId,String yearMonth);
    /**
     * 商户给车主绑定抵扣券
     * @param licensePlat
     * @param parkingUserId
     * @return
     */
    RetKit bindDeduction(String licensePlat,Integer parkingUserId);
}
