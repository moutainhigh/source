package com.dchip.cloudparking.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.dchip.cloudparking.web.constant.BaseConstant;
import com.dchip.cloudparking.web.iService.IDeductionManageService;
import com.dchip.cloudparking.web.model.po.ParkingUser;
import com.dchip.cloudparking.web.model.vo.DeductionVo;
import com.dchip.cloudparking.web.model.vo.UserAuthentic;
import com.dchip.cloudparking.web.utils.QiniuUtil;
import com.dchip.cloudparking.web.utils.RetKit;
import com.dchip.cloudparking.web.utils.StrKit;

@Controller
@RequestMapping("/deductionManage")
public class DeductionManageController {

    @Autowired
    private IDeductionManageService deductionManageService;

    @Autowired
    private QiniuUtil uploadUtil;

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        request.setAttribute("domain", QiniuUtil.getDomain());
        request.setAttribute("domainUrl", QiniuUtil.getUrl());
        request.setAttribute("token", QiniuUtil.getUpToken());
        request.setAttribute("bucketZone", QiniuUtil.getBucketZone());
        request.setAttribute("roleType", user.getRoleType());
        return "deductionManage/index";
    }

    @RequestMapping("/rendering")
    @ResponseBody
    public Object rendering(HttpServletRequest request) {
        UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ParkingUser parkingUser = deductionManageService.findParkingUserByUserName(user.getUserName());
        Integer parkingId = -1;
        if (parkingUser != null) {
            parkingId = parkingUser.getParkingId();
        }

        // 排序条件
        String sortName = request.getParameter("sortName");
        String direction = request.getParameter("direction");
        // 动态搜索参数
        Integer pageSize = Integer.parseInt(request.getParameter("limit"));
        Integer pageNum = Integer.parseInt(request.getParameter("page")) - 1;

        String strStatus = request.getParameter("status");
        String strLicensePlate = request.getParameter("licensePlate");
        String strRealName = request.getParameter("realName");

        List<Map<String, Object>> para = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("parkingId", parkingId);// 停车场id
        para.add(map1);

        if (StrKit.notBlank(strStatus)) {
            Map<String, Object> map = new HashMap<>();
            map.put("status", strStatus);
            para.add(map);
        }
        if (StrKit.notBlank(strLicensePlate)) {
            Map<String, Object> map = new HashMap<>();
            map.put("strLicensePlate", strLicensePlate);
            para.add(map);
        }
        if (StrKit.notBlank(strRealName)) {
            Map<String, Object> map = new HashMap<>();
            map.put("strRealName", strRealName);
            para.add(map);
        }

        return JSON.toJSON(deductionManageService.getDeductionList(pageSize, pageNum, sortName, direction, para));
    }

    @RequestMapping("/add")
    @ResponseBody
    public RetKit add(HttpServletRequest request) {
        if (StrKit.isBlank(request.getParameter("voData"))) {
            return RetKit.fail("参数不存在");
        }
        Object voData = JSON.parse(request.getParameter("voData"));
        DeductionVo vo = JSON.toJavaObject(JSON.parseObject(voData.toString()), DeductionVo.class);
        return deductionManageService.save(vo);
    }

    @RequestMapping("/edit")
    @ResponseBody
    public RetKit edit(HttpServletRequest request) {
        if (StrKit.isBlank(request.getParameter("voData"))) {
            return RetKit.fail("参数不存在");
        }
        Object voData = JSON.parse(request.getParameter("voData"));
        DeductionVo vo = JSON.toJavaObject(JSON.parseObject(voData.toString()), DeductionVo.class);

        return deductionManageService.save(vo);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public RetKit del(HttpServletRequest request) {
        Integer deductionId = Integer.parseInt(request.getParameter("deductionId"));
        // return deductionManageService.del(deductionId);
        return deductionManageService.softlyDel(deductionId);
    }

    @RequestMapping("/changeStatus")
    @ResponseBody
    public RetKit changeStatus(HttpServletRequest request) {
        Integer deductionId = Integer.parseInt(request.getParameter("deductionId"));
        Integer deductionStatus = Integer.parseInt(request.getParameter("deductionStatus"));
        if (BaseConstant.DeductionStatus.NotUsedStatus.getTypeValue() == deductionStatus) {
            deductionManageService.changeStatus(deductionId,
                    BaseConstant.DeductionStatus.OutOfDateStatus.getTypeValue());
        }
        return RetKit.ok();
    }

    // 消费图片上传
    @RequestMapping("/upload")
    @ResponseBody
    public RetKit upload(@RequestParam("file") MultipartFile multipartFile, HttpSession session) throws Exception {
        if (!StringUtils.isEmpty(multipartFile) && multipartFile.getSize() > 0) {
            String filename = multipartFile.getOriginalFilename();
            String suffix = filename.substring(filename.lastIndexOf(".") + 1);
            if (filename.endsWith("jpg") || filename.endsWith("png") || filename.endsWith("jpeg")) {
                // 若上传路径文件夹不存在，创建文件夹
                File tmpUpload = new File(session.getServletContext().getRealPath("/") + "/image/deduction/");// 缓存目录
                if (!tmpUpload.exists())
                    tmpUpload.mkdirs();
                String realPath = session.getServletContext().getRealPath("/") + "/image/deduction/"
                        + new Date().getTime() + "." + suffix;
                File newfile = new File(realPath);
                try {
                    multipartFile.transferTo(newfile);
                    String imageName = uploadUtil.upload(newfile);
                    newfile.delete();// 删除缓存文件
                    return RetKit.okData(imageName);
                } catch (IOException e) {
                    e.printStackTrace();
                    return RetKit.fail(1, "文件上传异常");
                }
            } else {
                return RetKit.fail(2, "不支持该上传类型");
            }
        } else {
            return RetKit.fail(3, "文件为空");
        }
    }

    /**
     * 导出excel
     */
    @RequestMapping("/export")
    @ResponseBody
    public Integer export(HttpServletRequest request, HttpServletResponse response) {
        UserAuthentic user = (UserAuthentic) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ParkingUser parkingUser = deductionManageService.findParkingUserByUserName(user.getUserName());
        Integer parkingId = -1;
        if (parkingUser != null) {
            parkingId = parkingUser.getParkingId();
        }

        String strStatus = request.getParameter("status");
        String strLicensePlate = request.getParameter("licensePlate");
        String strRealName = request.getParameter("realName");

        List<Map<String, Object>> para = new ArrayList<>();

        if (parkingId != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("parkingId", parkingId);// 停车场id
            para.add(map);
        }

        if (StrKit.notBlank(strStatus)) {
            Map<String, Object> map = new HashMap<>();
            map.put("status", strStatus);
            para.add(map);
        }
        if (StrKit.notBlank(strLicensePlate)) {
            Map<String, Object> map = new HashMap<>();
            map.put("strLicensePlate", strLicensePlate);
            para.add(map);
        }
        if (StrKit.notBlank(strRealName)) {
            Map<String, Object> map = new HashMap<>();
            map.put("strRealName", strRealName);
            para.add(map);
        }

        deductionManageService.deductionExport(para, request, response);
        return null;
    }

}
