/*
 * File Name：ReportDataController.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月22日 下午2:53:28
 */
package com.detection.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.detection.services.CheckReportService;

/**
 *
 * @author lcc (lincc@ggkbigdata.com)
 * @version 1.0, 2017年2月22日 下午2:53:28
 * @function 前端页面请求控制器
 */
@RestController
public class ReportDataController {
    
    @Autowired
    private CheckReportService checkReportService;
    
    /**
     * @author lcc
     * @version 1.0
     * @throws Exception 
     * @function 处理风险评估报告责任提取码验证
     * 校验reportNum、责任人dutyPerson和责任人电话dutyTel
     * 返回校验token
     */
    @RequestMapping(value = {"/report/submitExtractCode" }, method = RequestMethod.GET)
    public JSONObject submitExtractCode(@RequestParam String reportNum, 
            @RequestParam String dutyPerson, @RequestParam String dutyTel) throws Exception {
        return checkReportService.submitExtractCode(reportNum, dutyPerson, dutyTel);
    }
    
    @RequestMapping(value = {"/report/getDetailReportInfo" }, method = RequestMethod.GET)
    public JSONObject getDetailReportInfo(@RequestParam String verifyToken) {

        return checkReportService.getDetailReportInfo(verifyToken);
    }
    
    /**
     * @author lcc
     * @version 1.0
     * @function 获取评估项目概况表的摘要信息，包括：
     * reportNum ： 项目号
     * reportDate : 评估日期
     * projectName : 被评估单位名称
     * riskLevel : 极高水平（4）、高水平（3）、中等水平（2）、低水平（1） 
     * code : 200 success, 201 failure
     */
    @RequestMapping(value = {"/report/getAbstractReportInfo" }, method = RequestMethod.GET)
    public JSONObject getAbstractReportInfo(@RequestParam String reportNum){

        return checkReportService.getAbstractReportInfo(reportNum);
    }
}

