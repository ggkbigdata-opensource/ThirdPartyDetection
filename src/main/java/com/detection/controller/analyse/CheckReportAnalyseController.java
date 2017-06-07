/*
 * File Name：ReportViewController.java
 *
 * Copyrighe：copyright@2017 www.ggkbigdata.com. All Rights Reserved
 *
 * Create Time: 2017年2月22日 下午2:00:35
 */
package com.detection.controller.analyse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.detection.model.area.Street;
import com.detection.model.report.entities.CrCheckReport;
import com.detection.services.AuthenticationService;
import com.detection.services.CheckReportService;
import com.detection.services.PDFParserService;
import com.detection.services.StreetService;
import com.detection.services.UserControlService;

/**
 *
 * @author lcc (lincc@ggkbigdata.com)
 * @version 1.0, 2017年2月22日 下午2:00:35
 * 
 */
@Controller
public class CheckReportAnalyseController {
    @Autowired
    private CheckReportService checkReportService;
    @Autowired
    private UserControlService userControlService;
    @Autowired
    private AuthenticationService authService;
    @Autowired
    private PDFParserService pdfParser;
    @Autowired
    private StreetService streetService;

    @Value("${uploadPath}")
    private String uploadPath;

    @RequestMapping(value = "/checkReport", method = RequestMethod.GET)
    public String toPage(Long streetId, Long blockId, String report, HttpServletRequest request) {
        request.setAttribute("streetId", streetId);
        request.setAttribute("blockId", blockId);
        request.setAttribute("report", report);
        return "analyse/check-report-analyse";
    }

    /**
     * @createDate 2017年6月7日上午11:24:33 
     * @author wangzhiwang
     * @return 
     * @description 综合分析
     */
    @RequestMapping(value = "/streetAndScore", method = RequestMethod.GET)
    @ResponseBody
    public List<JSONObject> streetAndScore() {

        List<Street> streets = streetService.findAll();
        List<JSONObject> result = new ArrayList<JSONObject>();
        for (Street street : streets) {
            JSONObject obj = new JSONObject();
            obj.put("streetId", street.getId());
            obj.put("streetName", street.getName());
            obj.put("score", "");
            double allScore = 0.000;
            List<CrCheckReport> reports = checkReportService.findByStreetId(street.getId());
            if (reports!=null&&reports.size()>0) {
                for (CrCheckReport report : reports) {
                    if (report.getScore()!=null) {
                        allScore=allScore+report.getScore();
                    }
                }
                obj.put("score", allScore/reports.size());
            }
            result.add(obj);
        }
        
        
        return result;
    }
    @RequestMapping(value = "/streetAndLevel", method = RequestMethod.GET)
    @ResponseBody
    public List<JSONObject> levelAndScore() {
        
        List<Street> streets = streetService.findAll();
        List<JSONObject> result = new ArrayList<JSONObject>();
        for (Street street : streets) {
            JSONObject obj = new JSONObject();
            obj.put("streetId", street.getId());
            obj.put("streetName", street.getName());
            double allScore = 0.000;
            List<CrCheckReport> reports = checkReportService.findByStreetId(street.getId());
            for (CrCheckReport report : reports) {
                if (report.getScore()!=null) {
                    allScore=allScore+report.getScore();
                }
                
            }
        }
        
        
        return result;
    }
    @RequestMapping(value = "/streetAndType", method = RequestMethod.GET)
    @ResponseBody
    public List<JSONObject> streetAndType() {
        
        List<Street> streets = streetService.findAll();
        List<JSONObject> result = new ArrayList<JSONObject>();
        for (Street street : streets) {
            JSONObject obj = new JSONObject();
            obj.put("streetId", street.getId());
            obj.put("streetName", street.getName());
            double allScore = 0.000;
            List<CrCheckReport> reports = checkReportService.findByStreetId(street.getId());
            for (CrCheckReport report : reports) {
                if (report.getScore()!=null) {
                    allScore=allScore+report.getScore();
                }
                
            }
        }
        
        
        return result;
    }
    @RequestMapping(value = "/streetAndDepartment", method = RequestMethod.GET)
    @ResponseBody
    public List<JSONObject> streetAndCompetentDepartment() {
        
        List<Street> streets = streetService.findAll();
        List<JSONObject> result = new ArrayList<JSONObject>();
        for (Street street : streets) {
            JSONObject obj = new JSONObject();
            obj.put("streetId", street.getId());
            obj.put("streetName", street.getName());
            double allScore = 0.000;
            List<CrCheckReport> reports = checkReportService.findByStreetId(street.getId());
            for (CrCheckReport report : reports) {
                if (report.getScore()!=null) {
                    allScore=allScore+report.getScore();
                }
                
            }
        }
        
        
        return result;
    }
    @RequestMapping(value = "/streetAndHeight", method = RequestMethod.GET)
    @ResponseBody
    public List<JSONObject> streetAndHeight() {
        
        List<Street> streets = streetService.findAll();
        List<JSONObject> result = new ArrayList<JSONObject>();
        for (Street street : streets) {
            JSONObject obj = new JSONObject();
            obj.put("streetId", street.getId());
            obj.put("streetName", street.getName());
            double allScore = 0.000;
            List<CrCheckReport> reports = checkReportService.findByStreetId(street.getId());
            for (CrCheckReport report : reports) {
                if (report.getScore()!=null) {
                    allScore=allScore+report.getScore();
                }
                
            }
        }
        
        
        return result;
    }

}
