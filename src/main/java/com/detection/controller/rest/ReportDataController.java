/*
 * File Name：ReportDataController.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月22日 下午2:53:28
 */
package com.detection.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

/**
 *
 * @author lcc (lincc@ggkbigdata.com)
 * @version 1.0, 2017年2月22日 下午2:53:28
 * @function 前端页面请求控制器
 */
@RestController
public class ReportDataController {
    
    /**
     * @author lcc
     * @version 1.0
     * @function 处理风险评估报告责任提取码验证
     * 校验reportNum、责任人dutyPerson和责任人电话dutyTel
     * 返回校验token
     */
    @RequestMapping(value = {"/report/submitExtractCode" }, method = RequestMethod.GET)
    public JSONObject submitExtractCode(@RequestParam String reportNum, 
            @RequestParam String dutyPerson, @RequestParam String dutyTel) {
        JSONObject obj = new JSONObject();
        obj.put("code", 200);
        obj.put("message", "success");
        obj.put("verifyToken", "fcea920f7412b5da7be0cf42b8c93759");
        return obj;
    }
    
    @RequestMapping(value = {"/report/getDetailReportInfo" }, method = RequestMethod.GET)
    public JSONObject getDetailReportInfo(@RequestParam String verifyToken) {
        JSONObject obj = new JSONObject();
        obj.put("code", 200);
        obj.put("message", "success");
        obj.put("reportNum", "16GJA153");
        obj.put("reportLevel", "高水平");
        obj.put("reportDate", "2017年1月20日");
        obj.put("reportConclusion", "检测结论");
        obj.put("rectifyComments", "暂无");
        obj.put("disqualification", "消防设施检测不合格项");
        obj.put("projectName", "广东广业开元科技有限公司");
        obj.put("verifyToken", "fcea920f7412b5da7be0cf42b8c93759");
        obj.put("dutyTel", "13450255760");
        obj.put("dutyPerson","蔡禹");
        return obj;
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
        JSONObject obj = new JSONObject();
        obj.put("code", 200);   //
        obj.put("message", "success");
        obj.put("reportNum", "17GJB370");
        obj.put("reportDate", "2017-03-01");
        obj.put("projectName", "广州市国会饮食娱乐有限公司");
        obj.put("riskLevel", 4);
        return obj;
    }
}

