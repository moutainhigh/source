package com.dchip.cloudparking.web.iService;

import com.dchip.cloudparking.web.model.po.Coupon;
import com.dchip.cloudparking.web.utils.RetKit;

public interface ICouponManageService {

	public Object getCouponList(Integer pageSize, Integer pageNum , String sortName, String direction);

	public RetKit save(Coupon vo);

	public RetKit del(Integer couponId);

	public RetKit softlyDel(Integer couponId);

    public RetKit changeCouponStatus(Integer couponId, Integer couponStatus);
    
    public Object getActivityCouponList(Integer pageSize, Integer pageNum);
}
