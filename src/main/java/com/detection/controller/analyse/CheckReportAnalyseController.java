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
import com.detection.model.report.entities.CrCheckReportInfo;
import com.detection.services.AuthenticationService;
import com.detection.services.CheckReportInfoService;
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
    private CheckReportInfoService checkReportInfoService;
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
    /**
     * @createDate 2017年6月7日下午12:51:46 
     * @author wangzhiwang
     * @param streetId
     * @return 
     * @description 通过街道获取危险等级和分数
     */
    @RequestMapping(value = "/levelAndScore", method = RequestMethod.GET)
    @ResponseBody
    public List<JSONObject> levelAndScore(Long streetId) {
        
        List<Street> streets = streetService.findAll();
        List<JSONObject> result = new ArrayList<JSONObject>();
        for (Street street : streets) {

            int countLevel1=0;
            int countLevel2=0;
            int countLevel3=0;
            int countLevel4=0;
            
            JSONObject obj = new JSONObject();
            
            obj.put("streetId", street.getId());
            obj.put("streetName", street.getName());

            List<CrCheckReport> reports = checkReportService.findByStreetId(street.getId());
            if (reports!=null&&reports.size()>0) {
                for (CrCheckReport report : reports) {
                    if (report.getScore()!=null) {
                        if (report.getRiskLevel()!=null&&report.getRiskLevel().contains("1")) {
                            countLevel1++;
                        }
                        if (report.getRiskLevel()!=null&&report.getRiskLevel().contains("2")) {
                            countLevel2++;
                        }
                        if (report.getRiskLevel()!=null&&report.getRiskLevel().contains("3")) {
                            countLevel3++;
                        }
                        if (report.getRiskLevel()!=null&&report.getRiskLevel().contains("4")) {
                            countLevel4++;
                        }
                    }
                }
            }
            int[] arr = {countLevel1,countLevel2,countLevel3,countLevel4};
            obj.put("list", arr);
            
            result.add(obj);
        }
        
        return result;
    }
/*    @RequestMapping(value = "/levelAndScore", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject levelAndScore(Long streetId) {
        
        //List<JSONObject> result = new ArrayList<JSONObject>();
        JSONObject result = new JSONObject();
        List<CrCheckReport> reports=null;
        if (streetId==null) {
            reports = checkReportService.findAll();
        }else {
            reports = checkReportService.findByStreetId(streetId);
        }
        if (reports==null||reports.size()<1) {
            return null;
        }
        double allScore1=0;
        double allScore2=0;
        double allScore3=0;
        double allScore4=0;
        
        int countLevel1=0;
        int countLevel2=0;
        int countLevel3=0;
        int countLevel4=0;
        for (CrCheckReport report : reports) {
            if (report.getScore()!=null) {
                if (report.getRiskLevel()!=null&&report.getRiskLevel().contains("1")) {
                    allScore1=allScore1+report.getScore();
                    countLevel1++;
                }
                if (report.getRiskLevel()!=null&&report.getRiskLevel().contains("2")) {
                    allScore2=allScore2+report.getScore();
                    countLevel2++;
                }
                if (report.getRiskLevel()!=null&&report.getRiskLevel().contains("3")) {
                    allScore3=allScore3+report.getScore();
                    countLevel3++;
                }
                if (report.getRiskLevel()!=null&&report.getRiskLevel().contains("4")) {
                    allScore4=allScore4+report.getScore();
                    countLevel4++;
                }
            }
        }
        JSONObject one = new JSONObject();
        one.put("level", 1);
        one.put("score", countLevel1==0?0:allScore1/countLevel1);
        JSONObject two = new JSONObject();
        two.put("level", 2);
        two.put("score", countLevel2==0?0:allScore2/countLevel2);
        JSONObject three = new JSONObject();
        three.put("level", 3);
        three.put("score", countLevel3==0?0:allScore3/countLevel3);
        JSONObject four = new JSONObject();
        four.put("level", 4);
        four.put("score", countLevel4==0?0:allScore4/countLevel4);
        
        double[] arr = {countLevel1==0?0:allScore1/countLevel1,countLevel2==0?0:allScore2/countLevel2,countLevel3==0?0:allScore3/countLevel3,countLevel4==0?0:allScore4/countLevel4};
        
        result.put("list", arr);
        
        result.add(one);
        result.add(two);
        result.add(three);
        result.add(four);
        
        return result;
    }
*/    @RequestMapping(value = "/streetAndType", method = RequestMethod.GET)
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
