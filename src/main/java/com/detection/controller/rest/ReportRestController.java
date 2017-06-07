/*
 * File Name：ReportRestController.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月21日 上午9:34:03
 */
package com.detection.controller.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.detection.services.AuthenticationService;
import com.detection.services.CheckReportService;

/**
 *
 * @author lcc (lincc@ggkbigdata.com)
 * @version 1.0, 2017年2月21日 上午9:34:03
 */

@RestController
public class ReportRestController {

    @Autowired
    private CheckReportService service;
    @Autowired
    private AuthenticationService authService;
    
    
    /**
     * @createDate 2017年6月4日下午2:07:23 
     * @author wangzhiwang
     * @param request
     * @return 
     * @description 获取数据列表
     */
    @RequestMapping(value = {"/getReportList"} , method = RequestMethod.POST)
    public JSONObject getReportList( 
            @RequestParam(value = "rows", defaultValue = "10") int pageSize, HttpSession session,
            @RequestParam(required = false) String streetId, 
            @RequestParam(required = false) String blockId, 
            @RequestParam(required = false) Long riskLevel, // 危险等级
            @RequestParam(required = false) Long buildingType, //建筑类型 
            @RequestParam(required = false) String competentDepartment, // 
            @RequestParam(required = false) Long heightType, // 高层类型
            HttpServletRequest request
            ){
        //
        int permittedRole = 1;
        JSONObject result = new JSONObject();
        result.put("code", 201);
        result.put("message", "fail");
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        if (StringUtils.hasText(streetId) && !streetId.equals("0")) {
            map.put("streetId", streetId);
        }
        if (StringUtils.hasText(blockId) && !blockId.equals("0")) {
            map.put("blockId", blockId);
        }
        if (riskLevel!=null && !"".equals(riskLevel) ) {
            if (riskLevel==1) {
                map.put("riskLevel", "危险等级1");
            }
            if (riskLevel==2) {
                map.put("riskLevel", "危险等级2");
            }
            if (riskLevel==3) {
                map.put("riskLevel", "危险等级3");
            }
            if (riskLevel==4) {
                map.put("riskLevel", "危险等级4");
            }
        }

        if (buildingType!=null && !"".equals(buildingType)) {
            if (buildingType==1) { map.put("buildingTypeSmall", "中学");}
            if (buildingType==2) { map.put("buildingTypeSmall", "交通枢纽建筑");}
            if (buildingType==3) { map.put("buildingTypeSmall", "公共娱乐建筑");}
            if (buildingType==4) { map.put("buildingTypeSmall", "养老院");}
            if (buildingType==5) { map.put("buildingTypeSmall", "医院");}
            if (buildingType==6) { map.put("buildingTypeSmall", "商业建筑");}
            if (buildingType==7) { map.put("buildingTypeSmall", "大学");}
            if (buildingType==8) { map.put("buildingTypeSmall", "小学");}
            if (buildingType==9) { map.put("buildingTypeSmall", "幼儿园");}
            if (buildingType==10) { map.put("buildingTypeSmall", "教育科研建筑");}
            if (buildingType==11) { map.put("buildingTypeSmall", "文化设施建筑");}
            if (buildingType==12) { map.put("buildingTypeSmall", "物流仓储建筑");}
            if (buildingType==13) { map.put("buildingTypeSmall", "科研院");}
            if (buildingType==14) { map.put("buildingTypeSmall", "综合性办公建筑");}
            if (buildingType==15) { map.put("buildingTypeSmall", "行政办公建筑");}
            if (buildingType==16) { map.put("buildingTypeSmall", "院所等教育科研建筑");}
        }
        if (StringUtils.hasText(competentDepartment) && !"0".equals(competentDepartment)&& !"全部".equals(competentDepartment)) {
            map.put("competentDepartment", competentDepartment);
        }
        if (heightType!=null && !heightType.equals("")) {
            if (heightType==1) { map.put("heightType", "多层建筑");}
            if (heightType==2) { map.put("heightType", "高层建筑");}
            if (heightType==3) { map.put("heightType", "超高层建筑");}
        }
        
        
        
        result = service.getAllReports(map);
        /*if(authService.isLoggedin(request) && authService.isPermitted(request, permittedRole)){
            result = service.getAllReports(map);
        }*/
        return result;
    }
    
    /**
     * @createDate 2017年6月4日下午2:07:09 
     * @author wangzhiwang
     * @param request
     * @param streetName
     * @param reportNum
     * @return 
     * @description 更新街道
     */
    @RequestMapping(value = "/updateStreet" , method = RequestMethod.POST)
    public JSONObject updateStreet( HttpServletRequest request ,String streetName,String reportNum){
        
        JSONObject result = new JSONObject();
        try {
             service.updateStreet(reportNum,streetName);
             result.put("result", true);
             result.put("msg", "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("result", false);
            result.put("msg", e.getMessage());
            
        }
        
        return result;
    }
    
    /**
     * @createDate 2017年6月4日下午2:08:37 
     * @author wangzhiwang
     * @param streetId
     * @param blockId
     * @param latitude
     * @return 
     * @description 走势图
     */
    @RequestMapping(value = "/trendChart" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject trendChart(String streetId,String blockId,String latitude){
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        if (StringUtils.hasText(streetId) && !streetId.equals("0")) {
            map.put("streetId", streetId);
        }
        if (StringUtils.hasText(blockId) && !blockId.equals("0")) {
            map.put("blockId", blockId);
        }
        if (StringUtils.hasText(latitude) && !latitude.equals("0")) {
            map.put("latitude", latitude);
        }
        
        JSONObject result = service.trendChart(map);
        
        return result;
      
    }
    
    @RequestMapping(value = { "/deleteReportByReportNum" }, method = RequestMethod.GET)
    public JSONObject deleteReportByReportNum(@RequestParam(required=true) String reportNum, HttpServletRequest request) {
        JSONObject result = new JSONObject();
        int permittedRole = 1;
        if (!authService.isLoggedin(request)) {
            result.put("code", 201);
            result.put("message", "请先登录！");
        } else if (!authService.isPermitted(request, permittedRole)) {
            result.put("code", 201);
            result.put("message", "您没有权限！");
        } else {
        	reportNum="天消"+reportNum;
            result = service.deleteReportByReportNum(reportNum);
        }
        return result;
    }

}

