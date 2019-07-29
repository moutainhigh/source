package com.dchip.cloudparking.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.api.iService.IDeductionService;
import com.dchip.cloudparking.api.utils.QiniuUtil;
import com.dchip.cloudparking.api.utils.RetKit;
import com.dchip.cloudparking.api.utils.StrKit;

@RestController
@RequestMapping("/deduction")
public class DeductionController {

    @Autowired
    private IDeductionService deductionService;

    @Autowired
    private QiniuUtil uploadUtil;

    /**
     * 获取已使用的抵扣券列表
     * @author hrd
     * @param request
     * @return
     */
    @RequestMapping("/getList")
    public Object getList(HttpServletRequest request) {
        Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
        Integer pageNum = Integer.parseInt(request.getParameter("pageNum")) - 1;
        Integer parkingUserId = Integer.parseInt(request.getParameter("parkingUserId"));
        String yearMonth = request.getParameter("yearMonth");
        return JSON.toJSON(deductionService.getDeductionList(pageSize, pageNum,parkingUserId,yearMonth));
    }

    /**
     *  商户给车主绑定抵扣券
     * @author sjh
     * @param request
     * @return
     */
    @RequestMapping("/bindDeduction")
    public RetKit bindDeduction(HttpServletRequest request) {
        String licensePlat = request.getParameter("licensePlat");
        String parkingUserId = request.getParameter("parkingUserId");
        if (StrKit.isBlank(parkingUserId)) {
            return RetKit.fail("商户id不能为空");
        }
        return deductionService.bindDeduction(licensePlat, Integer.parseInt(parkingUserId));
    }
}
