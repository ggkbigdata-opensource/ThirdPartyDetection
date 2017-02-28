/*
 * File Name：ReportRestController.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月21日 上午9:34:03
 */
package com.detection.controller.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.detection.model.report.entities.CheckReport;
import com.detection.model.report.entities.CheckReportInfo;
import com.detection.services.CheckReportService;
import com.detection.services.ReportService;

/**
 *
 * @author lcc (lincc@ggkbigdata.com)
 * @version 1.0, 2017年2月21日 上午9:34:03
 */

@RestController
public class ReportRestController {

    @Autowired
    private CheckReportService service;
    
/*    @RequestMapping(value = {"/getReportByReportNum" }, method = RequestMethod.GET)
    public JSONObject getReportByReportNum(@RequestParam String reportNum) {
        return service.getReportItemByReportNum(reportNum);
    }
    
    @RequestMapping(value = {"/getReportLevelByReportNum" }, method = RequestMethod.GET)
    public JSONObject getReportLevelByReportNum(@RequestParam String reportNum) {
        return service.getReportLevelByReportNum(reportNum);
    }*/
    
    @RequestMapping(value = {"/getReportList"} , method = RequestMethod.GET)
    public JSONObject getReportList(@RequestParam(name = "projectName") String projectName,
                                    @RequestParam(name = "reportNum") String reportNum,
                                    //@RequestParam(name = "projectAddress") String projectAddress,
                                    @RequestParam(name = "riskLevel") String riskLevel,
                                    @RequestParam(name = "qaName") String qaName,
                                    @RequestParam(name = "token") String token){
        JSONObject result = new JSONObject();
        int code = 200;
        String message = "sucess";
        
        //List<CheckReport> reportlist = service.getAllReports();
        List<CheckReport> reportlist = service.getReportByCondition(projectName,reportNum,riskLevel,qaName);
        List<CheckReportInfo> dataList = new ArrayList<CheckReportInfo>();
        Iterator<CheckReport>it = reportlist.iterator();
        while(it.hasNext()){
            dataList.add(it.next().getCheckReportInfo());
        }
        result.put("code", code);
        result.put("message", message);
        result.put("data", dataList);
        
        return result;
    }

}

