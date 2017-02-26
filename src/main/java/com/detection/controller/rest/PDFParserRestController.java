/*
 * File Name：PDFParserRestController.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月21日 下午3:18:50
 */
package com.detection.controller.rest;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.detection.model.pdfparse.PDFParserResult;
import com.detection.services.CheckReportService;
import com.detection.services.PDFParserService;


/**
 *
 * @author lcc (lincc@ggkbigdata.com)
 * @version 1.0, 2017年2月21日 下午3:18:50
 */
@RestController
public class PDFParserRestController {
    @Autowired
    private PDFParserService service;
    @Autowired
    private CheckReportService checkService;
    
    @RequestMapping(value = {"/pdfparser" }, method = RequestMethod.GET)
    public JSONObject getReportById(@RequestParam String filePath) {
        File pdfFile = new File("C://Users//lcc//Documents//文档//项目//【2017】天河消防//report1.PDF");
        JSONObject obj = new JSONObject();
        try {
            PDFParserResult r = service.parse(pdfFile);
            obj.put("code", "200");
            obj.put("message", "sessuce");
            obj.put("result", r);
            checkService.saveCheckReportInfo(obj.getJSONObject("result").getJSONObject("cover"));
            /*checkService.saveCheckReportResultStat(obj.getJSONObject("result").getJSONArray("firstPart"), 
                    obj.getJSONObject("result").getString("reportNum"));*/  //由于解析程序尚有问题，保存会破坏测试数据
            checkService.saveCheckItemDetail(obj.getJSONObject("result").getJSONArray("thirdPart"), 
                    obj.getJSONObject("result").getString("reportNum"));
        } catch (IOException e) {
            obj.put("code", "500");
            obj.put("message", "failure");
            obj.put("result", null);
            e.printStackTrace();
        }
        return obj;
    }
}
