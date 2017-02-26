/*
 * File Name：ReportItemServiceImpl.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月21日 上午9:29:15
 */
package com.detection.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.detection.config.LevelWeightProperties;
import com.detection.model.report.entities.ReportItem;
import com.detection.model.report.repositories.ReportItemRepository;
import com.detection.services.ReportService;


/**
 *
 * @author lcc (lincc@ggkbigdata.com)
 * @version 1.0, 2017年2月21日 上午9:29:15
 */
@Service
public class ReportServiceImpl implements ReportService {
    
    @Autowired
    private ReportItemRepository reportItemRepo;
    
    @Autowired
    private LevelWeightProperties weight;
    
    @Override
    public JSONObject getReportItemByReportNum(String reportNum) {
        
        ReportItem reportItem = reportItemRepo.findOne(reportNum);
        JSONObject obj = new JSONObject();
        obj.put("code", 200);
        obj.put("message", "success");
        obj.put("reportNum", reportNum);
        obj.put("levelA", weight.getLevelA());
        obj.put("levelB", weight.getLevelB());
        obj.put("levelC", weight.getLevelC());
        obj.put("reportItems", reportItemRepo.findOne(reportNum));
        return obj;
    }
    
    @Override
    public JSONObject getReportLevelByReportNum(String reportNum) {
        //检测总分：sum(A) * weight[A] + sum(B) * weight[B] + sum(C) * weight[C]
        double sumValue = 0.0;
        //检测通过项获得的总分
        double gainValue = 0.0;
/*        //List<ReportItem> list = reportItemRepo.findByReportNum(reportNum);
        for(ReportItem item : list) {
            if(item.getCheckLevel().trim().equals("A")) {
                sumValue += weight.getLevelA() * item.getCheckNum();
                gainValue += weight.getLevelA() * (item.getCheckNum() - item.getUnqualifiedNum());
            } else if(item.getCheckLevel().trim().equals("B")) {
                sumValue += weight.getLevelB() * item.getCheckNum();
                gainValue += weight.getLevelB() * (item.getCheckNum() - item.getUnqualifiedNum());
            } else {
                sumValue += weight.getLevelC() * item.getCheckNum();
                gainValue += weight.getLevelC() * (item.getCheckNum() - item.getUnqualifiedNum());
            }
        }
        double score = (gainValue / sumValue) * 100 ;
        score =((int)(score*100))/100.0;
        String reportLevel = "";
        if(score <= 25.00 ) {
            reportLevel = "低水平";
        } else if(score <= 65.00) {
            reportLevel = "中等水平";
        } else if(score <= 85.00) {
            reportLevel = "高水平";
        } else {
            reportLevel = "极高水平";
        }*/
        JSONObject obj = new JSONObject();
        //obj.put("score", score);
        obj.put("reportNum", reportNum);
        obj.put("code", 200);
        obj.put("message", "success");
        //obj.put("reportLevel", reportLevel);
        return obj;
    }
    
    @Override
    public String saveReportResult(String reportNum) {
/*        JSONObject obj = getReportLevelByReportNum(reportNum);
        double score = obj.getDoubleValue("score");
        String reportLevel = obj.getString("reportLevel");
        mapper.saveReportResult(reportNum, score, reportLevel);*/
        return "ok";
    }
    
    @Override
    public String bulkSaveReportResult() {
/*        List<String> reportIdList = mapper.getReportList();
        for(String reportNum : reportIdList) {
            saveReportResult(reportNum);
        }*/
        return "success";
    }
}

