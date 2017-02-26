/*
 * File Name：ReportRestController.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月21日 上午9:34:03
 */
package com.detection.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.detection.services.ReportService;

/**
 *
 * @author lcc (lincc@ggkbigdata.com)
 * @version 1.0, 2017年2月21日 上午9:34:03
 */

@RestController
public class ReportRestController {

    @Autowired
    private ReportService service;
    
    @RequestMapping(value = {"/getReportByReportNum" }, method = RequestMethod.GET)
    public JSONObject getReportByReportNum(@RequestParam String reportNum) {
        return service.getReportItemByReportNum(reportNum);
    }
    
    @RequestMapping(value = {"/getReportLevelByReportNum" }, method = RequestMethod.GET)
    public JSONObject getReportLevelByReportNum(@RequestParam String reportNum) {
        return service.getReportLevelByReportNum(reportNum);
    }
    
    @RequestMapping(value = {"/saveReportResult" }, method = RequestMethod.GET)
    public String saveReportResult(@RequestParam String reportNum) {
        return service.saveReportResult(reportNum);
    }
    
    @RequestMapping(value = {"/bulkSaveReportResult" }, method = RequestMethod.GET)
    public String bulkSaveReportResult() {
        return service.bulkSaveReportResult();
    }

}

